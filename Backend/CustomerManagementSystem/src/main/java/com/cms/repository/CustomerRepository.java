package com.cms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cms.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer>,
PagingAndSortingRepository<Customer, String> {
default Page<Customer> findByColumnNameAndKeyword(String columnName, String searchKeyword, Pageable pageable) {
return findAll((root, query, criteriaBuilder) -> {
	if (columnName != null && searchKeyword != null) {
		return criteriaBuilder.like(criteriaBuilder.lower(root.get(columnName)),
				"%" + searchKeyword.toLowerCase() + "%");
	}
	return null;
}, pageable);
}

}
