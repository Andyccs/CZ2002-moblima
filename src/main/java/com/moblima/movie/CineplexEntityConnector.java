package com.moblima.movie;

import java.util.ArrayList;

import com.moblima.util.Connector;

public class CineplexEntityConnector extends Connector{
	private CineplexEntity cineplexEntity;
	private static final String FILE_PATH = "entities/cineplexEntity";
	
	public CineplexEntityConnector() {
	}
	
	//query cineplex
	public ArrayList<Cineplex> queryCineplex(){
		cineplexEntity = (CineplexEntity) openEntity(CineplexEntity.class, FILE_PATH);
		ArrayList<Cineplex> cineplexArray = cineplexEntity.getCineplex();

		return cineplexArray;
	}
	
	//add cineplex
	public boolean addCineplex(String name){
		cineplexEntity = (CineplexEntity) openEntity(CineplexEntity.class, FILE_PATH);
		ArrayList<Cineplex> cineplexArray = cineplexEntity.getCineplex();
		
		for (Cineplex cine : cineplexArray) {
			if(cine.getName().compareTo(name)==0){
				return false;
			}
		}
		
		cineplexArray.add(new Cineplex(name));
		writeToFile(cineplexEntity, FILE_PATH);
		return true;
	}
	
	//delete cineplex
	public void deleteCineplex(String name){
		cineplexEntity = (CineplexEntity) openEntity(CineplexEntity.class, FILE_PATH);
		ArrayList<Cineplex> cineplexArray = cineplexEntity.getCineplex();
		
		for (Cineplex cine : cineplexArray) {
			if(cine.getName().compareTo(name)==0){
				cineplexArray.remove(cine);
				break;
			}
		}
		writeToFile(cineplexEntity, FILE_PATH);
		
		ShowTimeEntityConnector showTimeConnector = new ShowTimeEntityConnector();
		showTimeConnector.deleteShowTime(new Cineplex(name));
	}
}
