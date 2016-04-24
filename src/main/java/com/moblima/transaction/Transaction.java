
package com.moblima.transaction;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Transaction implements Serializable{
	private String username;
	private Calendar transactionDateTime;
	private String transactionID;
	private GregorianCalendar movieDateTime;
	private String movie;
	private String Cineplex;
	private int cinemaID;
	private String seatID;
	private double profit;
	
	public Transaction(String username, GregorianCalendar dateTime, 
			String Cineplex, int cinemaID, String movie, String seatID, 
			double profit,Calendar transactionDateTime){
		this.username=username;
		this.movieDateTime=dateTime;
		this.Cineplex=Cineplex;
		this.cinemaID=cinemaID;
		this.movie=movie;
		this.seatID=seatID;
		this.transactionDateTime = transactionDateTime;
		setTransactionID(transactionDateTime, cinemaID);
		this.profit=profit;

	}
	
	private void setTransactionID(Calendar dateTime, int cinemaID){

		int Y = dateTime.get(Calendar.YEAR);
		int M = dateTime.get(Calendar.MONTH)+1;
		int D = dateTime.get(Calendar.DAY_OF_MONTH);
		int h = dateTime.get(Calendar.HOUR_OF_DAY);
		int m = dateTime.get(Calendar.MINUTE);
		String YYYY=Integer.toString(Y);
		String MM = Integer.toString(M);
		String DD = Integer.toString(D);
		String hh = Integer.toString(h);
		String mm = Integer.toString(m);
		transactionID= "00"+cinemaID+YYYY+(M < 10 ? "0" + MM : MM) +(D < 10 ? "0" + DD : DD)+(h < 10 ? "0" + hh : hh)+(m < 10 ? "0" + mm : mm);
	}
	

	
	public String getTransactionID(){
		return transactionID;
	}
	
	
	public double getProfit(){
		return profit;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setSeatID(String seatID){
		this.seatID= seatID;
	}
	
	
	public String getSeatID(){
		
		return seatID;
	}
	
	public void setMovie(String movie){
		this.movie= movie;
	}
	public String getMovie(){
		return movie;
	}
	public int getCinemaID(){
		return cinemaID;
	}
	
	public String getCineplex(){
		return Cineplex;
	}
	
	
	public void setMovieDateTime(GregorianCalendar dateTime) {
		this.movieDateTime = dateTime;
	}
	
	public GregorianCalendar getMovieDateTime() {
		return movieDateTime;
	}
	
	public int getMovieYear(){
		return movieDateTime.get(GregorianCalendar.YEAR);
	}
	
	
	public int getMovieMonth(){
		return movieDateTime.get(GregorianCalendar.MONTH)+1;
	}
	
	
	public int getMovieDay(){
		return movieDateTime.get(GregorianCalendar.DAY_OF_MONTH);
	}
	
	public int getTranYear(){
		return transactionDateTime.get(GregorianCalendar.YEAR);
	}
	
	
	public int getTranMonth(){
		return transactionDateTime.get(GregorianCalendar.MONTH)+1;
	}
	
	
	public int getTranDay(){
		return transactionDateTime.get(GregorianCalendar.DAY_OF_MONTH);
	}
	
	public int getTranHour(){
		return transactionDateTime.get(GregorianCalendar.HOUR_OF_DAY);
	}
	public int getTranMinute(){
		return transactionDateTime.get(GregorianCalendar.MINUTE);
	}
	public Calendar getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Calendar transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	
	
	
}
