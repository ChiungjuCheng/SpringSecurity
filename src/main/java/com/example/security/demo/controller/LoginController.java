package com.example.security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	/**
	 * 登入頁面
	 * @return
	 */
	@GetMapping("/customLoginPage")
	public String login() {
		
		return "customlogin";
	}
	
	/**
	 * 登入頁面
	 * @return
	 */
	@GetMapping("/loginSuccess")
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
