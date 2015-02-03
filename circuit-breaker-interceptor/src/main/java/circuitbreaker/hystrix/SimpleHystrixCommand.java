package circuitbreaker.hystrix;

import java.lang.reflect.Method;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class SimpleHystrixCommand extends HystrixCommand<Object> {
	
	private Object target;
	
	private String methodName;
	
	private Object[] parameters;
	
	private Class<?>[] parameterTypes;
	
	public SimpleHystrixCommand(Object target, String methodName) {
		this(target, methodName, null, null);
	}
	
	public SimpleHystrixCommand(Object target, String methodName, Object[] parameters, Object[] parameterTypes) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(target.getClass().getSimpleName()))
					.andCommandKey(HystrixCommandKey.Factory.asKey(methodName))
					.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
					.withExecutionIsolationThreadTimeoutInMilliseconds(100 * 1000))
				);
		
		this.target = target;
		this.methodName = methodName;
		this.parameters = parameters;
		
		if(parameterTypes != null && parameters.length > 0) {
			this.parameterTypes = new Class[parameterTypes.length];
			
			for(int i = 0; i < parameterTypes.length; i++) {
				this.parameterTypes[i] = (Class<?>) parameterTypes[i];
			}
		}
		
	}

	@Override
	protected Object run() throws Exception {
		
		Method declaredMethod = target.getClass().getDeclaredMethod(methodName, parameterTypes);
		Object invoke = declaredMethod.invoke(target, parameters);
		
		return invoke;
	}
}
