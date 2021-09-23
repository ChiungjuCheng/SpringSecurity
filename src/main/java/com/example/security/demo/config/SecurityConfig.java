package com.example.security.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource securityDataSource;
	
//	 設定使用者帳號、密碼和角色
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}

	// 設定需要驗證的url
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		
//		http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/**").authenticated().antMatchers(HttpMethod.GET)
//				.permitAll().antMatchers(HttpMethod.POST, "/users").permitAll().anyRequest().authenticated().and()
//				.csrf().disable().formLogin();
//	}



}
