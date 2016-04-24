package com.moblima.movie;

import java.util.ArrayList;

import com.moblima.movie.MovieListing.Status;
import com.moblima.util.Connector;


public class MovieListingEntityConnector extends Connector{
	
	private MovieListingEntity movielistingEntity;
	public static final String FILE_PATH="entities/movieListing";
	
	public MovieListingEntityConnector() {
	}
	
	public boolean addMovie(Movie movie,Status status){
		movielistingEntity = (MovieListingEntity) openEntity(MovieListingEntity.class, FILE_PATH);
		for (MovieListing movieL : movielistingEntity.getMovielisting()) {
			if(movieL.getMovie().getName().compareTo(movie.getName())==0){
				writeToFile(movielistingEntity,FILE_PATH);
				return false;
			}
		}
		movielistingEntity.getMovielisting().add(new MovieListing(movie, status));
		writeToFile(movielistingEntity,FILE_PATH);
		return true;
	}
	
	public boolean deleteMovie(Movie movie){
		movielistingEntity = (MovieListingEntity) openEntity(MovieListingEntity.class, FILE_PATH);

		for (MovieListing movieL : movielistingEntity.getMovielisting()) {
			if(movieL.getMovie().getName().compareTo(movie.getName())==0){
				movieL.setStatus(Status.END_OF_SHOWING);
				ShowTimeEntityConnector showtimeConnector = new ShowTimeEntityConnector();
				showtimeConnector.deleteShowTime(movieL.getMovie());
				writeToFile(movielistingEntity,FILE_PATH);
				return true;
				
			}
		}
		writeToFile(movielistingEntity,FILE_PATH);
		return false;
	}
	
	public boolean updateStatus(Movie movie, Status status){
		movielistingEntity = (MovieListingEntity) openEntity(MovieListingEntity.class, FILE_PATH);

		int index=0;
		for (MovieListing movieL : movielistingEntity.getMovielisting()) {
			if(movieL.getMovie().getName().compareTo(movie.getName())==0){
				movielistingEntity.getMovielisting().get(index).setStatus(status);
				writeToFile(movielistingEntity,FILE_PATH);
				return true;
			}
			index++;
		}
		writeToFile(movielistingEntity,FILE_PATH);
		return false;
	}

	public ArrayList<MovieListing> queryMovie(Status status){
		movielistingEntity = (MovieListingEntity) openEntity(MovieListingEntity.class, FILE_PATH);

		ArrayList<MovieListing> queryMovieL = new ArrayList<MovieListing>();
		for (MovieListing movieL : movielistingEntity.getMovielisting()) {
			if(status==null){
				queryMovieL.add(movieL);
			}
			else if(movieL.getStatus()==status){
				queryMovieL.add(movieL);
			}
		}

		return queryMovieL;
	}
	
	public ArrayList<MovieListing> queryMovie(){
		movielistingEntity = (MovieListingEntity) openEntity(MovieListingEntity.class, FILE_PATH);
		ArrayList<MovieListing> queryMovieL = movielistingEntity.getMovielisting();

		return queryMovieL;
	}
}
