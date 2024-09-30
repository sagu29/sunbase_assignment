package com.cms.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.cms.dto.Logindto;
import com.cms.entity.Customer;
import com.cms.exception.cmsException;
import com.cms.repository.CustomerRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;;


@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepo;

	private RestTemplate restTemplate;

	private ObjectMapper objectMapper;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepo, RestTemplate restTemplate,ObjectMapper objectMapper) {
		super();
		this.customerRepo = customerRepo;
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	@Override
	public Customer create(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public Customer update(Customer customer) {
		customerRepo.findById(customer.getUuid())
				.orElseThrow(() -> new cmsException("invalid customer id :" + customer.getUuid()));
		return customerRepo.save(customer);
	}

	@Override
	public Customer findById(String id) {
		return customerRepo.findById(id).orElseThrow(() -> new cmsException("invalid customer id : " + id));
	}

	@Override
	public Page<Customer> findAll(Pageable pageable, String columnName, String searchKeyword) {
		if (columnName != null && searchKeyword != null) {
			return customerRepo.findByColumnNameAndKeyword(columnName, searchKeyword, pageable);
		} else {
			return customerRepo.findAll(pageable);
		}
	}

	@Override
	public String delete(String id) {
		customerRepo.findById(id).orElseThrow(() -> new cmsException("invalid customer id : " + id));
		customerRepo.deleteById(id);
		return "Customer Record Deleted Successfully";
	}

	@Override
	public void syncAll(String token) throws IOException {
		String path = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp";
		String url = UriComponentsBuilder.fromHttpUrl(path)
				.queryParam("cmd", "get_customer_list").toUriString();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		
		HttpEntity<String> entity = new HttpEntity<>(headers);

		// Make the GET request
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		List<Customer> customers = objectMapper.readValue(response.getBody(), new TypeReference<List<Customer>>() {});
		
		customers.forEach(c->createOrupdate(c));
		
	}

	
	public void createOrupdate(Customer customer) {
		Optional<Customer> findById = customerRepo.findById(customer.getUuid());
		if(findById.isPresent()) {
			update(customer);
		}else {
			create(customer);
		}
	}
	
	@Override
	public String sunbaselogin(String loginId, String password) {

		String url = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
		Logindto logindto = new Logindto(loginId, password);
		System.out.println(logindto);
		String response = restTemplate.postForObject(url, logindto, String.class);

		JSONObject jsonObject = new JSONObject(response);

		String accessToken = jsonObject.getString("access_token");

		return accessToken;
	}

}