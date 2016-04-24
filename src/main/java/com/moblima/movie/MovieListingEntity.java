package com.moblima.movie;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieListingEntity implements Serializable{
	private ArrayList<MovieListing> movielistingArray;
	
	public MovieListingEntity(){
		movielistingArray = new ArrayList<MovieListing>();
	}
	
	public ArrayList<MovieListing> getMovielisting() {
		return movielistingArray;
	}

	public void setMovielisting(ArrayList<MovieListing> movielistingArray) {
		this.movielistingArray = movielistingArray;
	}
	
}
