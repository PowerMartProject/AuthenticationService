package com.powermart.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.powermart.dto.UserDetailsDto;
import com.powermart.dto.UserLoginDto;
import com.powermart.dto.UserSignUpDto;

@FeignClient(name = "USERMANAGEMENT")
public interface UserManagementClient {
	
	@PostMapping("/user/signup")
	public ResponseEntity<String> signUp(@RequestBody UserSignUpDto userSignUpDto);
	
	@PostMapping("/user/login")
	public ResponseEntity<UserDetailsDto> login(@RequestBody UserLoginDto userLoginDto);
	
	@GetMapping("/user/{email}")
	public UserDetailsDto getUserDetails(@PathVariable(name="email") String email);
}
