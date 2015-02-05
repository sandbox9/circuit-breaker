package thor.connector.db.mybatis.repository;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import thor.connector.db.mybatis.config.ApplicationConfig;
import thor.connector.db.mybatis.config.MyBatisConfig;
import thor.connector.db.mybatis.model.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class, MyBatisConfig.class})
public class MemberRepositoryTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void initTest() throws Exception {
		assertNotNull(dataSource);
	}
	
	@Test
	public void findMember() throws Exception {
		
		String memberId = "TEST1";
		
		Member findMember = memberRepository.findOne(memberId);
		assertNotNull(findMember);
		assertEquals(memberId, findMember.getId());
		
	}
	
}
