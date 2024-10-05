package com.powermart.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDetails {
	
	private String firstName;
	
	private String lastName;
	
	private String contactNumber;
	
	private String gender;
	
	private LocalDate dob;

}
