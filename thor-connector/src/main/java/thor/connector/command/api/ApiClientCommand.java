package thor.connector.command.api;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * CommandGroup(Hystrix): 일단은 API 한 통으로 하나로 묶고 다시 생각 (TODO)
 * CommandName(Hystrix): API 별로 구분
 * Event: API
 * Created by chanwook on 2015. 2. 4..
 */
public class ApiClientCommand extends HystrixCommand<Object> {
    protected ApiClientCommand(HystrixCommandGroupKey group) {
        super(group);
    }

    @Override
    protected Object run() throws Exception {
        return null;
    }
}
