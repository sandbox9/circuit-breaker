package metrix.connector.db.mybatis.repository;

import metrix.connector.db.mybatis.model.Member;

public interface MemberRepository {
	
	Member findOne(String memberId);
}
