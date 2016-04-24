package com.moblima.movie;

import java.io.Serializable;

public class Cinema implements Serializable {
	public static final String CINEMA_CLASS_REGULAR = "Regular Movie Suites";
	public static final String CINEMA_CLASS_PLATINIUM = "Platinium Movie Suites";
	
	private int id;
	private String cinemaClass;

	public Cinema (int id, String cinemaClass) {
		this.setId(id);
		this.setCinemaClass(cinemaClass);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCinemaClass() {
		return cinemaClass;
	}

	public void setCinemaClass(String cinemaClass) {
		this.cinemaClass = cinemaClass;
	}

	
}
