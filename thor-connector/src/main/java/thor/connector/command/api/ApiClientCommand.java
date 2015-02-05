package thor.connector.command.api;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import thor.connector.command.CommandExecutionCallback;

import static thor.connector.command.EventType.API;

/**
 * CommandGroup(Hystrix): 일단은 API 한 통으로 하나로 묶고 다시 생각 (TODO)
 * CommandName(Hystrix): API 별로 구분
 * Event: API
 * Created by chanwook on 2015. 2. 4..
 */
public class ApiClientCommand<T> extends HystrixCommand<T> {

    private final CommandExecutionCallback<T> executionCallback;

    public ApiClientCommand(String apiName, CommandExecutionCallback<T> executionCallback) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(API.name()))
                        .andCommandKey(HystrixCommandKey.Factory.asKey(API.name() + " : " + apiName))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                				디버깅 테스트를 위해서 타임아웃 길게 설정
                                        .withExecutionIsolationThreadTimeoutInMilliseconds(1000 * 1000)
                        )
        );
        this.executionCallback = executionCallback;
    }

    @Override
    protected T run() throws Exception {
        T result = executionCallback.execute();
        return result;
    }
}
