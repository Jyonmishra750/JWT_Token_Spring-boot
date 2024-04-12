package com.example.customercrud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.customercrud.dto.CustomerResponseDTO;
import com.example.customercrud.entity.Customer;
import com.example.customercrud.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Customer saveCustomer(Customer user) {
		var bcrypt = passwordEncoder.encode(user.getPassword());
        user.setPassword(bcrypt);
		return customerRepository.save(user);
	}
	
	public Customer getCustomerByEmailAndPassword(String email, String password) {
		return customerRepository.findByEmailAndPassword(email, password);
	}
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	public List<Customer> getCustomersByAddress(String address){
		return customerRepository.findByAddress(address);
	}
	
	public Optional<Customer> getCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	public List<Customer> getCustomersByAge(int age) {
		return customerRepository.findByAge(age);
	}
	
	public List<Customer> getCustomersByName(String name) {
		return customerRepository.findByName(name);
	}
	
	public Optional<Customer> getCustomerById(Long id) {
		return customerRepository.findById(id);
	}
	
	public void deleteCustomer(String email) {
		customerRepository.deleteByEmail(email);	
	}
	
	public boolean checkCustomer(String email) {
		return customerRepository.existsByEmail(email);	
	}

}
