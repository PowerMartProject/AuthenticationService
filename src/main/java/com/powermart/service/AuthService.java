package com.powermart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.powermart.dto.UserDetailsDto;
import com.powermart.dto.UserSignUpDto;
import com.powermart.entity.User;
import com.powermart.proxy.UserManagementClient;

@Service
public class AuthService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserManagementClient userManagementClient;
	
	public ResponseEntity<String> signUp(UserSignUpDto userSignUpDto) {
		userSignUpDto.setPassword(passwordEncoder.encode(userSignUpDto.getPassword()));
		ResponseEntity<String> email = userManagementClient.signUp(userSignUpDto);
		return email;
	}
	
	public UserDetailsDto authenticateUser(User user) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword()));
		
		UserDetailsDto userDetails = userManagementClient.getUserDetails(user.getEmailId());
		return userDetails;
	}
	

}
