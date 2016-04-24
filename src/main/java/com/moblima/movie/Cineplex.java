package com.moblima.movie;

import java.io.Serializable;
import java.util.ArrayList;

public class Cineplex implements Serializable{
	private String name;
	private ArrayList<Cinema> cinemaArray;

	public Cineplex(String name) {
		this.name = name;
		this.cinemaArray = new ArrayList<Cinema>();
		for (int i = 0; i < 2; i++) {
			cinemaArray.add(new Cinema((i+1), Cinema.CINEMA_CLASS_REGULAR));
		}
		cinemaArray.add(new Cinema(2, Cinema.CINEMA_CLASS_PLATINIUM));
	}

	public ArrayList<Cinema> getCinema() {
		return cinemaArray;
	}
	
	public void setCinema(ArrayList<Cinema> cinemaArray) {
		this.cinemaArray = cinemaArray;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
