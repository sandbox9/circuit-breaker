package metrix.connector.db.mybatis;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import metrix.connector.command.EventType;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class MybatisDbCommandFilterTest {

	@Test
	public void initTest() throws Throwable {

		//		원래의 요청을 가로채(intercept) 호출하여 동일한 결과를 나타냄을 테스트하고, DBCommand를 이용하여 래핑했음을 확인한다.

//		given
		String statementId = "fineOne";
		Object mockResult = new Object();

		Invocation invocation = Mockito.mock(Invocation.class);
		Mockito.when(invocation.proceed()).thenReturn(mockResult);

		// final class라 Mock 객체를 생성할 수 없음 ...
		Builder builder = new MappedStatement.Builder(new Configuration(), statementId, Mockito.mock(SqlSource.class), SqlCommandType.SELECT);
		MappedStatement mappedStatement = builder.build();
		Mockito.when(invocation.getArgs()).thenReturn(new Object[]{mappedStatement});

//		when
		MybatisDbCommandFilter filter = new MybatisDbCommandFilter();
		Object interceptedResult = filter.intercept(invocation);

//		then
		assertEquals("MyBatis 요청을 가로채 래핑하여 호출한 결과가 동일", mockResult, interceptedResult);

		HystrixRequestLog currentRequest = HystrixRequestLog.getCurrentRequest();
		assertNotNull(currentRequest);
		assertTrue("DBCommand를 통해 요청이 처리되었는지 확인",
				   currentRequest.getExecutedCommandsAsString().startsWith(EventType.DB + " : " + statementId));
	}	
	
	
	@Before
	public void setUp() {
		/* we must call this to simulate a new request lifecycle running and clearing caches */
		HystrixRequestContext.initializeContext();
	}

	@After
	public void tearDown() {
		// instead of storing the reference from initialize we'll just get the current state and shutdown
		if (HystrixRequestContext.getContextForCurrentThread() != null) {
			// it could have been set NULL by the test
			HystrixRequestContext.getContextForCurrentThread().shutdown();
		}

		// force properties to be clean as well
		ConfigurationManager.getConfigInstance().clear();
	}
}
