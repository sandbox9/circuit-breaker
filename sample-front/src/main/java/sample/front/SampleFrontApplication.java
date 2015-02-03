package sample.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import sample.front.config.HystrixMetricsStreamServletConfig;

@SpringBootApplication
public class SampleFrontApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleFrontApplication.class, args);
	}
}
