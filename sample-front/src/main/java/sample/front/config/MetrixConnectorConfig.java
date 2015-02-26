package sample.front.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import metrix.connector.api.ApiClient;
import metrix.connector.api.ApiRegistry;
import metrix.connector.command.EventHistory;
import metrix.connector.command.trail.TrailHandlerInterceptor;
import metrix.connector.ui.UiCommandEventFilter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import sample.front.connector.SimpleApiRegistry;

import com.netflix.hystrix.MetrixCommandMetrics;
import com.netflix.hystrix.contrib.metrics.eventstream.MetrixMetricsStreamServlet;

@Configuration
@EnableWebMvc
public class MetrixConnectorConfig  extends WebMvcConfigurerAdapter {

	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
	    return new ServletRegistrationBean(new MetrixMetricsStreamServlet(),"/hystrix.stream");
	}
	
	@Bean
	public FilterRegistrationBean connterUiFilter() {
		
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		UiCommandEventFilter filter = new UiCommandEventFilter();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.addUrlPatterns("/");
		
		return filterRegistrationBean;
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

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(new TrailHandlerInterceptor());
	}
	
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		
		JsonTest json = new JsonTest();
		
		ObjectMapper om = new ObjectMapper();
		String writeValueAsString = om.writeValueAsString(json);
		System.out.println(writeValueAsString);
		
	}
	
static class JsonTest {
	
//	{"id":"json","eventHistories":[{"id":null,"order":0,"time":1424850597303,"status":"성공","exception":null},{"id":null,"order":0,"time":1424850597303,"status":"성공","exception":null},{"id":null,"order":0,"time":1424850597303,"status":"성공","exception":null}]}
		
		private String id = "json";
		
		private List<EventHistory> eventHistories = new ArrayList<EventHistory>();
		
		public JsonTest() {
			super();
			this.eventHistories.add(new EventHistory());
			this.eventHistories.add(new EventHistory());
			this.eventHistories.add(new EventHistory());
		}
		

		public String getId() {
			return id;
		}




		public void setId(String id) {
			this.id = id;
		}




		public List<EventHistory> getEventHistories() {
			return eventHistories;
		}




		public void setEventHistories(List<EventHistory> eventHistories) {
			this.eventHistories = eventHistories;
		}




		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("JsonTest [eventHistories=").append(eventHistories)
					.append("]");
			return builder.toString();
		}
	}
}
