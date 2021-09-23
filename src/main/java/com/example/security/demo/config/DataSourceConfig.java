package com.example.security.demo.config;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:persistence-oracle.properties")
public class DataSourceConfig {
	
	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Bean
	public DataSource securityDataSource() {
		
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create(); 		
		// set driver
		dataSourceBuilder.driverClassName(env.getProperty("jdbc.driver"));
		// set url
		dataSourceBuilder.url(env.getProperty("jdbc.url"));
		// set user
		dataSourceBuilder.username(env.getProperty("jdbc.user"));
		// set password
		dataSourceBuilder.password(env.getProperty("jdbc.password"));
		
		return dataSourceBuilder.build();
	}
	
}
