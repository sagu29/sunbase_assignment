package com.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.entity.Customer;
import com.cms.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
		
		@Autowired
	  private CustomerService cs;
	  
	
		@PostMapping
    	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
			Customer cust = cs.createCustomer(customer);
			return new ResponseEntity<>(cust, HttpStatus.CREATED);
		}
	
	 	@PutMapping("/{id}")
	    public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
		 Customer cust = cs.updateCustomer(id, customer);
		 return new ResponseEntity<>(cust, HttpStatus.OK);
	    }
	 	
	 	@GetMapping("/{id}")
	    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
	 		Customer cust = cs.getCustomerById(id);
	        return new ResponseEntity<>(cust, HttpStatus.OK);
	    }
	 	
	 	@GetMapping
	    public ResponseEntity<Page<Customer>> getCustomers(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy) {
	        Page<Customer> cust= cs.getCustomers(page, size, sortBy);
	 		return new ResponseEntity<>(cust, HttpStatus.OK);
	 	}
	 	
	 	@DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteCustomer(@PathVariable int id) {
	        cs.deleteCustomer(id);
	        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
	    }

	 
	 
	  


}
