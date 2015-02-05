package thor.connector.api;

import org.springframework.http.HttpMethod;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public interface ApiRegistry {
    <T> ApiMetadata createApiMeta(String apiName, Object[] params, Class<T> resultClass, HttpMethod httpMethod);
}
