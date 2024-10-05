package com.powermart.dto;

import java.util.List;

import com.powermart.entity.Address;
import com.powermart.entity.Authority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
	
	private String emailId;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private List<Address> addressList;
	
	private List<Authority> authorities;

}
