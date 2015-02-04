package sample.front.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import thor.connector.command.api.ApiClientCommand;

@Service
public class SampleFrontServiceImpl implements SampleFrontService {
	
	public String callSample() {
		
		String execute = new ApiClientCommand<String>("http://localhost:7777/").execute();
		return execute;
	}

	public String callSample2(String sample1, Integer sample2) {

		String execute = new ApiClientCommand<String>("http://localhost:8888/").execute();
		return execute;
	}

}
