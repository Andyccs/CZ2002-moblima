package com.moblima.users;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.moblima.exception.PublicHolidayException;
import com.moblima.exception.ShowTimeException;
import com.moblima.movie.Cineplex;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing.Status;
import com.moblima.movie.MovieListingEntityConnector;
import com.moblima.movie.ShowTimeEntityConnector;
import com.moblima.transaction.MoviePriceEntityConnector;
import com.moblima.transaction.PublicHoliday;
import com.moblima.transaction.RevenueReport;


public class Staff extends User{
	
	private String userName;

	public Staff() {
		this.setUsrAuthority(Authority.Staff);
		userName = null;
	}
	public Staff(String name) {
		this.setUsrAuthority(Authority.Staff);
		userName = name;
	}
	
	//Accessor
	public String getUserName()
	{
		return userName;
	}
	
	//Mutator
	public void setUserName(String name)
	{
		this.userName = name;
	}
	
	
	public void addHoliday(GregorianCalendar date) throws Exception {
		PublicHoliday holiday = new PublicHoliday();
		boolean status = holiday.addHoliday(date);
		if(!status){
			throw new PublicHolidayException("Cannot add the holiday");
		}
	}

	public void removeHoliday(GregorianCalendar date) throws Exception{
		PublicHoliday holiday = new PublicHoliday();
		boolean status = holiday.deleteHoliday(date);
		if(!status){
			throw new PublicHolidayException("Cannot remove the holiday");
		}
	}
	public LinkedList<GregorianCalendar> getHoliday() throws Exception{
		PublicHoliday holiday = new PublicHoliday();
		LinkedList<GregorianCalendar> temp = holiday.getPublicHoliday();
		if(temp==null||temp.size()==0){
			throw new PublicHolidayException("No holiday");
		}
		return temp;
	}
	public void addShowTime(GregorianCalendar dateTime, 
			Cineplex cineplex, Integer cinema, Movie movie) 
					throws ShowTimeException {
		ShowTimeEntityConnector addMovie = new ShowTimeEntityConnector();
		boolean status = addMovie.addShowTime(dateTime, cineplex, cinema, movie);
		if(!status){
			throw new ShowTimeException("Cannot add show time");
		}
		
	}
	public void removeShowTime(GregorianCalendar dateTime, 
			String cineplexName, String movieName, int cinemaId) throws ShowTimeException{
		boolean status = new ShowTimeEntityConnector().deleteShowTime(dateTime, cineplexName, movieName, cinemaId);
		if(!status){
			throw new ShowTimeException("Cannot remove show time");
		}
	}
	public void updateShowTime(
			GregorianCalendar odateTime, Cineplex ocineplex, Integer ocinema, Movie omovie,
			GregorianCalendar dateTime, Cineplex cineplex, Integer cinema, 
			Movie movie) throws ShowTimeException{
		boolean status = new ShowTimeEntityConnector().
				updateShowTime(odateTime, ocineplex, ocinema, omovie, 
						dateTime, cineplex, cinema, movie);
		if(!status){
			throw new ShowTimeException("Update failed due to: \n" +
					"1. Customer has brought the ticket for the original time slot OR\n"
					+"2. The new time slow crash with other time slot");
		}
		
	}
	
	public void addMovie(String name, String type, String rating, Status movieStatus) throws ShowTimeException
	{
		Movie movie = new Movie(name, type, rating);
		
		MovieListingEntityConnector addMovie = new MovieListingEntityConnector();
		
		boolean status = addMovie.addMovie(movie, movieStatus);
		
		if(!status){
			throw new ShowTimeException("Add movie failed");
		}
		
	}
	public void updateMovie(Movie movie, Status movieStatus) throws ShowTimeException
	{
		boolean status = new MovieListingEntityConnector().updateStatus(movie, movieStatus);
		if(!status){
			throw new ShowTimeException("Update movie failed");
		}
		
	}

	public void removeMovie(Movie movie) throws ShowTimeException
	{
		boolean status = new MovieListingEntityConnector().deleteMovie(movie);
		if(!status){
			throw new ShowTimeException("Remove movie failed");
		}
	}

	public String generateReportByMovie() 
	{
		String reportMovie;
		reportMovie = new RevenueReport().getReportByMovie();
		return reportMovie;
		
	}
	public String generateReportByCineplex() 
	{
		String reportCineplex;
		reportCineplex = new RevenueReport().getReportByCineplex();
		return reportCineplex;
		
	}
	public String generateReportByPeriod(int year) 
	{	
		String report;
		report = new RevenueReport().getReportByPeriod(year);
		return report;
		
	}
	
	public void updatePrice(String movieType,double price){
		new MoviePriceEntityConnector().changePrice(movieType, price);
	}
	
}
