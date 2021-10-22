package com.example.security.demo.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * role table
 * @author user
 *
 */
public class RoleEntity implements GrantedAuthority{

	private String name;

	private String role;

	public RoleEntity() {
	}

	public RoleEntity(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}

}
