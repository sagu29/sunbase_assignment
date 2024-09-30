package com.cms.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.dto.SunbaseLoginResponse;
import com.cms.entity.Customer;
import com.cms.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@PostMapping()
	public ResponseEntity<Customer> create(@RequestBody Customer customer) {
		if (customer.getUuid() == null || customer.getUuid() == "")
			customer.setUuid(UUID.randomUUID().toString());
		Customer cust = customerService.create(customer);
		return ResponseEntity.ok(cust);
	}

	@PutMapping()
	public ResponseEntity<Customer> update(@RequestBody Customer customer) {
		Customer cust = customerService.update(customer);
		return ResponseEntity.ok(cust);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
		Customer cust = customerService.findById(id);
		return ResponseEntity.ok(cust);
	}

	@GetMapping()
	public ResponseEntity<Page<Customer>> getCustomers(Pageable pageable, @RequestParam(required = false) String column,
			@RequestParam(required = false) String search) {
		Page<Customer> customers = customerService.findAll(pageable, column, search);
		return ResponseEntity.ok(customers);
	}

	@PatchMapping("/sync")
	public ResponseEntity<String> syncCustomers(@RequestBody SunbaseLoginResponse token) throws IOException {
		customerService.syncAll(token.getAccess_token());

		return ResponseEntity.ok("data sync successfull");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		String message = customerService.delete(id);
		return ResponseEntity.ok(message);
	}

}