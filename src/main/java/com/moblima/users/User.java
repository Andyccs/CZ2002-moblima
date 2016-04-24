package com.moblima.users;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.moblima.exception.CineplexException;
import com.moblima.exception.MovieListingException;
import com.moblima.exception.ShowTimeException;
import com.moblima.movie.Cineplex;
import com.moblima.movie.CineplexEntityConnector;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieListing.Status;
import com.moblima.movie.MovieListingEntityConnector;
import com.moblima.movie.ShowTime;
import com.moblima.movie.ShowTimeEntityConnector;
import com.moblima.transaction.MoviePriceEntityConnector;



public class User{
	//attribute
	private Authority usrAuthority;
	
	public User() {
		// TODO Auto-generated constructor stub
		usrAuthority = null;
	}
	
	//Accessor
	public Authority getUsrAuthority() {
		return usrAuthority;
	}
	
	//Mutator
	public void setUsrAuthority(Authority usrAuthority) {
		this.usrAuthority = usrAuthority;
	}
	
	
	public ArrayList<MovieListing> queryMovie(Status status) throws MovieListingException
	{
		ArrayList<MovieListing> movieList = new ArrayList<MovieListing>();
		MovieListingEntityConnector querymovie = new MovieListingEntityConnector();
		movieList = querymovie.queryMovie(status);
		if(movieList==null||movieList.size()==0)
			throw new MovieListingException("No movie found");
		return movieList;
	}
	
	public ArrayList<ShowTime> queryMovie(String movieName, String cineplexName, GregorianCalendar dateTime) throws MovieListingException, ShowTimeException
	{ 
		ArrayList<ShowTime> movieShowTime  = new ArrayList<ShowTime>();
		ShowTimeEntityConnector queryMovie = new ShowTimeEntityConnector();
		
		
		movieShowTime = queryMovie.queryShowTime(dateTime, cineplexName, movieName);
		if(movieShowTime==null||movieShowTime.size()==0)
			throw new MovieListingException("No movie found");
		return movieShowTime;
		
		
	}
	
	public ArrayList<Cineplex> getCineplex() throws CineplexException{
		CineplexEntityConnector connector = new CineplexEntityConnector();
		ArrayList<Cineplex> cineplexes = connector.queryCineplex();
		if(cineplexes==null||cineplexes.size()==0)
			throw new CineplexException("No cineplex found");
		return cineplexes;
	}
	
	public double getPrice(String movieType){
		MoviePriceEntityConnector priceConnector = new MoviePriceEntityConnector();
		return priceConnector.getPrice(movieType);
	}
}
