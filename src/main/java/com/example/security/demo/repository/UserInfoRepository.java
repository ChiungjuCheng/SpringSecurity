package com.example.security.demo.repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.security.demo.entity.UserInfoEntity;

@Service
public class UserInfoRepository {
	
	private Set<UserInfoEntity> userInfoEntityList = new HashSet<UserInfoEntity>();
	
	public UserInfoRepository() {
		
		UserInfoEntity user1 = new UserInfoEntity("user1","123","admin");
		UserInfoEntity user2 = new UserInfoEntity("user2","123","admin");
		userInfoEntityList.add(user1);
		userInfoEntityList.add(user2);				
	}
	
	/**
	 * 利用使用者的帳號找到該使用者的帳號密碼
	 */
	public UserInfoEntity getUserByName(String name) {
		
		Optional<UserInfoEntity> userInfoEntityOpt = userInfoEntityList.stream()
													.filter(userInfo->userInfo.getName().equals(name))
													.findFirst();

		if(userInfoEntityOpt.isEmpty()) {
			return null;
		}
				
		return userInfoEntityOpt.get();
		
	}

}
