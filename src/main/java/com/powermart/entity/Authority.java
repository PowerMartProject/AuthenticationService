package com.powermart.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
	
	private int id;
	
	private String role;
	
	public List<User> users;

	public Authority(String role) {
		super();
		this.role = role;
	}
	
}
