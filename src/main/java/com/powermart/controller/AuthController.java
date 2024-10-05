package com.powermart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.powermart.dto.UserDetailsDto;
import com.powermart.dto.UserSignUpDto;
import com.powermart.entity.Authority;
import com.powermart.entity.User;
import com.powermart.security.JwtService;
import com.powermart.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	JwtService jwtService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody UserSignUpDto userSignUpDto) {
		return authService.signUp(userSignUpDto);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		
		UserDetailsDto authenticatedUser = authService.authenticateUser(user);
		List<Authority> authorities = authenticatedUser.getAuthorities();
		List<String> roles = new ArrayList<>();
		for(Authority authority: authorities) {
			roles.add(authority.getRole());
		}
		String jwtToken = jwtService.generateToken(authenticatedUser.getEmailId(),roles);
		
		return jwtToken;
	}
	
	

}
