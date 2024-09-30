package com.cms.service;



import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cms.entity.Customer;

public interface CustomerService {
	
	public Customer create(Customer customer);
	public Customer update(Customer customer);
	public Customer findById(String id);
	public Page<Customer> findAll(Pageable pageable, String columnName, String searchKeyword);
	public String delete(String id);
	
	public void syncAll(String token)throws IOException;

	public String sunbaselogin(String loginId, String password);
	 
	 

}
