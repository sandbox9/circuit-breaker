package sample.front.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.front.model.Member;
import sample.front.repository.MemberRepository;
import thor.connector.command.api.ApiClientCommand;

@Service
public class SampleFrontServiceImpl implements SampleFrontService {
	
	@Autowired
	private MemberRepository memberRepository;
	

    public String callSample() {
        //TODO API가 변경되면서 리팩터링 필요
//        String execute = new ApiClientCommand<String>("http://localhost:7777/", null).execute();
        return "sample";
    }

    public String callSample2(String sample1, Integer sample2) {
        //TODO API가 변경되면서 리팩터링 필요
//        String execute = new ApiClientCommand<String>("http://localhost:8888/", null).execute();
        return "sample2";
    }

	@Override
	public Member getMember(String memberId) {
		return memberRepository.findOne(memberId);
	}

}
