package com.example.customercrud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.customercrud.dto.CustomerRequestDTO;
import com.example.customercrud.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Optional<Customer> findByEmail(String email);
	
	List<Customer> findByAge(int age);
	
	List<Customer> findByName(String name);
	
	List<Customer> findByAddress(String address);
	
	Customer findByEmailAndPassword(String email, String password);
	
	@Transactional
	void deleteByEmail(String email);

	Customer save(CustomerRequestDTO customer);
	
	boolean existsByEmail(String email);
}
