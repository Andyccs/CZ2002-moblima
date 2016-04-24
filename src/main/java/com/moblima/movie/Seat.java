package com.moblima.movie;

import java.io.Serializable;

public class Seat implements Serializable{
	private boolean available;
	
	public Seat (boolean available) {
		this.available = available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public boolean getAvailable() {
		return available;
	}
}
