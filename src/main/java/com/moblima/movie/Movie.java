package com.moblima.movie;

import java.io.Serializable;

public class Movie implements Serializable{
	public static final String TYPE_REGULAR = "Regular movie";
	public static final String TYPE_DIGITAL = "Digital movie";
	public static final String TYPE_3D = "3D movie";
	
	public static final String RATING_PG="PG-13";
	public static final String RATING_U="U";
	public static final String RATING_18="18";
	
	private String name;
	private String type;
	private String rating;
	
	public Movie (String name, String type, String rating) {
		this.name = name;
		this.type = type;
		this.rating = rating;
	}
	
	@Override
	public String toString() {
		return "Movie name: "+name+"\n"
				+"Movie type: "+type+"\n"
				+"Movie rating: "+rating+"\n";
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}

}
