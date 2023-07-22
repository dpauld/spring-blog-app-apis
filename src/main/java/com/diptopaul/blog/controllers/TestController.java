package com.diptopaul.blog.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	@GetMapping("/user")
	public String getCurrentUser(Authentication authentication) {
        String userName = authentication.getName();
        Object userDetails= authentication.getDetails();
        System.out.println(userDetails.toString());
        return "Hello, " + userName + "!";
    }
	
}
