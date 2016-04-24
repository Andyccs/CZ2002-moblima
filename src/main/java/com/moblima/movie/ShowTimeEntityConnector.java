package com.moblima.movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.moblima.exception.ShowTimeException;
import com.moblima.movie.MovieListing.Status;
import com.moblima.transaction.TransactionEntityConnector;
import com.moblima.util.Connector;

public class ShowTimeEntityConnector extends Connector{
	
	private ShowTimeEntity showTimeEntity;
	private static final String FILE_PATH="entities/showTime";
	
	public ShowTimeEntityConnector() {
	}
	
	public boolean addShowTime(GregorianCalendar dateTime, Cineplex cineplex, Integer cinema, Movie movie){
		showTimeEntity = (ShowTimeEntity) openEntity(ShowTimeEntity.class,FILE_PATH);
		
		//check if the movie status is NOW_SHOWING
		boolean showing = isNowShowing(movie);
		
		//check if there is a same dateTime(assume movie is 2 hour), at that particular cineplex and cinema
		boolean clash = isClash(dateTime, cineplex, cinema);
		
		if(clash||!showing){
			writeToFile(showTimeEntity, FILE_PATH);
			return false;
		}
	
		ShowTime show = new ShowTime(dateTime, cineplex, cinema, movie);
		showTimeEntity.getShowTime().add(show);
		writeToFile(showTimeEntity, FILE_PATH);
		return true;
	}

	public void deleteShowTime(Movie movie){
		String movieName = movie.getName();
		showTimeEntity = (ShowTimeEntity) openEntity(ShowTimeEntity.class,FILE_PATH);
		
		ArrayList<ShowTime> show = showTimeEntity.getShowTime();

		for(int i = 0;i<show.size();i++){
			if(show.get(i).getMovie().getName().compareTo(movieName)==0){
				showTimeEntity.getShowTime().remove(i);
				i--;
			}
		}
		writeToFile(showTimeEntity, FILE_PATH);
	}
	
	public void deleteShowTime(Cineplex cineplex){
		String cineplexName = cineplex.getName();
		showTimeEntity = (ShowTimeEntity) openEntity(ShowTimeEntity.class,FILE_PATH);

		int index = 0;

		for (ShowTime time : showTimeEntity.getShowTime()) {
			if(time.getCineplex().getName().compareTo(cineplexName)==0){
				showTimeEntity.getShowTime().remove(index);
			}
			index++;
		}
		writeToFile(showTimeEntity, FILE_PATH);
	}
	
	public boolean deleteShowTime(GregorianCalendar dateTime, String cineplexName, String movieName, int cinemaId){
		showTimeEntity = (ShowTimeEntity) openEntity(ShowTimeEntity.class,FILE_PATH);

		ShowTime showTime = getShowTime(dateTime, cineplexName, movieName, cinemaId);
		if(showTime==null){
			return false;
		}
		showTimeEntity.getShowTime().remove(showTime);
		
		writeToFile(showTimeEntity, FILE_PATH);
		return true;
	}
	
	public ArrayList<ShowTime> queryShowTime(GregorianCalendar dateTime, String cineplex, String movie) throws ShowTimeException{
		showTimeEntity = (ShowTimeEntity) openEntity(ShowTimeEntity.class,FILE_PATH);

		ArrayList<ShowTime> showtimeArray = (ArrayList<ShowTime>) showTimeEntity.getShowTime().clone();
		if(showtimeArray==null||showtimeArray.size()==0)
			throw new ShowTimeException("No show time available");
		
		GregorianCalendar currentDateTime = (GregorianCalendar) Calendar.getInstance();
		for (int i=0;i<showtimeArray.size();i++) {
			if(showtimeArray.get(i).getDateTime().compareTo(currentDateTime)<0){
				showtimeArray.remove(i);
				i--;
			}
		}
		
		if (dateTime != null) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
			for (int i=0;i<showtimeArray.size();i++) {
				if(!dateFormatter.format(showtimeArray.get(i).getDateTime().getTime()).equals(dateFormatter.format(dateTime.getTime()))){
					showtimeArray.remove(i);
					i--;
				}
			}
		}
		
		if (cineplex != null) {
			for (int i=0;i<showtimeArray.size();i++) {
				if(showtimeArray.get(i).getCineplex().getName().compareTo(cineplex)!=0){
					showtimeArray.remove(i);
					i--;
				}
			}
		}
		
