package thor.connector.command.db;

import thor.connector.command.CommandExecutionCallback;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class DBCommand<T> extends HystrixCommand<T> {

	private static String groupName = "DB";
	private final CommandExecutionCallback<T> executionCallback;
	
	public DBCommand(String statementId, CommandExecutionCallback<T> executionCallback) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(groupName + " : " + statementId))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//        				디버깅 테스트를 위해서 타임아웃 길게 설정
                                .withExecutionIsolationThreadTimeoutInMilliseconds(1000 * 1000)
               ));
		
		this.executionCallback = executionCallback;
	}

	@Override
	protected T run() throws Exception {
		System.out.println("MyBatis Hystrix Call");
		return executionCallback.execute();
	}
}
