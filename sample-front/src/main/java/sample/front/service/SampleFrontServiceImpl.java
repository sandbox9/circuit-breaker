package sample.front.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SampleFrontServiceImpl implements SampleFrontService {

	public String callSample() {
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://localhost:7777/", String.class);
	}

	public String callSample2() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://localhost:8888/", String.class);
	}
}
