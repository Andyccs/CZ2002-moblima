package com.moblima.loginsystem;


import com.moblima.exception.LoginException;
import com.moblima.users.Customer;
import com.moblima.users.Staff;
import com.moblima.users.User;


public class LoginSystem {

	public LoginSystem() {
		// TODO Auto-generated constructor stub
	}
	
	public User authenticate(String username,String password) throws LoginException{
		AccountEntityConnector dbConnector = new AccountEntityConnector();
		Account acc = dbConnector.getAccount(username, password);
		if(acc instanceof CustomerAccount){
			return new Customer(acc.getUsername());
		}
		else if(acc instanceof StaffAccount){
			return new Staff(acc.getUsername());
		}
		else{
			throw new LoginException();
		}
	}

}
