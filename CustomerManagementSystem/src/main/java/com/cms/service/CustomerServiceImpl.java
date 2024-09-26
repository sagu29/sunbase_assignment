package com.cms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cms.entity.Customer;
import com.cms.exception.cmsException;
import com.cms.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository cr;
	


	@Override
	public Customer createCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return cr.save(customer);
	}

	@Override
	public Customer updateCustomer(int id, Customer customer) {
		// TODO Auto-generated method stub
		Optional<Customer> existingCustomer = cr.findById(id);
        if (existingCustomer.isPresent()) {
            customer.setCust_id(id);
            return cr.save(customer);
        }
        throw new cmsException("Customer not found");
	}

	@Override
	public String deleteCustomer(int id) {
		// TODO Auto-generated method stub
		cr.deleteById(id);
		return "customer deleted successfully";
	}

	@Override
	public Customer getCustomerById(int id) {
		// TODO Auto-generated method stub
		return cr.findById(id).orElseThrow(() -> new cmsException("Customer not found"));
    };

	@Override
	public Page<Customer> getCustomers(int page, int size, String sortBy) {
		// TODO Auto-generated method stub
		return cr.findAll(PageRequest.of(page, size));
	}

}
