 package com.moblima.transaction;

import java.util.ArrayList;

import com.moblima.util.Connector;

public class MoviePriceEntityConnector extends Connector {
	private MoviePriceEntity moviePriceEntity;
	private static final String FILE_PATH="entities/moviePriceList";
	
	public MoviePriceEntityConnector() {
	}
	
	public boolean addPrice(String movieType, double price) {
		moviePriceEntity = (MoviePriceEntity) openEntity(MoviePriceEntity.class,FILE_PATH);
		ArrayList<MoviePrice> priceList = moviePriceEntity.getPriceList();
		
		for (MoviePrice moviePrice : priceList) {
			if (moviePrice.getMovietype().compareTo(movieType) == 0) {
				return false;
			}
		}
		priceList.add(new MoviePrice(movieType, price));
		writeToFile(moviePriceEntity, FILE_PATH);
		return true;
	}
	
	public boolean changePrice(String movieType, double price) {
		moviePriceEntity = (MoviePriceEntity) openEntity(MoviePriceEntity.class,FILE_PATH);
		ArrayList<MoviePrice> priceList = moviePriceEntity.getPriceList();
		int index = 0;
		
		for (MoviePrice moviePrice : priceList) {
			if (moviePrice.getMovietype().compareTo(movieType) == 0) {
				priceList.get(index).setPrice(price);
				writeToFile(moviePriceEntity, FILE_PATH);
				return true;
			}
			index++;
		}
		writeToFile(moviePriceEntity, FILE_PATH);
		return false;
	}
	
	public boolean deletePrice(String movieType, double price) {
		moviePriceEntity = (MoviePriceEntity) openEntity(MoviePriceEntity.class,FILE_PATH);
		ArrayList<MoviePrice> priceList = moviePriceEntity.getPriceList();
		
		for (int i=0; i<priceList.size(); i++) {
			if(priceList.get(i).getMovietype().compareTo(movieType) == 0) {
				priceList.remove(i);
				writeToFile(moviePriceEntity, FILE_PATH);
				return true;
			}
		}
		writeToFile(moviePriceEntity, FILE_PATH);
		return false;
	}
	
	public double getPrice(String movieType) {
		moviePriceEntity = (MoviePriceEntity) openEntity(MoviePriceEntity.class,FILE_PATH);
		ArrayList<MoviePrice> priceList = moviePriceEntity.getPriceList();
		
		for (MoviePrice moviePrice : priceList) {
			if (moviePrice.getMovietype().compareTo(movieType) == 0) {
				writeToFile(moviePriceEntity, FILE_PATH);
				return moviePrice.getPrice();
			}
		}
		writeToFile(moviePriceEntity, FILE_PATH);
		return -1;
	}
}
