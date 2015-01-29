package circuitbreaker.hystrix;

import java.lang.reflect.Method;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class SimpleHystrixCommand extends HystrixCommand<Object> {
	
	private Object target;
	
	private Method method;
	
	private Object[] args;

	public SimpleHystrixCommand(Object target, Method method, Object[] args) {
		 super(HystrixCommandGroupKey.Factory.asKey(target.getClass().getSimpleName()));
		 
		 this.target = target;
		 this.method = method;
		 this.args = args;
	}

	@Override
	protected Object run() throws Exception {
		
		System.out.println("HystrixCall");
		
		return method.invoke(target, args);
	}

}
