package thor.connector.api;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import thor.connector.command.CommandExecutionCallback;
import thor.connector.command.api.ApiClientCommand;

import java.util.Arrays;
import java.util.concurrent.Future;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class ApiClient {
    private RestTemplate template = new RestTemplate();

    private ApiRegistry apiRegistry;

    public <T> T get(String apiName, Class<T> resultClass, Object... params) {
        final ApiMetadata metadata = apiRegistry.createApiMeta(apiName, params, resultClass, HttpMethod.GET);
        return execute(metadata);
    }

    private <T> T execute(ApiMetadata metadata) {

        //TODO 일단 여기서 짜고 그 다음 리팩터링 하기...
        CommandExecutionCallback<T> executionCallback =
                new SpringRestTemplateApiCommandExecutionCallback(template, metadata);

        //TODO 매번 생성하는 것이 아니라 처음에 생성하고 사용하는 것으로 교체..
        ApiClientCommand<T> command = new ApiClientCommand(metadata.getApiName(), executionCallback);

        T result = null;
        try {
            //TODO Observable로 교체 검토
            Future<T> queue = command.queue();
            result = queue.get();
        } catch (Throwable e) {
            new ApiConnectorException("API 실행 중 예외가 발생했습니다.", e);
        }
        return result;
    }

    private class SpringRestTemplateApiCommandExecutionCallback<T> implements CommandExecutionCallback<T> {
        private final RestTemplate template;
        private final ApiMetadata metadata;

        public SpringRestTemplateApiCommandExecutionCallback(RestTemplate template, ApiMetadata metadata) {
            this.template = template;
            this.metadata = metadata;
        }

        @Override
        public T execute() {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity httpEntity = new HttpEntity(headers);

            ResponseEntity<Object> response =
                    template.exchange(metadata.getApiUrl(), metadata.getHttpMethod(), httpEntity,
                            metadata.getResultClass(), metadata.getParams());
            return (T) response.getBody();
        }
    }

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public void setApiRegistry(ApiRegistry apiRegistry) {
        this.apiRegistry = apiRegistry;
    }
}
