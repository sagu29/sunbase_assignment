package com.cms.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Customer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cust_id;
	
	
	private String first_name;

	private String last_name;
	
	private String street;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String email;
	
	private String phone;

}
