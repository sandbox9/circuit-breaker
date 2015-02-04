package thor.connector.api;

import org.springframework.http.HttpMethod;

/**
 * Created by chanwook on 2015. 2. 4..
 */
public class ApiMetadata<T> {
    private String apiUrl;
    private String apiName;
    private Object[] params;
    private Class<T> resultClass;
    private HttpMethod method;
    private HttpMethod httpMethod;

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiName() {
        return apiName;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }

    public void setResultClass(Class<T> resultClass) {
        this.resultClass = resultClass;
    }

    public Class<T> getResultClass() {
        return resultClass;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
