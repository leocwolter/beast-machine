package order;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;

public class StateMachine {

	private final Order order;

	public StateMachine(Order order) {
		this.order = order;
	}

	public static StateMachine move(Order order) {
		return new StateMachine(order);
	}

	public <T> T using(Class<T> clazz) {
		return new JavassistProxifier(new MethodHandler() {
			@Override
			public Object invoke(Object obj, Method arg1, Method method, Object[] args) throws Throwable {

				if(!isAnnotationPresent(obj, method, ChangeState.class)) return method.invoke(obj, args);

				Action action = (Action) obj;
				Field orderState = Order.class.getDeclaredField("state");
				orderState.setAccessible(true);

				if(action.canExecute(order) && theOrderIsOnState(action.getPreviousState(), orderState)) {

					System.out.println("Depois, muda estado");

					Object object = method.invoke(obj, args);
					orderState.set(order, action.getNextState());
					return object;
				}

				throw new IllegalStateException("Tentei mudar de estado mas a action nao deixou");
			}

			private boolean theOrderIsOnState(State previousState, Field orderState) {
				try {
					return orderState.get(order) == previousState;
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException("Vish, order não tem state não", e);
				}
			}

			private boolean isAnnotationPresent(Object obj, Method method, Class<ChangeState> annotation) {
				Method[] declaredMethods = obj.getClass().getSuperclass().getDeclaredMethods();
				for (Method originalMethod : declaredMethods) {
					if(method.getName().endsWith(originalMethod.getName()))
						return originalMethod.isAnnotationPresent(annotation);
				}
				return false;
			}
		}).proxify(clazz);
	}

}
