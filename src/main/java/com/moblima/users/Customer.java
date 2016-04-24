package com.moblima.users;


import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.moblima.exception.TransactionException;
import com.moblima.loginsystem.Account;
import com.moblima.loginsystem.AccountEntityConnector;
import com.moblima.loginsystem.CustomerAccount;
import com.moblima.movie.Cineplex;
import com.moblima.movie.Movie;
import com.moblima.movie.ShowTimeEntityConnector;
import com.moblima.transaction.Discount;
import com.moblima.transaction.MoviePriceEntityConnector;
import com.moblima.transaction.Transaction;
import com.moblima.transaction.TransactionEntityConnector;

public class Customer extends User{
	private String userName;
	
	public Customer() {
		// TODO Auto-generated constructor stub
		this.setUsrAuthority(Authority.Customer);
		userName = null;
	}
	public Customer(String name) {
		// TODO Auto-generated constructor stub
		this.setUsrAuthority(Authority.Customer);
		userName = name;
	}
	
	//Accessor
	public String getUserName() {
		return userName;
	}
	
	//Mutator
	public void setUserName(String name) {
		this.userName = name;
	}


	public Transaction bookShowTime(
			String userName, Movie movie, Cineplex cineplex, int cinemaId, 
			GregorianCalendar dateTime, int seatRow, int seatCol) throws TransactionException {
			
		//using given usrname to get age for discount
		Account acc = new AccountEntityConnector().getAccount(userName);
		if(acc==null || acc.getAuthority() != Authority.Customer){
			throw new TransactionException();
		}

		int age = ((CustomerAccount) acc).getAge();
		double netPrice = getNetPrice(movie, cineplex, cinemaId, dateTime, age);
		
		new ShowTimeEntityConnector().changeAvailability(dateTime,cineplex.getName(),movie.getName(), cinemaId, seatRow, seatCol);
		
		char row = (char) ('A' + seatRow);
		char col = (char) ('1'+seatCol);
		StringBuilder sb = new StringBuilder();
		sb.append(row);
		if(seatCol==9){
			sb.append(10);
		}else{
			sb.append(col);
		}
		String seatID = sb.toString();
		
		new TransactionEntityConnector().addTransaction(GregorianCalendar.getInstance(),
				userName, dateTime, cineplex.getName(), cinemaId, movie.getName(), 
				seatID, netPrice);
		return new Transaction(userName, dateTime, cineplex.getName(), cinemaId, 
				movie.getName(), seatID, netPrice,GregorianCalendar.getInstance());
	}
	public double getNetPrice(Movie movie, Cineplex cineplex, int cinemaId,
			GregorianCalendar dateTime, int age) {
		double oriPrice = new MoviePriceEntityConnector().getPrice(movie.getType());	//price ==-1？
		double discout = new Discount().getDiscount(age, dateTime, cineplex, cinemaId, movie);
		double netPrice = oriPrice+discout;
		return netPrice;
	}

	public ArrayList<Transaction> checkBookingStatus(String usrName) throws TransactionException {
		
		ArrayList<Transaction> ticketCheck = new TransactionEntityConnector().queryTransactionByUsername(usrName);
		if(ticketCheck==null||ticketCheck.size()==0){
			throw new TransactionException("No booking history");
		}
		return ticketCheck;
		
	}
	
	public int getAge(){
		AccountEntityConnector account = new AccountEntityConnector();
		Account acc = account.getAccount(userName);
		int age = ((CustomerAccount)acc).getAge();
		return age;
	}

//	public void checkBookingHistory() {
//		// TODO Auto-generated method stub
//		
//	}
}
