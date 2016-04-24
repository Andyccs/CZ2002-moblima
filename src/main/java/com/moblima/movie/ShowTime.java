package com.moblima.movie;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class ShowTime implements Serializable{
	private GregorianCalendar dateTime;
	private Cineplex cineplex;
	private Integer cinemaId;
	private Movie movie;
	private SeatLayout layout;
		
	public ShowTime(GregorianCalendar dateTime, Cineplex cineplex,
			Integer cinemaId, Movie movie) {
		this.dateTime = dateTime;
		this.cineplex = cineplex;
		this.cinemaId = cinemaId;
		this.movie = movie;
		this.layout = new SeatLayout();
	}
	
	public SeatLayout getLayout() {
		return layout;
	}
	
	public void setLayout(SeatLayout layout) {
		this.layout = layout;
	}
	
	@Override
	public String toString() {
		return dateTime+"\t"+movie.getName()+"\t"+cineplex.getName();
	}
	
	public GregorianCalendar getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(GregorianCalendar dateTime) {
		this.dateTime = dateTime;
	}
	
	public Cineplex getCineplex() {
		return cineplex;
	}
	
	public void setCineplex(Cineplex cineplex) {
		this.cineplex = cineplex;
	}
	
	public Movie getMovie() {
		return movie;
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public int getNoOfCinema() {
		return cinemaId;
	}
	
	public void setNoOfCinema(int noOfCinema) {
		this.cinemaId = noOfCinema;
	}
	
}
