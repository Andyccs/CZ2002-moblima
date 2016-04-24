package com.moblima.loginsystem;

import java.io.Serializable;

import com.moblima.users.Authority;

public class StaffAccount extends Account implements Serializable{
	
	public StaffAccount(String username, String password) {
		super(username, password);
		setAuthority(Authority.Customer);
	}

}