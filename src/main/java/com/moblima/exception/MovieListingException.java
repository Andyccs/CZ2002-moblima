package com.moblima.exception;

public class MovieListingException extends Exception{
	public MovieListingException(){
		super("Movie Listing Exception");
	}
	public MovieListingException(String message){
		super(message);
	}
}
