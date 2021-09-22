package com.example.security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

	
	@GetMapping("/users/adminPage")
	public String adminPage() {		
		return "admin";
	}
	
	@GetMapping("/public")
	public String publicPage() {
		
		return "public";
	}
}
