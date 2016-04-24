package com.moblima.loginsystem;

import java.io.Serializable;

import com.moblima.users.Authority;

public class Account implements Serializable{
	private String username;
	private String password;
	private Authority authority;
	public Account(String username,String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Authority getAuthority() {
		return authority;
	}
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	

}
