package sample.front.connector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpMethod;

import metrix.connector.api.ApiMetadata;
import metrix.connector.api.ApiRegistry;

public class SimpleApiRegistry implements ApiRegistry {
	
	@SuppressWarnings("rawtypes")
	private final Map<String, ApiMetadata> apiMetadataMap = new ConcurrentHashMap<String, ApiMetadata>();

	@SuppressWarnings("rawtypes")
	@Override
	public <T> ApiMetadata createApiMeta(String apiName, Object[] params, Class<T> resultClass, HttpMethod httpMethod) {
		
		ApiMetadata apiMetadata = apiMetadataMap.get(apiName);
		
		if(apiMetadata == null) {
			
			apiMetadata = new ApiMetadata();
			apiMetadata.setApiName(apiName);
			apiMetadata.setApiUrl(apiName);
			apiMetadata.setParams(params);
			apiMetadata.setResultClass(resultClass);
			apiMetadata.setHttpMethod(httpMethod);
			
			apiMetadataMap.put(apiName, apiMetadata);
			
		}
		
		return apiMetadata;
	}

}
