package com.example.security.demo.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 檢查每一個request
 * 
 * @author user
 */
public class JwtAuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Authentication authentication = JwtService.getAuthentication((HttpServletRequest) request,
				(HttpServletResponse) response);

		SecurityContextHolder.getContext().setAuthentication(authentication); // 查一下好不

		filterChain.doFilter(request, response);
	}

}
