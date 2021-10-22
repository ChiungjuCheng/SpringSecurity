package com.example.security.demo.repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.example.security.demo.entity.UserInfoEntity;

@Repository
public class UserInfoRepository {
	
	private Set<UserInfoEntity> userInfoEntitySet = new HashSet<UserInfoEntity>();
	
	public UserInfoRepository() {
		
		UserInfoEntity user1 = new UserInfoEntity("user1","123");
		UserInfoEntity user2 = new UserInfoEntity("user2","123");
		userInfoEntitySet.add(user1);
		userInfoEntitySet.add(user2);				
	}
	
	/**
	 * 利用使用者的帳號找到該使用者的帳號密碼
	 */
	public UserInfoEntity getUserByName(String name) {
		
		Optional<UserInfoEntity> userInfoEntityOpt = userInfoEntitySet.stream()
													.filter(userInfo->userInfo.getName().equals(name))
													.findFirst();

		if(userInfoEntityOpt.isEmpty()) {
			return null;
		}
				
		return userInfoEntityOpt.get();
		
	}

}
