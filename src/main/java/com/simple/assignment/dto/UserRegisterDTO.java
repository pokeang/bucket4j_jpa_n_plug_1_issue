package com.simple.assignment.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.simple.assignment.model.Gender;
import com.simple.assignment.model.Role;

public class UserRegisterDTO {
	private long id;
	@NotBlank(message = "Frist name is required")
	@Size(min=3, message = "Frist name should have at least 3 characters")
	private String firstName;
	
	@NotBlank(message = "Last name is required")
	private String lastName;
	
	@NotBlank(message = "Email is required")
    @Email(message = "Email doesn't match format email")
	private String email;

	@NotBlank(message = "password is required")
	@Size(min=6, max=30, message = "Password should have at least 6~30 characters")
	private String password;
	
	@NotNull
	@NotBlank(message = "confirm password is required")
	private String confirmPassword;
	
	private Role role;
	private Gender gender;
	private String phoneNumber;
	private String city;
	private String address1;
	private String address2;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	
	
}
