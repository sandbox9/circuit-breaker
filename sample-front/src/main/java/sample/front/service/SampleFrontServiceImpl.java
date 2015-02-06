package sample.front.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.front.model.Member;
import sample.front.repository.MemberRepository;
import thor.connector.api.ApiClient;
import thor.connector.command.api.ApiClientCommand;

@Service
public class SampleFrontServiceImpl implements SampleFrontService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private ApiClient apiClient;
	

    public String callSample() {
      
    	//TODO API가 변경되면서 리팩터링 필요
    	String call = apiClient.get("http://1.223.129.53:10001/", String.class);
        return call;
    }

    public String callSample2(String sample1, Integer sample2) {

    	//TODO API가 변경되면서 리팩터링 필요
    	String call = apiClient.get("http://1.223.129.53:10002/", String.class, sample1, sample2);
        return call;
    }

	@Override
	public Member getMember(String memberId) {
		return memberRepository.findOne(memberId);
	}

}
