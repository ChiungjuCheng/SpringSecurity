package com.example.security.demo.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.example.security.demo.entity.UserInfoEntity;

/**
 * 要傳入TokenAuthenticationProvider做驗證的物件
 * 在到達TokenAuthenticationProvider之前，會被建立出來，然後利用filter傳到TokenAuthenticationProvider內
 * @author user
 */
public class TokenAuthentication extends AbstractAuthenticationToken{
	
	private Object principal;

	private Object credentials;
	
	public TokenAuthentication(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}
		
	public TokenAuthentication(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
	}

	

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal.toString();
	}
	
}
