package order;


import static javassist.util.proxy.ProxyFactory.isProxyClass;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

public class JavassistProxifier {

	/**
	 * Do not proxy these methods.
	 */
	private static final MethodFilter IGNORE_BRIDGE_AND_OBJECT_METHODS = new MethodFilter() {
		@Override
		public boolean isHandled(Method method) {
			return !method.isBridge() && !method.getDeclaringClass().equals(Object.class);
		}
	};
	private MethodHandler handler;

	public JavassistProxifier(MethodHandler handler) {
		this.handler = handler;
	}

	public <T> T proxify(Class<T> type) {
		final ProxyFactory factory = new ProxyFactory();
		factory.setFilter(IGNORE_BRIDGE_AND_OBJECT_METHODS);
		Class<?> rawType = extractRawType(type);

		if (type.isInterface()) {
			factory.setInterfaces(new Class[] { rawType });
		} else {
			factory.setSuperclass(rawType);
		}

		Object instance = createInstance(type, factory);

		return (T) instance;
	}

	private <T> Object createInstance(Class<T> type, ProxyFactory factory) {
		try {
			return factory.create(null, null, handler);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> Class<?> extractRawType(Class<T> type) {
		return isProxyType(type) ? type.getSuperclass() : type;
	}

	private boolean isProxyType(Class<?> type) {
		boolean proxy = isProxyClass(type);

		return proxy;
	}

}
