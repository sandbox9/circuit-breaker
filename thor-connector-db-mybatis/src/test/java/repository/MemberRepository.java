package repository;

import model.Member;

public interface MemberRepository {
	
	Member findOne(String memberId);
}
