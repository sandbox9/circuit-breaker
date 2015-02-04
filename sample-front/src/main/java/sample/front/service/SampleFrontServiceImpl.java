package sample.front.service;

import org.springframework.stereotype.Service;
import thor.connector.command.api.ApiClientCommand;

@Service
public class SampleFrontServiceImpl implements SampleFrontService {

    public String callSample() {
        //TODO API가 변경되면서 리팩터링 필요
        String execute = new ApiClientCommand<String>("http://localhost:7777/", null).execute();
        return execute;
    }

    public String callSample2(String sample1, Integer sample2) {
        //TODO API가 변경되면서 리팩터링 필요
        String execute = new ApiClientCommand<String>("http://localhost:8888/", null).execute();
        return execute;
    }

}
