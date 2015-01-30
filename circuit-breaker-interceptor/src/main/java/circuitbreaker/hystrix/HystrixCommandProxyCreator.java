package circuitbreaker.hystrix;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class HystrixCommandProxyCreator {
	
	
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> classs)  {

		ProxyFactory factory = new ProxyFactory();
		factory.setSuperclass(getSuperClass(classs));
		factory.setInterfaces(getInterfaces(classs));
		factory.setFilter(createMethodFilter());

		Class proxyClass = factory.createClass();
		Object proxyIntance;
		try {
			proxyIntance = proxyClass.newInstance();
			((ProxyObject) proxyIntance).setHandler(createHystrixCommandMethodHandler());
			return (T) proxyIntance;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static MethodHandler createHystrixCommandMethodHandler() {
		return new MethodHandler() {
			@Override
			public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
				return 	new SimpleHystrixCommand(self, proceed, args).execute();
			}
		};
	}

//	TODO : Proxy 적용 대상 메소드 필터링 
	private static MethodFilter createMethodFilter() {
		return new MethodFilter() {

			@Override
			public boolean isHandled(Method m) {
				return !m.getName().equals("finalize");
			}
		};
	}

	@SuppressWarnings("rawtypes")
	private static Class[] getInterfaces(Class classs) {
		return classs.getInterfaces();
	}

	@SuppressWarnings("rawtypes")
	private static Class getSuperClass(Class classs) {
		return classs.getSuperclass();
	}


	//	ProxyFactory f = new ProxyFactory();
	//	 f.setSuperclass(Foo.class);
	//	 f.setFilter(new MethodFilter() {
	//	     public boolean isHandled(Method m) {
	//	         // ignore finalize()
	//	         return !m.getName().equals("finalize");
	//	     }
	//	 });
	//	 Class c = f.createClass();
	//	 MethodHandler mi = new MethodHandler() {
	//	     public Object invoke(Object self, Method m, Method proceed,
	//	                          Object[] args) throws Throwable {
	//	         System.out.println("Name: " + m.getName());
	//	         return proceed.invoke(self, args);  // execute the original method.
	//	     }
	//	 };
	//	 Foo foo = (Foo)c.newInstance();
	//	 ((Proxy)foo).setHandler(mi);


	//	public static void main(String[] args) throws Throwable {
	//		ProxyFactory proxy = new ProxyFactory();
	//		proxy.setSuperclass(ProxyFactoryExample.class);
	//		proxy.setInterfaces(new Class[] { Serializable.class });
	//		proxy.setFilter(new MethodFilter() {
	//			@Override
	//			public boolean isHandled(Method m) {
	//				// skip finalize methods
	//				return !(m.getParameterTypes().length == 0 && m.getName()
	//						.equals("finalize"));
	//			}
	//		});
	//
	//		MethodHandler tracingMethodHandler = new MethodHandler() {
	//
	//			@Override
	//			public Object invoke(Object self, Method thisMethod,
	//					Method proceed, Object[] args) throws Throwable {
	//
	//				long start = System.currentTimeMillis();
	//				try {
	//
	//					return proceed.invoke(self, args);
	//
	//				} finally {
	//
	//					long end = System.currentTimeMillis();
	//					System.out.println("Execution time: " + (end - start)
	//							+ " ms, method: " + proceed);
	//				}
	//
	//			}
	//		};
	//
	//		ProxyFactoryExample obj = (ProxyFactoryExample) proxy.create(
	//				new Class[0], new Object[0], tracingMethodHandler);
}
