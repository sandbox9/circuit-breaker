package sample.front.service;

import sample.front.model.Member;

public interface SampleFrontService {

	String callSample();

	String callSample2(String sample1, Integer sample2);
	
	Member getMember(String memberId);

}
