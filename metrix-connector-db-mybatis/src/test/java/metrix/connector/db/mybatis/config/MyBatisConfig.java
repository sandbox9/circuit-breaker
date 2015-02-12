package metrix.connector.db.mybatis.config;

import metrix.connector.db.mybatis.MybatisDbCommandFilter;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

	@Bean
	public DataSource dataSource() {
		
		EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:hsqldb/schema.sql")
				.addScript("classpath:hsqldb/data.sql").build();
		
		return dataSource;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws Exception {

		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setConfigLocation(new ClassPathResource("sqlmap/sqlmap-config.xml"));
		sessionFactory.setMapperLocations(new Resource[]{new ClassPathResource("sqlmap/user-sql.xml")});
		sessionFactory.setPlugins(new Interceptor[] { new MybatisDbCommandFilter()});

		return sessionFactory;
	}

	@Bean
	public SqlSessionTemplate sqlSession() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory().getObject());
	}

}
