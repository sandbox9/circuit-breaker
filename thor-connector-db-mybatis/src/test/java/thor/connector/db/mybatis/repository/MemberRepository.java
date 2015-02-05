package thor.connector.db.mybatis.repository;

import thor.connector.db.mybatis.model.Member;

public interface MemberRepository {
	
	Member findOne(String memberId);
}
