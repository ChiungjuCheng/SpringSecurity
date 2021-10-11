package com.example.security.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class ResourceController {

	@PostMapping("/generaluser")
	public String testUrl() {
		
		return "OK";	
	}
}
