package com.example.security.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.security.demo.auth.LoginAuthenticationProcessingFilter;
import com.example.security.demo.auth.TokenAuthenticationProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private TokenAuthenticationProvider authProvider;
 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		http.csrf().disable()
			.authorizeRequests().antMatchers("/login").permitAll()
			.anyRequest().authenticated();
		
		
//				// 第一個參數先 第二個參數後 驗證過後需要
				http.addFilterBefore(getLoginAuthenticationProcessingFilter(),
						UsernamePasswordAuthenticationFilter.class);
		
        // And filter other requests to check the presence of JWT in header
//        .addFilterBefore(new JWTAuthenticationFilter(),
//                UsernamePasswordAuthenticationFilter.class);
	}
	
	
	public LoginAuthenticationProcessingFilter getLoginAuthenticationProcessingFilter() {
		
		return new LoginAuthenticationProcessingFilter("/login",this.authenticationManager);
	}
	
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
