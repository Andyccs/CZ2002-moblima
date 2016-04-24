package com.moblima.movie;

import java.io.Serializable;

public class MovieListing implements Serializable{
	
	public enum Status {
		COMING_SOON{
			public String toString()
			{
				return "Coming Soon";
			}
		}, 
		PREVIEW{
			public String toString()
			{
				return "Preview";
			}
		}, 
		NOW_SHOWING{
			public String toString()
			{
				return "Now Showing";
			}
		},
		END_OF_SHOWING{
			public String toString()
			{
				return "End of Showing";
			}
		}
	}
	
	private Movie movie;
	private Status status;
	
	public MovieListing(Movie movie, Status status) {
		this.movie = movie;
		this.status = status;
	}
	@Override
	public String toString() {
		return movie.toString() + "Status: " + status + "\n";
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
		
}
