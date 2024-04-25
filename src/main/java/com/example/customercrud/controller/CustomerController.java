package com.example.customercrud.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RestController;

import com.example.customercrud.dto.CredentialRequestDTO;
import com.example.customercrud.dto.CustomerRequestDTO;
import com.example.customercrud.dto.CustomerResponseDTO;
import com.example.customercrud.entity.Customer;
import com.example.customercrud.entity.Token;
import com.example.customercrud.mapper.CustomerMapper;
import com.example.customercrud.repository.CustomerRepository;
import com.example.customercrud.service.CustomerJwtService;
import com.example.customercrud.service.CustomerService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@SecurityRequirement(name = "Authorization")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerJwtService customerJwtService;
	
	@PostMapping("/signup")
	public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customer) {
		if (customerService.checkCustomer(customer.getEmail())) {
			throw new RuntimeException("Email Already Exists.");
		} else {
			var user = CustomerMapper.modelMapper(customer);
			var result = customerService.saveCustomer(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.dtoMapper(result));
		}
	}
	
	@PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Token login(@RequestBody CredentialRequestDTO credentialRequestDTO ) {
		var email = credentialRequestDTO.getEmail();
		
        var customerEmail = customerJwtService.loadUserByUsername(email);
        var token = customerJwtService.generateToken(String.valueOf(customerEmail));
        
        var expiryTime = (customerJwtService.EXPIRATION_TIME_MS/60)/1000;
        var message = "Login Successful!! Your token will be expired in "+expiryTime+" minutes";
        
        return new Token(token, message);
    }
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam String email) {
		customerService.deleteCustomer(email);
		return ResponseEntity.status(HttpStatus.OK).body("Successfully Deleted");
	} 
	
	@GetMapping("/home")
	public String customerHome() {
		return "Welcome to Customer CRUD Project.";
	}
	
	@GetMapping("/allCustomers")
	public List<Customer> allCustomers() {
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/byAge")
	public List<Customer> customersByAge(@RequestParam int age) {
		return customerService.getCustomersByAge(age);
	}
	
	@GetMapping("/byName")
	public List<Customer> customersByName(@RequestParam String name) {
		return customerService.getCustomersByName(name);
	}
	
	@GetMapping("/byAddress")
	public List<Customer> customersByAddress(@RequestParam String address) {
		return customerService.getCustomersByAddress(address);
	}
	
	@GetMapping("/byEmail")
	public Optional<Customer> customerByEmail(@RequestParam String email) {
		return customerService.getCustomerByEmail(email);
	}
	
	@GetMapping("/byId")
	public Optional<Customer> customerById(@RequestParam Long id) {
		return customerService.getCustomerById(id);
	}
	
}
