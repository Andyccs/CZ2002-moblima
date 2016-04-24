package com.moblima.alltestingfiles;

import java.util.GregorianCalendar;

import com.moblima.loginsystem.AccountEntityConnector;
import com.moblima.loginsystem.CustomerAccount;
import com.moblima.loginsystem.StaffAccount;
import com.moblima.movie.Cineplex;
import com.moblima.movie.CineplexEntityConnector;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing.Status;
import com.moblima.movie.MovieListingEntityConnector;
import com.moblima.movie.ShowTimeEntityConnector;
import com.moblima.transaction.MoviePriceEntityConnector;
import com.moblima.transaction.TransactionEntityConnector;

public class Initialization {

	public static void main(String[] args) {
		CineplexEntityConnector cineplex = new CineplexEntityConnector();
		cineplex.addCineplex("The Cathay");
		cineplex.addCineplex("AMK Hub");
		cineplex.addCineplex("Causeway Point");
		
		MoviePriceEntityConnector price = new MoviePriceEntityConnector();
		price.addPrice(Movie.TYPE_REGULAR, 9.50);
		price.addPrice(Movie.TYPE_DIGITAL,9.50);
		price.addPrice(Movie.TYPE_3D, 11.00);
		
		MovieListingEntityConnector movieL = new MovieListingEntityConnector();
		movieL.addMovie(new Movie("Ender's Game",Movie.TYPE_3D,Movie.RATING_U), Status.NOW_SHOWING);
		movieL.addMovie(new Movie("Baby Blues",Movie.TYPE_DIGITAL,Movie.RATING_18), Status.NOW_SHOWING);
		movieL.addMovie(new Movie("Tom Yum 2",Movie.TYPE_REGULAR,Movie.RATING_PG), Status.NOW_SHOWING);
		
		AccountEntityConnector account = new AccountEntityConnector();
		account.addAccount(new StaffAccount("admin", "admin"));
		
		ShowTimeEntityConnector showtime = new ShowTimeEntityConnector();
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,10,0),
				new Cineplex("The Cathay"), 
				1, 
				new Movie("Ender's Game", Movie.TYPE_3D, Movie.RATING_U));
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,12,0),
				new Cineplex("The Cathay"), 
				1, 
				new Movie("Ender's Game", Movie.TYPE_3D, Movie.RATING_U));
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,14,0),
				new Cineplex("The Cathay"), 
				1, 
				new Movie("Ender's Game", Movie.TYPE_3D, Movie.RATING_U));
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,10,0),
				new Cineplex("The Cathay"), 
				2, 
				new Movie("Baby Blues", Movie.TYPE_DIGITAL, Movie.RATING_18));
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,12,0),
				new Cineplex("The Cathay"), 
				2, 
				new Movie("Baby Blues", Movie.TYPE_DIGITAL, Movie.RATING_18));
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,14,0),
				new Cineplex("The Cathay"), 
				2, 
				new Movie("Baby Blues", Movie.TYPE_DIGITAL, Movie.RATING_18));
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,10,0),
				new Cineplex("The Cathay"), 
				3, 
				new Movie("Tom Yum 2", Movie.TYPE_REGULAR, Movie.RATING_PG));
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,12,0),
				new Cineplex("The Cathay"), 
				3, 
				new Movie("Tom Yum 2", Movie.TYPE_REGULAR, Movie.RATING_PG));
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,14,0),
				new Cineplex("The Cathay"), 
				3, 
				new Movie("Tom Yum 2", Movie.TYPE_REGULAR, Movie.RATING_PG));
		
		
		//fully booked movie
		showtime.addShowTime(
				new GregorianCalendar(2013,10,29,10,0),
				new Cineplex("AMK Hub"), 
				1, 
				new Movie("Ender's Game", Movie.TYPE_3D, Movie.RATING_U));
		
		TransactionEntityConnector transaction = new TransactionEntityConnector();
		account.addAccount(new CustomerAccount("foo", "foo", "foo", 21, "83937419", "andychong2@gmail.com"));
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				showtime.changeAvailability(
						new GregorianCalendar(2013,10,29,10,0), 
						"AMK Hub", "Ender's Game", 1, i, j);
				transaction.addTransaction(
						GregorianCalendar.getInstance(), 
						"foo", new GregorianCalendar(2013,10,29,10,0), "AMK Hub", 1, "Ender's Game", "XX", 10);
			}
		}
		transaction.addTransaction(
				new GregorianCalendar(2013,10,1,10,0), 
				"foo", new GregorianCalendar(2013,10,29,10,0), "AMK Hub", 1, "Ender's Game", "XX", 10);
		transaction.addTransaction(
				new GregorianCalendar(2013,10,7,10,0), 
				"foo", new GregorianCalendar(2013,10,29,10,0), "AMK Hub", 1, "Ender's Game", "XX", 10);
		transaction.addTransaction(
				new GregorianCalendar(2013,10,10,10,0), 
				"foo", new GregorianCalendar(2013,10,29,10,0), "AMK Hub", 1, "Ender's Game", "XX", 10);
		transaction.addTransaction(
				new GregorianCalendar(2013,10,15,10,0), 
				"foo", new GregorianCalendar(2013,10,29,10,0), "AMK Hub", 1, "Ender's Game", "XX", 10);
		transaction.addTransaction(
				new GregorianCalendar(2013,10,20,10,0), 
				"foo", new GregorianCalendar(2013,10,29,10,0), "AMK Hub", 1, "Ender's Game", "XX", 10);
		
		//Holiday
		showtime.addShowTime(
				new GregorianCalendar(2013,10,30,10,0),
				new Cineplex("Causeway Point"), 
				1, 
				new Movie("Ender's Game", Movie.TYPE_3D, Movie.RATING_U));
	
		//mon - sun
		int date = 24;
		while(date!=29){
			showtime.addShowTime(
					new GregorianCalendar(2013,10,date,10,0),
					new Cineplex("The Cathay"), 
					1, 
					new Movie("Ender's Game", Movie.TYPE_3D, Movie.RATING_U));
			showtime.addShowTime(
					new GregorianCalendar(2013,10,date,10,0),
					new Cineplex("The Cathay"), 
					2, 
					new Movie("Ender's Game", Movie.TYPE_3D, Movie.RATING_U));
			showtime.addShowTime(
					new GregorianCalendar(2013,10,date,10,0),
					new Cineplex("The Cathay"), 
					3, 
					new Movie("Ender's Game", Movie.TYPE_3D, Movie.RATING_U));
			showtime.addShowTime(
					new GregorianCalendar(2013,10,date,12,0),
					new Cineplex("The Cathay"), 
					1, 
					new Movie("Baby Blues", Movie.TYPE_DIGITAL, Movie.RATING_18));
			showtime.addShowTime(
					new GregorianCalendar(2013,10,date,12,0),
					new Cineplex("The Cathay"), 
					2, 
					new Movie("Baby Blues", Movie.TYPE_DIGITAL, Movie.RATING_18));
			showtime.addShowTime(
					new GregorianCalendar(2013,10,date,12,0),
					new Cineplex("The Cathay"), 
					3, 
					new Movie("Baby Blues", Movie.TYPE_DIGITAL, Movie.RATING_18));
			showtime.addShowTime(
					new GregorianCalendar(2013,10,date,14,0),
					new Cineplex("The Cathay"), 
					1, 
					new Movie("Tom Yum 2", Movie.TYPE_REGULAR, Movie.RATING_PG));
			showtime.addShowTime(
					new GregorianCalendar(2013,10,date,14,0),
					new Cineplex("The Cathay"), 
					2, 
					new Movie("Tom Yum 2", Movie.TYPE_REGULAR, Movie.RATING_PG));
			showtime.addShowTime(
					new GregorianCalendar(2013,10,date,14,0),
					new Cineplex("The Cathay"), 
					3, 
					new Movie("Tom Yum 2", Movie.TYPE_REGULAR, Movie.RATING_PG));
			date++;
		}
	}
	
}
