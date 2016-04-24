package com.moblima.transaction;

import java.io.Serializable;
import java.util.ArrayList;

public class MoviePriceEntity implements Serializable {
	private ArrayList<MoviePrice> moviePriceArray;

	public MoviePriceEntity() {
		moviePriceArray = new ArrayList	<MoviePrice>();
	}

	public ArrayList<MoviePrice> getPriceList() {
		return moviePriceArray;
	}
}
