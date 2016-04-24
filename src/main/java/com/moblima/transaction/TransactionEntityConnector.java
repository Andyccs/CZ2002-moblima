
package com.moblima.transaction;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.moblima.movie.Cineplex;
import com.moblima.movie.Movie;
import com.moblima.util.Connector;

public class TransactionEntityConnector extends Connector{
	
	private TransactionEntity transactionEntity;
	public static final String FILE_PATH="entities/transaction";
	
	public TransactionEntityConnector() {
		
	}
	
	
	public void addTransaction(Calendar transactionDateTime, String usrName, 
			GregorianCalendar movieDateTime, String cineplex, int cinemaID, 
			String movie, String seatID,
			double netPrice){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		transactionEntity.getTransaction().add(
				new Transaction(usrName, movieDateTime, cineplex, cinemaID, 
						movie, seatID, netPrice, transactionDateTime));
		writeToFile(transactionEntity, FILE_PATH);
	}

	//only for testing purpose
	public void deleteTransaction(int index){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		
		transactionEntity.getTransaction().remove(index-1);
		
		writeToFile(transactionEntity, FILE_PATH);
	}
	
	//only for testing purpose
	public TransactionEntity getAllTransaction(){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		TransactionEntity entity=transactionEntity;
		writeToFile(transactionEntity, FILE_PATH);
		return entity;
		
	}
	
	/*
	public Transaction getTransactionByID(String TransactionID){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		for (Transaction trans : transactionEntity.getTransaction()) {
			if(compareTransactionID(TransactionID, trans)){
				writeToFile(transactionEntity, FILE_PATH);
				return trans;
			}
		}
		writeToFile(transactionEntity, FILE_PATH);
		return null;
	}

	
	private boolean compareTransactionID(String TransactionID, Transaction trans) {
		return trans.getTransactionID().equals(TransactionID);
	}
	*/
	
	public ArrayList<Transaction> queryTransactionByMovie(String movie){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		ArrayList<Transaction> transactionArray = (ArrayList<Transaction>) transactionEntity.getTransaction().clone();
	
		if (movie != null && transactionArray.size() != 0) {
			for (int i=transactionArray.size()-1;i>=0;i--) {
				if(transactionArray.get(i).getMovie().compareTo(movie)!=0 )
					transactionArray.remove(i);
			}
		}
		writeToFile(transactionEntity, FILE_PATH);
		return transactionArray;		
	}
	
	
	public ArrayList<Transaction> queryTransactionByUsername(String username){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		ArrayList<Transaction> transactionArray = (ArrayList<Transaction>) transactionEntity.getTransaction().clone();
	
		if (username != null && transactionArray.size() != 0) {
			for (int i=transactionArray.size()-1;i>=0;i--) {
				if(transactionArray.get(i).getUsername().compareTo(username)!=0 )
					transactionArray.remove(i);
			}
		}
		writeToFile(transactionEntity, FILE_PATH);
		return transactionArray;		
	}
	
	public ArrayList<Transaction> queryTransactionByCineplex(String cineplex){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		ArrayList<Transaction> transactionArray = (ArrayList<Transaction>) transactionEntity.getTransaction().clone();
	
		if (cineplex != null && transactionArray.size() != 0) {
			for (int i=transactionArray.size()-1;i>=0;i--) {
				
				if(transactionArray.get(i).getCineplex().compareTo(cineplex)!=0){
					
					transactionArray.remove(i);
				}
				
			}
		}
		writeToFile(transactionEntity, FILE_PATH);
		
		return transactionArray;		
	}

	public boolean isBooked(GregorianCalendar dateTime, Cineplex cineplex, Integer cinema, Movie movie){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		for (Transaction t : transactionEntity.getTransaction()) {
			if(t.getMovieDateTime().compareTo(dateTime)==0
					&&t.getCineplex().compareTo(cineplex.getName())==0
					&&t.getCinemaID()==cinema
					&&t.getMovie().compareTo(movie.getName())==0){
				writeToFile(transactionEntity, FILE_PATH);
				return true;
			}
		}
		writeToFile(transactionEntity, FILE_PATH);
		return false;
	}
	
	
	public ArrayList<Transaction> queryTransactionByDate(GregorianCalendar dateTime){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		ArrayList<Transaction> transactionArray = (ArrayList<Transaction>) transactionEntity.getTransaction().clone();
		int Y = dateTime.get(GregorianCalendar.YEAR);
		int M = dateTime.get(GregorianCalendar.MONTH)+1;
		int D = dateTime.get(GregorianCalendar.DAY_OF_MONTH);
		if (dateTime != null && transactionArray.size() != 0) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
			for (int i=transactionArray.size()-1;i>=0;i--) {
				
				if(Y>0 && M>0 && D>0){
					if(!dateFormatter.format(transactionArray.get(i).getTransactionDateTime().getTime()).equals(dateFormatter.format(dateTime.getTime())))
						transactionArray.remove(i);
				}
				else if(Y>0 && M>0 && D<=0){
					if((Y!=transactionArray.get(i).getMovieYear()) && (M!=transactionArray.get(i).getMovieMonth())){
						transactionArray.remove(i);
					}
				}
				else if(Y>0 && M<=0 && D<=0){
					if((Y!=transactionArray.get(i).getMovieYear())){
						transactionArray.remove(i);
					}
				}
				else if(Y<0){
					transactionArray.remove(i);
				}
				else if(Y>0 && M<0 && D>0){
					transactionArray.remove(i);
				}
				else
					transactionArray.remove(i);
			}
		}
		writeToFile(transactionEntity, FILE_PATH);
		return transactionArray;		
	}
	
	public ArrayList<Transaction> getTransactionByPeriod(GregorianCalendar startTime, GregorianCalendar endTime){
		transactionEntity = (TransactionEntity) openEntity(TransactionEntity.class, FILE_PATH);
		ArrayList<Transaction> resultTransaction = new ArrayList<Transaction>();
		for (Transaction t : transactionEntity.getTransaction()) {
			if(t.getTransactionDateTime().compareTo(startTime)>=0&&t.getTransactionDateTime().compareTo(endTime)<=0)
				resultTransaction.add(t);
		}
		return resultTransaction;
	}
}
