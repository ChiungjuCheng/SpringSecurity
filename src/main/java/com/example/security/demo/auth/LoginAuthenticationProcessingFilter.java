package com.example.security.demo.auth;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.example.security.demo.entity.UserInfoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 設定有哪些請求需要被驗證
 * 當有request要求驗證時，此AuthenticationProcessingFilter就會負責從request拿取資料並且創立Authentication物件
 */
public class LoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	// TODO improving it by using filter
	@Autowired
	JwtService jwtService;

	private Logger LOG = Logger.getLogger(LoginAuthenticationProcessingFilter.class.getName());

	/**
	 * 設定有哪些url視為要求驗證 constructor是用來設定有哪些請求適用這個filter
	 * 
	 * @param url
	 * @param authManager
	 */
	public LoginAuthenticationProcessingFilter(String defaultFilterProcessesUrl,
			AuthenticationManager authenticationManager) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(authenticationManager);
	}

	/**
	 * 將請求轉換成自定義的Authentication子類別
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		LOG.info("AbstractAuthenticationProcessingFilter attemptAuthentication.......");

		UserInfoEntity creds = new ObjectMapper().readValue(request.getInputStream(), UserInfoEntity.class);

		// 開始驗證，並回傳結果
		return getAuthenticationManager().authenticate(new TokenAuthentication(creds));

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		LOG.info("AbstractAuthenticationProcessingFilter successfulAuthentication..... creating token");

		jwtService.addAuthentication(response, authResult.getName());
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authResult);
		SecurityContextHolder.setContext(context);

		chain.doFilter(request, response);
	}

}
