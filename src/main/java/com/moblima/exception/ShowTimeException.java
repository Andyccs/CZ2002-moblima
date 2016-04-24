package com.moblima.exception;

public class ShowTimeException extends Exception {
	public ShowTimeException(){
		super("Show time exception");
	}
	public ShowTimeException(String message){
		super(message);
	}
}
