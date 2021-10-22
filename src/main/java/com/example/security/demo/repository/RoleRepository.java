package com.example.security.demo.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.security.demo.entity.RoleEntity;

@Repository
public class RoleRepository {
	
	private Set<RoleEntity> roleEntitySet = new HashSet<RoleEntity>();
	
	public RoleRepository() {
		
		RoleEntity role1 = new RoleEntity("user1","ROLE_ADMIN");
		RoleEntity role2 = new RoleEntity("user2","ROLE_GENERAL");
		
		roleEntitySet.add(role1);
		roleEntitySet.add(role2);
	}
	
	public List<RoleEntity> getRoleByName(String name) {
		List<RoleEntity> result = roleEntitySet.stream()
												.filter(roleEntity->roleEntity.getName().equals(name))
												.collect(Collectors.toList());
		
		return result;
	}

}
