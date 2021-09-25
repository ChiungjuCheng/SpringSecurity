package com.example.security.demo.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security.demo.entity.UserInfoEntity;
import com.example.security.demo.repository.UserInfoRepository;

@Service
public class DemoUserService implements UserDetailsService {
	
	private Logger LOG = Logger.getLogger(DemoUserService.class.getName());

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserInfoEntity userInfo = userInfoRepository.getUserByName(username);
		if (userInfo == null) {
			LOG.info("the user name is not exist");
			throw new UsernameNotFoundException(username);
		}

		return User.builder()
					.username(userInfo.getName())
					.password("{noop}" + userInfo.getPassword())
					.roles(userInfo.getRole())
					.build();
	}

}
