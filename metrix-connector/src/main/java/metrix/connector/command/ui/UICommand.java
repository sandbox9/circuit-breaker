package metrix.connector.command.ui;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import metrix.connector.command.CommandExecutionCallback;

import static metrix.connector.command.EventType.UI;

/**
 * UI 이벤트를 받아서 요청 처리를 지원하는 커맨드 클래스
 * <p/>
 * Created by chanwook on 2015. 2. 4..
 */
public class UICommand<T> extends HystrixCommand<T> {

    private CommandExecutionCallback<T> executionCallback;

    private String url;

    public UICommand(String url, CommandExecutionCallback<T> executionCallback) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(UI.name()))
                .andCommandKey(HystrixCommandKey.Factory.asKey(UI.name() + " : " + url))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)));
        this.url = url;
        this.executionCallback = executionCallback;
    }

    @Override
    protected T run() throws Exception {
        return executionCallback.execute();
    }
}
