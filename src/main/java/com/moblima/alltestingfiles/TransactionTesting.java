package com.moblima.alltestingfiles;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

import com.moblima.movie.Cineplex;
import com.moblima.movie.CineplexEntityConnector;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieListing.Status;
import com.moblima.movie.MovieListingEntityConnector;
import com.moblima.transaction.RevenueReport;
import com.moblima.transaction.Transaction;
import com.moblima.transaction.TransactionEntityConnector;

public class TransactionTesting {

	private static final Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		
		TransactionEntityConnector connector = new TransactionEntityConnector();
		RevenueReport report=new RevenueReport();
		
		printTransaction(connector);

	}

	
	private static void printTransaction(TransactionEntityConnector connector) {
		int index = 1;
		
		GregorianCalendar calendar;
		System.out.println("Index\tDate\t\tTime\tTransactionID\tCineplex\tCinemaID\tMovieName\tseatID\t\tprofit");
		for (Transaction transaction : connector.getAllTransaction().getTransaction()) {
			calendar = transaction.getMovieDateTime();
			
			System.out.printf("%d \t%4d/%02d/%02d \t%02d:%02d\t",
			          index, 
			          calendar.get(GregorianCalendar.YEAR), 
			          calendar.get(GregorianCalendar.MONTH)+1, 
			          calendar.get(GregorianCalendar.DAY_OF_MONTH), 
			          calendar.get(GregorianCalendar.HOUR_OF_DAY), 
			          calendar.get(GregorianCalendar.MINUTE));
			System.out.print(transaction.getTransactionID()+"\t");
			System.out.print(transaction.getCineplex()+"\t\t");
			System.out.print(transaction.getCinemaID()+"\t");
			System.out.print(transaction.getMovie()+"\t\t");
			System.out.print(transaction.getSeatID()+"\t\t");
			System.out.println(transaction.getProfit()+"\t");
			index++;
		}
	}
	
	
	private static Movie moviePicker() {
		int index=0;
		int userInput;
		MovieListingEntityConnector connector = new MovieListingEntityConnector();
		ArrayList<MovieListing> ml = connector.queryMovie(Status.NOW_SHOWING);
		for (MovieListing movie : ml) {
			System.out.println(index+"\t"+movie.getMovie().getName());
			index++;
		}
		System.out.println("Please pick a movie");
		userInput = in.nextInt();
		
		return ml.get(userInput).getMovie();
	}
	private static int cinemaPicker(Cineplex cineplex) {
		int numberOfCinema = cineplex.getCinema().size();
		System.out.println("This cineplex contain "+numberOfCinema+" cinemas");
		System.out.println("Please picker a number");
		int cinema = in.nextInt();
		in.nextLine();
		
		return cinema;
	}
	private static Cineplex cineplexPicker() {
		int index=0;
		int userInput;
		CineplexEntityConnector connector = new CineplexEntityConnector();
		ArrayList<Cineplex> cineplexes = connector.queryCineplex();
		for (Cineplex cineplex : cineplexes) {
			System.out.println(index+"\t"+cineplex.getName());
			index++;
		}
		System.out.println("Please pick a cineplex");
		userInput = in.nextInt();
		
		return cineplexes.get(userInput);
	}
	
	private static GregorianCalendar dateTimePicker() {
		int year,month,day,hour,minute;
		System.out.println("Enter year");
		year = in.nextInt();
		System.out.println("Enter month");
		month = in.nextInt();
		month = month-1;
		System.out.println("Enter day");
		day = in.nextInt();
		System.out.println("Enter hour");
		hour = in.nextInt();
		System.out.println("Enter minute");
		minute = in.nextInt();
		in.nextLine();
		
		return new GregorianCalendar(year, month, day, hour, minute);
	}
	
	private static GregorianCalendar datePicker() {
		int year,month,day;
		System.out.println("Enter year");
		year = in.nextInt();
		System.out.println("Enter month");
		month = in.nextInt();
		month = month-1;
		System.out.println("Enter day");
		day = in.nextInt();
		in.nextLine();
		
		return new GregorianCalendar(year, month, day, 0, 0);
	}
}
