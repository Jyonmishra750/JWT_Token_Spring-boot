package com.example.customercrud.mapper;

import org.springframework.beans.BeanUtils;

import com.example.customercrud.dto.CustomerRequestDTO;
import com.example.customercrud.dto.CustomerResponseDTO;
import com.example.customercrud.entity.Customer;

public class CustomerMapper {

	private CustomerMapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static Customer modelMapper(CustomerRequestDTO dto) {
        var customer = new Customer();
        BeanUtils.copyProperties(dto, customer);

        return customer;
    }
	
	public static CustomerResponseDTO dtoMapper(Customer customer) {
		return new CustomerResponseDTO(
				customer.getId(),
				customer.getName(),
				customer.getEmail(),
				customer.getPassword(),
				customer.getAge(),
				customer.getAddress());
	}
}
