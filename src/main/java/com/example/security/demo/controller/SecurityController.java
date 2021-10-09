package com.example.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

	
	@GetMapping("/adminPage")
	public String adminPage() {
		
		return "admin";
	}
}
