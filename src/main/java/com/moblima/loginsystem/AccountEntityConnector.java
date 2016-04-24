package com.moblima.loginsystem;

import com.moblima.util.Connector;

public class AccountEntityConnector extends Connector{
	private AccountEntity accountEntity;
	private static final String FILE_PATH="entities/accountEntity";
	
	public AccountEntityConnector() {
		
	}
	
	public boolean addAccount(Account account){
		String username = account.getUsername();
		String password = account.getPassword();
		accountEntity = (AccountEntity) openEntity(AccountEntity.class,FILE_PATH);
		for (Account acc : accountEntity.getAccounts()) {
			if(compareUsername(username, acc)&&comparePassword(password,acc)){
				return false;
			}
		}
		accountEntity.getAccounts().add(account);
		writeToFile(accountEntity, FILE_PATH);
		return true;
	}

	public Account getAccount(String username,String password){
		accountEntity = (AccountEntity) openEntity(AccountEntity.class,FILE_PATH);
		for (Account account : accountEntity.getAccounts()) {
			if(compareUsername(username, account)&&comparePassword(password, account)){
				writeToFile(accountEntity, FILE_PATH);
				return account;
			}
		}
		writeToFile(accountEntity, FILE_PATH);
		return null;
	}
	
	public Account getAccount(String username){
		accountEntity = (AccountEntity) openEntity(AccountEntity.class,FILE_PATH);
		for (Account account : accountEntity.getAccounts()) {
			if(compareUsername(username, account)){
				writeToFile(accountEntity, FILE_PATH);
				return account;
			}
		}
		writeToFile(accountEntity, FILE_PATH);
		return null;
	}
	
//	public void deleteAccount(){
//
//	}

	private boolean comparePassword(String password, Account account) {
		return account.getPassword().equals(password);
	}
	private boolean compareUsername(String username, Account account) {
		return account.getUsername().equals(username);
	}
}
