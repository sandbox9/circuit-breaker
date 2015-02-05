package thor.connector.command.ui;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import org.aopalliance.intercept.MethodInvocation;
import thor.connector.command.CommandExecutionCallback;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class UICommand extends HystrixCommand<Object> {

    private static String groupName = "UI";
    private CommandExecutionCallback executionCallback;

    private String url;

    private MethodInvocation methodInvocation;

    public UICommand(String url, CommandExecutionCallback executionCallback) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(groupName + " : " + url))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)));
        this.url = url;
        this.executionCallback = executionCallback;
    }

    @Override
    protected Object run() throws Exception {
        return executionCallback.execute();
    }
}
