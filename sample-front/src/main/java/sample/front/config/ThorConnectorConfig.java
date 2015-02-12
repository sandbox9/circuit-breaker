package sample.front.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import metrix.connector.api.ApiClient;
import metrix.connector.api.ApiRegistry;
import metrix.connector.ui.UiCommandEventFilter;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sample.front.connector.SimpleApiRegistry;

import javax.servlet.Filter;

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
