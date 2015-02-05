package sample.front.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sample.front.model.Member;

@Repository
public class MemberSqlMapRepository implements MemberRepository {
	
	@Autowired
	private SqlSession sqlSession;
	

	@Override
	public Member findOne(String memberId) {
		return sqlSession.selectOne("member.getMember", memberId);
	}
}
