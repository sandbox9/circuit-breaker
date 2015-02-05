package config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import thor.connector.db.mybatis.MybatisDbCommandFilter;

@Configuration
public class MyBatisConfig {
	
	@Bean
	public DataSource dataSource() {
			
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:mysql://1.223.129.52:3306/fwdemo");
		dataSource.setUsername("kyjin");
		dataSource.setPassword("kyjin00");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
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
