package thor.connector.api;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class ApiClientForCommandTests {

    public static final String API_NAME = "getProduct";
    public static final String API_URL = "http://api.com/product/{}";
    public static final int PRODUCT_ID = 100;

    @Test
    public void callApi() throws Exception {
        //given
        ApiClient client = new ApiClient();
        client.setApiRegistry(new MockApiRegistry());
        client.setTemplate(new MockRestTemplate());

        //when
        Product product = client.get(API_NAME, Product.class, PRODUCT_ID);

        //then
        assertEquals(PRODUCT_ID, product.getProductId());
    }

    private class MockApiRegistry implements ApiRegistry {
        @Override
        public <T> ApiMetadata createApiMeta(String apiName, Object[] params, Class<T> resultClass, HttpMethod httpMethod) {
            ApiMetadata metadata = new ApiMetadata();
            if (API_NAME.equals(apiName)) {
                metadata.setApiName(apiName);
                metadata.setApiUrl(API_URL);
            }
            metadata.setParams(params);
            metadata.setResultClass(resultClass);
            metadata.setHttpMethod(httpMethod);
            return metadata;
        }
    }

    private class MockRestTemplate extends RestTemplate {
        @Override
        public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
            if (API_URL.equals(url)) {
                return new ResponseEntity(new Product((Integer) uriVariables[0]), HttpStatus.OK);
            }
            throw new RuntimeException("잘못된 테스트케이스 값입니다!");
        }
    }
}
