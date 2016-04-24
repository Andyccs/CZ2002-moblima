package com.moblima.transaction;

import java.io.Serializable;

public class MoviePrice implements Serializable{
	double price;
	String movieType;
	
	public MoviePrice (String movieType, double price) {
		this.movieType = movieType;
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getMovietype() {
		return movieType;
	}
	
	public void setMovietype(String movietype) {
		this.movieType = movietype;
	}
}
