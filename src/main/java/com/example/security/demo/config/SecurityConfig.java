package com.example.security.demo.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// 設定需要驗證的url
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		
//		http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/**").authenticated().antMatchers(HttpMethod.GET)
//				.permitAll().antMatchers(HttpMethod.POST, "/users").permitAll().anyRequest().authenticated().and()
//				.csrf().disable().formLogin();
//	}

	// 設定使用者帳號、密碼和角色
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		UserBuilder user = User.withDefaultPasswordEncoder();
//
//		auth.inMemoryAuthentication().withUser(user.username("test1").password("123").roles("ADMIN"));
//
//	}

}
