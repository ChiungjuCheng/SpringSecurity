package com.example.security.demo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.demo.auth.CustomAccessDecisionVoter;
import com.example.security.demo.auth.JwtAuthFilter;
import com.example.security.demo.auth.LoginAuthenticationProcessingFilter;
import com.example.security.demo.auth.TokenAuthenticationProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenAuthenticationProvider authProvider;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		

		http.csrf().disable().authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/test/auth/user1").hasRole("ADMIN") // TODO 沒有作用
				.accessDecisionManager(getAccessDecisionManager())
				.anyRequest().authenticated();
		
		// 驗證
		http.addFilterBefore(getLoginAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

		// And filter other requests to check the presence of JWT in header
		http.addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * login filter
	 * @return
	 * @throws Exception
	 */
	public LoginAuthenticationProcessingFilter getLoginAuthenticationProcessingFilter() throws Exception {
		return new LoginAuthenticationProcessingFilter("/login", authenticationManager());
	}

	/**
	 * 設定AccessDecisionManager使用的voter
	 * @return
	 */
	@Bean
	public AccessDecisionManager getAccessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
		// TODO 設定RoleVoter的ConfigAttribute (參考4_5_5)
//		decisionVoters.add(new AuthenticatedVoter());
		decisionVoters.add(new RoleVoter());

		decisionVoters.add(new CustomAccessDecisionVoter()); 
		return new UnanimousBased(decisionVoters);
	}

}
