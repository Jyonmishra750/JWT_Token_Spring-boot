package com.example.customercrud.dto;

import jakarta.validation.constraints.NotNull;

public class CustomerRequestDTO {
	@NotNull(message = "Name should not be Null")
	private String name;
	@NotNull(message = "Email should not be Null")
	private String email;
	@NotNull(message = "Password should not be Null")
	private String password;
	@NotNull(message = "Age should not be Null")
	private int age;
	@NotNull(message = "Address should not be Null")
	private String address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public CustomerRequestDTO(@NotNull(message = "Name should not be Null") String name,
			@NotNull(message = "Email should not be Null") String email,
			@NotNull(message = "Password should not be Null") String password,
			@NotNull(message = "Age should not be Null") int age,
			@NotNull(message = "Address should not be Null") String address) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
		this.address = address;
	}
	public CustomerRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
