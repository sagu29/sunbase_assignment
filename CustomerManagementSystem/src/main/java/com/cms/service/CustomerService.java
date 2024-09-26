package com.cms.service;



import org.springframework.data.domain.Page;

import com.cms.entity.Customer;

public interface CustomerService {
	
	public Customer createCustomer(Customer customer);
	
	 public Customer updateCustomer(int id, Customer customer);
	 
	 public String deleteCustomer(int id);
	 
	 public Customer getCustomerById(int id);
	 
	 public Page<Customer> getCustomers(int page, int size, String sortBy);
	 
	 

}
