package sample.front.config;

import javax.servlet.Filter;

import metrix.connector.api.ApiClient;
import metrix.connector.api.ApiRegistry;
import metrix.connector.ui.UiCommandEventFilter;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sample.front.connector.SimpleApiRegistry;

import com.netflix.hystrix.contrib.metrics.eventstream.MetrixMetricsStreamServlet;

@Configuration
public class MetrixConnectorConfig {

	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
	    return new ServletRegistrationBean(new MetrixMetricsStreamServlet(),"/hystrix.stream");
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
