package com.moblima.movie;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowTimeEntity implements Serializable {
	private ArrayList<ShowTime> showTimeArray;
	
	public ShowTimeEntity() {
		showTimeArray = new ArrayList<ShowTime>();
	}

	public ArrayList<ShowTime> getShowTime() {
		return showTimeArray;
	}
}
