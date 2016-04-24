package com.moblima.exception;

public class CineplexException extends Exception{
	public CineplexException(){
		super("Login failed");
	}
	public CineplexException(String message){
		super(message);
	}
}
