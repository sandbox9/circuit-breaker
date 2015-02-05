package repository;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import model.Member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.ApplicationConfig;
import config.MyBatisConfig;

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
		
		String memberId = "PRODUCTION1";
		
		Member findMember = memberRepository.findOne(memberId);
		assertNotNull(findMember);
		assertEquals(memberId, findMember.getId());
		
	}
	
}
