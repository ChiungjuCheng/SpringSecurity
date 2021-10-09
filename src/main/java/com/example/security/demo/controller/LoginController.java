package com.example.security.demo.controller;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.demo.auth.LoginAuthenticationProcessingFilter;


@RestController
public class LoginController {
	
	private Logger LOG = Logger.getLogger(LoginController.class.getName());
	
	/**
	 * 登入頁面
	 * @return
	 */
	@PostMapping("/login")
	public String loginSuccess() {		
		return "index";
	}
	
	/**
	 * 登出成功後的頁面
	 * @return
	 */
	@GetMapping("/logoutSuccess")
	public String logout() {	
		return "logoutPage";
	}
}
