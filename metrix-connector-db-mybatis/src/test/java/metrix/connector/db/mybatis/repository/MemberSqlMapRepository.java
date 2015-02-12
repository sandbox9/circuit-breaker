package metrix.connector.db.mybatis.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import metrix.connector.db.mybatis.model.Member;

@Repository
public class MemberSqlMapRepository implements MemberRepository {
	
	@Autowired
	private SqlSession sqlSession;
	

	@Override
	public Member findOne(String memberId) {
		return sqlSession.selectOne("member.getMember", memberId);
	}
}
