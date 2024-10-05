package com.powermart.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.powermart.dto.UserDetailsDto;
import com.powermart.entity.Authority;
import com.powermart.proxy.UserManagementClient;

@Service
public class SecurityUser implements UserDetailsService {
	
	@Autowired
	UserManagementClient userManagementClient;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetailsDto userDetails = userManagementClient.getUserDetails(username);
		
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		List<Authority> list = userDetails.getAuthorities();
		for(Authority authority : list) {
			authorities.add(new SimpleGrantedAuthority(authority.getRole()));
		}
		
		return new org.springframework.security.core.userdetails.User(userDetails.getEmailId(), userDetails.getPassword(), authorities);
	}

}
