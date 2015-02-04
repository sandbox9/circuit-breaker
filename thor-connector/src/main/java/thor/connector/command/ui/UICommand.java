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

    //FIXME 삭제 필요
    public UICommand(String url, MethodInvocation methodInvocation) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(groupName + " : " + url))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)));

        this.url = url;
        this.methodInvocation = methodInvocation;
    }

    public UICommand(String url, CommandExecutionCallback executionCallback) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(groupName + " : " + url))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)));
        this.executionCallback = executionCallback;
    }

    @Override
    protected Object run() throws Exception {
        try {
            return methodInvocation.proceed();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}
