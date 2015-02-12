package metrix.connector.command.db;

import static org.junit.Assert.*;

import metrix.connector.command.EventType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;

import metrix.connector.command.CommandExecutionCallback;

public class DBCommandTest {
	
	private DBCommand dbCommand;

	private CommandExecutionCallback mockCallback;
	
	private String statementId;

	
	@Before
	public void setUp() throws Exception {
		
		mockCallback = Mockito.mock(CommandExecutionCallback.class);
		
		statementId = "testStatementId";
		dbCommand = new DBCommand(statementId, mockCallback);
	}

	@Test
	public void settingTest() throws Exception {
		
//		생성자를 통한 설정 테스트
		Assert.assertEquals(EventType.DB.name(), dbCommand.getCommandGroup().name());
		assertEquals(EventType.DB + " : " + statementId, dbCommand.getCommandKey().name());
		assertEquals("스레드 풀 방식", ExecutionIsolationStrategy.THREAD, dbCommand.getProperties().executionIsolationStrategy().get());
		
//		Hystrix 기본 설정 테스트
		HystrixCommandProperties properties = dbCommand.getProperties();

		assertEquals("서킷 통계 데이터 누적 기준 시간(기본 10초 동안 에러율을 기준으로 Cricuit을 open할지 여부 판단)",
					 new Integer(10 * 1000), properties.metricsRollingStatisticalWindowInMilliseconds().get());
		assertEquals("서킷이 open되는 에러%", 
					 new Integer(50), properties.circuitBreakerErrorThresholdPercentage().get());
		assertEquals("동시접속 기준 수(동시에 20개 이상의 요청시 'Threadpool Rejected Request Count' 증가)",
					 new Integer(20), properties.circuitBreakerRequestVolumeThreshold().get());
		
		assertEquals("타임아웃 시간(요청 응답이 1초이상 지연될 경우 'Timed-out Request Count 증가)", 
					 new Integer(1 * 1000), properties.executionIsolationThreadTimeoutInMilliseconds().get());
	}
	
	@Test
	public void callTest() throws Exception {
		
		Mockito.verify(mockCallback, Mockito.times(0)).execute();
		dbCommand.execute();
		Mockito.verify(mockCallback, Mockito.times(1)).execute();
	}
}
