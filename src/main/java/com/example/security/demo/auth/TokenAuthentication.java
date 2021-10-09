package com.example.security.demo.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.example.security.demo.entity.UserInfoEntity;

/**
 * 要傳入TokenAuthenticationProvider做驗證的物件
 * 在到達TokenAuthenticationProvider之前，會被建立出來，然後利用filter傳到TokenAuthenticationProvider內
 * @author user
 */
public class TokenAuthentication implements Authentication{
	
	/** 使用者資訊*/
	private UserInfoEntity userInfoEntity;
	
	public TokenAuthentication(UserInfoEntity userInfoEntity) {
		this.userInfoEntity = userInfoEntity;
	}

	@Override
	public String getName() {
		return userInfoEntity.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	

	/** 回傳使用者資訊*/
	@Override
	public String getCredentials() {
		return userInfoEntity.getPassword();
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
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
	
}
