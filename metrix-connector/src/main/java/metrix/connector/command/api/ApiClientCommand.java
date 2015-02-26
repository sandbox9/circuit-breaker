package metrix.connector.command.api;

import metrix.connector.command.CommandExecutionCallback;
import metrix.connector.command.EventType;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.MetrixCommand;

/**
 * CommandGroup(Hystrix): 일단은 API 한 통으로 하나로 묶고 다시 생각 (TODO)
 * CommandName(Hystrix): API 별로 구분
 * Event: API
 * Created by chanwook on 2015. 2. 4..
 */
public class ApiClientCommand<T> extends MetrixCommand<T> {

    public ApiClientCommand(String apiName, CommandExecutionCallback<T> executionCallback) {
        super(apiName, executionCallback,
        	  Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(EventType.API.name()))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(EventType.API.name() + " : " + apiName))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//     				디버깅 테스트를 위해서 타임아웃 길게 설정
                    .withExecutionIsolationThreadTimeoutInMilliseconds(1000 * 1000)
                )
        );
    }
}
