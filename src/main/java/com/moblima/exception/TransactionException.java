package com.moblima.exception;

public class TransactionException extends Exception {
	public TransactionException(){
		super("Transaction Failed");
	}
	public TransactionException(String message){
		super(message);
	}
}
