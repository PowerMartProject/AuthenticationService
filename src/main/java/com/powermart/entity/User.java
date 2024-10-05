package com.powermart.entity;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private long userId;
	
	private String emailId;
	
	private String password;
	
	private PersonalDetails personalDetails;
	
	private LocalDate signedUpDate;
	
	private List<Address> address;
	
	private List<Authority> authorities;
	
	

}
