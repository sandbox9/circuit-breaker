package sample.front.config;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@Configuration
public class HystrixMetricsStreamServletConfig {

	
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
	    return new ServletRegistrationBean(new HystrixMetricsStreamServlet(),"/hystrix.stream");
	}
	
	
}
