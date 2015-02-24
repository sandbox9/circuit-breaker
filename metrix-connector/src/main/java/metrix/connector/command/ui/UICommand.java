package metrix.connector.command.ui;

import static metrix.connector.command.EventType.UI;
import metrix.connector.command.CommandExecutionCallback;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.MetrixCommand;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

/**
 * UI 이벤트를 받아서 요청 처리를 지원하는 커맨드 클래스
 * <p/>
 * Created by chanwook on 2015. 2. 4..
 */
public class UICommand<T> extends MetrixCommand<T> {

    public UICommand(String url, CommandExecutionCallback<T> executionCallback) {
        super(url, executionCallback,
    		  Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(UI.name()))
                .andCommandKey(HystrixCommandKey.Factory.asKey(UI.name() + " : " + url))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)));
    }
}
