package com.moblima.loginsystem;

import java.io.Serializable;

import com.moblima.users.Authority;

public class CustomerAccount extends Account implements Serializable{
	private int age;
	private String name;
	private String mobileNumber;
	private String email;
	public CustomerAccount(String username, String password,String name,int age, String mobileNumber,String email) {
		super(username, password);
		this.age = age;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.email = email;
		setAuthority(Authority.Customer);
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
