package com.moblima.transaction;


import java.io.Serializable;
import java.util.ArrayList;

public class TransactionEntity implements Serializable {
	private ArrayList<Transaction> trans;
	public TransactionEntity() {
		trans = new ArrayList<Transaction>();
	}
	public ArrayList<Transaction> getTransaction() {
		return trans;
	}
	public void setTransaction(ArrayList<Transaction> trans) {
		this.trans = trans;
	}
	

}