		if (movie != null) {
			for (int i=0;i<showtimeArray.size();i++) {
				if(showtimeArray.get(i).getMovie().getName().compareTo(movie)!=0 ){
					showtimeArray.remove(i);
					i--;
				}

			}
		}
		writeToFile(showTimeEntity, FILE_PATH);
		return showtimeArray;		
	}
	
	public void changeAvailability(GregorianCalendar dateTime,String cineplexName,String movieName, int cinemaId, int row, int column){
		showTimeEntity = (ShowTimeEntity) openEntity(ShowTimeEntity.class,FILE_PATH);

		ShowTime showtime;
		for(int i=0;i<showTimeEntity.getShowTime().size();i++){
			showtime = showTimeEntity.getShowTime().get(i);
			if(showtime.getCineplex().getName().compareTo(cineplexName)==0
					&& showtime.getDateTime().getTimeInMillis()==dateTime.getTimeInMillis()
					&& showtime.getMovie().getName().compareTo(movieName)==0
					&& showtime.getNoOfCinema()==cinemaId){
				showtime.getLayout().setSeats(row, column, true);//true is not available
			}
		}
		writeToFile(showTimeEntity, FILE_PATH);
	}
	
	public ShowTime getShowTime(GregorianCalendar dateTime, String cineplexName, String movieName, int cinemaId) {
		showTimeEntity = (ShowTimeEntity) openEntity(ShowTimeEntity.class,FILE_PATH);

		ShowTime showtime;
		for(int i=0;i<showTimeEntity.getShowTime().size();i++){
			showtime = showTimeEntity.getShowTime().get(i);
			if(showtime.getCineplex().getName().compareTo(cineplexName)==0
					&& showtime.getDateTime().getTimeInMillis()==dateTime.getTimeInMillis()
					&& showtime.getMovie().getName().compareTo(movieName)==0
					&& showtime.getNoOfCinema()==cinemaId){
				return showtime;
			}
		}
		return null;
	}
	
	public boolean updateShowTime(
			GregorianCalendar odateTime, Cineplex ocineplex, Integer ocinema, Movie omovie,
			GregorianCalendar dateTime, Cineplex cineplex, Integer cinema, Movie movie){
		showTimeEntity = (ShowTimeEntity) openEntity(ShowTimeEntity.class,FILE_PATH);

		boolean status;
		
		TransactionEntityConnector tconnect = new TransactionEntityConnector();
		
		status = tconnect.isBooked(dateTime, cineplex, cinema, movie);
		if(status){
			writeToFile(showTimeEntity, FILE_PATH);
			return false;
		}
				
		status = deleteShowTime(odateTime, ocineplex.getName(), omovie.getName(), ocinema);
		if(!status){
			writeToFile(showTimeEntity, FILE_PATH);
			return false;
		}
		
		status = addShowTime(dateTime, cineplex, cinema, movie);
		if(!status){
			status = addShowTime(odateTime, ocineplex, ocinema, omovie);
			writeToFile(showTimeEntity, FILE_PATH);
			return false;	
		}
		writeToFile(showTimeEntity, FILE_PATH);
		return true;
	}

	private boolean isNowShowing(Movie movie) {
		MovieListingEntityConnector connector = new MovieListingEntityConnector();
		ArrayList<MovieListing> allMovie = connector.queryMovie(Status.NOW_SHOWING);
		for (MovieListing movieListing : allMovie) {
			if(movieListing.getMovie().getName().compareTo(movie.getName())==0){
				return true;
			}
		}
		return false;
	}

	private boolean isClash(GregorianCalendar dateTime, Cineplex cineplex, Integer cinema) {
		long startTime = dateTime.getTimeInMillis();
		long endTime = startTime + 7200000; //2hour
		long givenStartTime,givenEndTime;
		
		showTimeEntity = (ShowTimeEntity) openEntity(ShowTimeEntity.class,FILE_PATH);

		for (ShowTime showTime : showTimeEntity.getShowTime()) {
			givenStartTime = showTime.getDateTime().getTimeInMillis();
			givenEndTime = givenStartTime + 7200000;
			if(givenStartTime<=startTime && startTime<givenEndTime
					||givenStartTime<endTime && endTime<=givenEndTime){
				if(showTime.getCineplex().getName().compareTo(cineplex.getName())==0
						&& showTime.getNoOfCinema()==cinema){
					//meet other scheduled show time
					return true;
				}
			}
		}
		return false;	
	}
}
