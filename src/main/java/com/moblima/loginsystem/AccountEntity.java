package com.moblima.loginsystem;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountEntity implements Serializable {
	private ArrayList<Account> accounts;
	public AccountEntity() {
		accounts = new ArrayList<Account>();
	}
	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

}
