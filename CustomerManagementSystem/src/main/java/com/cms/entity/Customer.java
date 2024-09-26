package com.cms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class Customer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cust_id;
	
	@NotNull(message = "Fill valid first name")
	private String first_name;
	
	@NotNull(message = "Fill valid last name")
	private String last_name;
	
	@NotNull(message = "Fill valid street name")
	private String street;
	
	@NotNull(message = "Fill valid address")
	private String address;
	
	@NotNull(message = "Fill valid city")
	private String city;
	
	@NotNull(message = "Fill valid State")
	private String state;
	
	@NotNull(message = "Fill valid email")
	@Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$",message = "Input a valid email address")
	private String email;
	
	@NotNull(message = "Fill valid phone number")
	@Pattern(regexp = "[6789]{1}[0-9]{9}",message = "Input a valid mobile number")
	private String phone;

}
