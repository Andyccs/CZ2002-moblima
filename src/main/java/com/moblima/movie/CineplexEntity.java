package com.moblima.movie;

import java.io.Serializable;
import java.util.ArrayList;

public class CineplexEntity implements Serializable{
	ArrayList<Cineplex> cineplexArray;
	
	public CineplexEntity(){
		cineplexArray = new ArrayList<Cineplex>();
	}
	public ArrayList<Cineplex> getCineplex() {
		return cineplexArray;
	}
	public void setCineplex(ArrayList<Cineplex> cineplexArray) {
		this.cineplexArray = cineplexArray;
	}
	
}
