package sample.front.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import circuitbreaker.hystrix.SimpleHystrixCommand;

@Service
public class SampleFrontServiceImpl implements SampleFrontService {
	
	public String callSample() {
		
		RestTemplate restTemplate = new RestTemplate();
//		throw new IllegalArgumentException("테테테테스트");
		return restTemplate.getForObject("http://localhost:7777/", String.class);
	}

	public String callSample2(String sample1, Integer sample2) {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://localhost:8888/", String.class);
	}

}
