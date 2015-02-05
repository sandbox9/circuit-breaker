package sample.front.repository;

import sample.front.model.Member;

public interface MemberRepository {
	
	Member findOne(String memberId);
}
