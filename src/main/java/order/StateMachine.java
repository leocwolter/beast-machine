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
		State currentState = order.getState();
		if (!currentState.canBeChangedBy((Class<? extends Action>) clazz)) {
			throw new IllegalArgumentException("O estado " + currentState + " não pode ser alterado pela ação" + clazz);
		}

		return new JavassistProxifier(new MethodHandler() {
			@Override
			public Object invoke(Object obj, Method arg1, Method method, Object[] args) throws Throwable {

				if (!isAnnotationPresent(obj, method, ChangeState.class))
					return method.invoke(obj, args);

				Action action = (Action) obj;
				Object object = method.invoke(obj, args);
				Field orderState = Order.class.getDeclaredField("state");

				if (action.canExecute(order) && theOrderIsOnState(action.getPreviousState())) {

					System.out.println("Depois, muda estado");

					orderState.setAccessible(true);
					orderState.set(order, action.getNextState());
					return object;
				}

				throw new IllegalStateException("Tentei mudar de estado mas a action nao deixou");
			}

			private boolean theOrderIsOnState(State previousState) {
				return order.getState() == previousState;
			}

			private boolean isAnnotationPresent(Object obj, Method method, Class<ChangeState> annotation) {
				Method[] declaredMethods = obj.getClass().getSuperclass().getDeclaredMethods();
				for (Method originalMethod : declaredMethods) {
					if (method.getName().endsWith(originalMethod.getName()))
						return originalMethod.isAnnotationPresent(annotation);
				}
				return false;
			}
		}).proxify(clazz);
	}

}
