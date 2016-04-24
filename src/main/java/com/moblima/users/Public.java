package com.moblima.users;


import com.moblima.exception.LoginException;
import com.moblima.loginsystem.AccountEntityConnector;
import com.moblima.loginsystem.CustomerAccount;
import com.moblima.loginsystem.LoginSystem;




public class Public extends User{
	public Public() {
		this.setUsrAuthority(Authority.Public);
	}
	
	/**
	 * add an customer account into the database
	 * @param username the username of the account
	 * @param password the password of the account
	 * @param name the name of the customer
	 * @param age the age of the customer
	 * @param mobileNumber the mobile number of the customer, no error checking
	 * @param email the email address of the customer, no error checking
	 */
	public boolean registerAccount(String username, String password,String name,int age,String mobileNumber, String email) {
		AccountEntityConnector connector = new AccountEntityConnector();
		boolean status = connector.addAccount(new CustomerAccount(username, password,name,age,mobileNumber,email));
		if(!status){
			throw new NullPointerException("Registration failed");
		}
		return status;
	}
	
	/**
	 * search for the account in the database using LoginSystem class
	 * @return return the type of person after knowing the type of the account. 
	 * If account is not found, Public class will be returned
	 * @throws LoginException 
	 */
	public User login(String username, String password) throws LoginException {
		LoginSystem loginSystem = new LoginSystem();
		User person = loginSystem.authenticate(username, password);
		return person;
	}
}
