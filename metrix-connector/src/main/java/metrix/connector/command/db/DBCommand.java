package metrix.connector.command.db;

import static metrix.connector.command.EventType.DB;
import metrix.connector.command.CommandExecutionCallback;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.MetrixCommand;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class DBCommand<T> extends MetrixCommand<T> {

    public DBCommand(String statementId, CommandExecutionCallback<T> executionCallback) {
        super(statementId, executionCallback,
        	  Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(DB.name()))
	                .andCommandKey(HystrixCommandKey.Factory.asKey(DB.name() + " : " + statementId))
	                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
	//        				디버깅 테스트를 위해서 타임아웃 길게 설정
	//                        .withExecutionIsolationThreadTimeoutInMilliseconds(1000 * 1000)
                		)
    		);
    }

}
