package com.moblima.exception;

public class PublicHolidayException extends Exception {
	public PublicHolidayException() {
		super("Public Holiday Exception");
	}
	public PublicHolidayException(String message){
		super(message);
	}
}
