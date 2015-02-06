package sample.front.config;

import javax.servlet.Filter;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sample.front.connector.SimpleApiRegistry;
import thor.connector.api.ApiClient;
import thor.connector.api.ApiMetadata;
import thor.connector.api.ApiRegistry;
import thor.connector.ui.UiCommandEventFilter;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@Configuration
public class ThorConnectorConfig {

	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
	    return new ServletRegistrationBean(new HystrixMetricsStreamServlet(),"/hystrix.stream");
	}
	
	@Bean
	public Filter connterUiFilter() {
		
		UiCommandEventFilter filter = new UiCommandEventFilter();
		return filter;
	}
	
	@Bean
	public ApiClient apiClient() {
		
		ApiClient apiClient = new ApiClient();
		apiClient.setApiRegistry(apiRegistry());
		
		return apiClient;
	}
	
	@Bean
	public ApiRegistry apiRegistry() {
		
		return new SimpleApiRegistry();
	}
	
}
