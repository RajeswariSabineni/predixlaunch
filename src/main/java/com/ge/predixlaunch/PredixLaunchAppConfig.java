package com.ge.predixlaunch;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * This configuration class has three responsibilities:
 * <ol>
 * <li>It enables the auto configuration of the Spring application context.</li>
 * <li>
 * It ensures that Spring looks for other components (controllers, services, and
 * repositories) from the <code>com.javaadvent.bootrest.todo</code> package.</li>
 * <li>It launches our application in the main() method.</li>
 * </ol>
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.ge.predixlaunch.*", "com.ge.predixlaunch.*"})
public class PredixLaunchAppConfig extends SpringBootServletInitializer {
	
	private static final Logger LOG = Logger.getLogger(PredixLaunchAppConfig.class);
	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(PredixLaunchAppConfig.class);
	}
	
	public static void main(String[] args) throws SQLException {
		
		LOG.info("................The main method of DigitalThreadAppConfig was called........");
		
		ApplicationContext applicationContext = SpringApplication.run(PredixLaunchAppConfig.class);
		
		
		/*String beanName[] = applicationContext.getBeanDefinitionNames();
		for(String bean: beanName){
			LOG.info("\n");
			LOG.info("--->>> BEAN NAME "+bean+" >>-------->> BEAN CLASS >>> "+applicationContext.getBean(bean).getClass().toString());
		}*/
		
	}
	
	
	@Value("${spring.datasource.primary.url}")
	String primaryUrl;
	
	@Value("${spring.datasource.primary.username}")
	String primaryUsername;
	
	@Value("${spring.datasource.primary.password}")
	String primaryPassword;
	
	@Value("${spring.datasource.primary.driverClassName}")
	String primaryDriverName;
	
/*	@Value("${spring.datasource.primary.max-active}")
	String primaryMaxActive;
	
	@Value("${spring.datasource.primary.max-idle}")
	String primaryMaxIdle;*/
	
	@Bean(name="primaryDataSource")
	@Primary
	//@ConfigurationProperties(prefix="spring.datasource")
	public DataSource primaryDataSource(){
		LOG.info(" primaryUrl " + primaryUrl + " primaryUsername " + primaryUsername);
		DataSource ds = DataSourceBuilder.create().url(primaryUrl).username(primaryUsername).password(primaryPassword).driverClassName(primaryDriverName).build();
		LOG.info("The Primary datasource that was created is " + ds.toString());
		return ds;
	}
	
	
	@Bean(name="primaryJdbcTemplate")
	public JdbcTemplate primaryJdbcTemplate(){
		 JdbcTemplate jdbcTemplate = new JdbcTemplate(primaryDataSource());
		 LOG.info("primaryJdbcTemplate is "+jdbcTemplate.toString());
		 return jdbcTemplate;
	}
}
