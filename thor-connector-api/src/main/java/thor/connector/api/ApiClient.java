package thor.connector.api;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import thor.connector.command.api.ApiClientCommand;
import thor.connector.command.api.CommandExecutionCallback;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Future;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class ApiClient {
    private RestTemplate template = new RestTemplate();

    private ApiRegistry apiRegistry;

    public <T> T get(String apiName, Class<T> resultClass, Object... params) {
        final ApiMetadata metadata = apiRegistry.getApiMeta(apiName, params, resultClass, HttpMethod.GET);
        return execute(metadata, new RequestEntity(metadata.getMethod(), toUrl(metadata)));
    }

    private URI toUrl(ApiMetadata metadata) {
        try {
            return new URI(metadata.getApiUrl());
        } catch (URISyntaxException e) {
            throw new ApiConnectorException(e);
        }
    }

    private <T> T execute(ApiMetadata metadata, RequestEntity requestEntity) {

        //TODO 일단 여기서 짜고 그 다음 리팩터링 하기...
        CommandExecutionCallback<T> executionCallback =
                new SimpleApiCommandExecutionCallback(template, metadata, requestEntity);

        //TODO 매번 생성하는 것이 아니라 처음에 생성하고 사용하는 것으로 교체..
        ApiClientCommand<T> command = new ApiClientCommand<T>(metadata.getApiName(), executionCallback);

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

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public void setApiRegistry(ApiRegistry apiRegistry) {
        this.apiRegistry = apiRegistry;
    }

    private class SimpleApiCommandExecutionCallback<T> implements CommandExecutionCallback<T> {
        private final RestTemplate template;
        private final ApiMetadata metadata;
        private final RequestEntity<T> requestEntity;

        public SimpleApiCommandExecutionCallback(RestTemplate template, ApiMetadata metadata, RequestEntity<T> requestEntity) {
            this.template = template;
            this.metadata = metadata;
            this.requestEntity = requestEntity;
        }

        @Override
        public T execute() {
            ResponseEntity<Object> response =
                    template.exchange(metadata.getApiUrl(), metadata.getMethod(), requestEntity,
                            metadata.getResultClass(), metadata.getParams());
            return (T) response.getBody();
        }
    }
}
