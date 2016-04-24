package com.moblima.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.moblima.exception.CineplexException;
import com.moblima.exception.LoginException;
import com.moblima.exception.MovieListingException;
import com.moblima.exception.ShowTimeException;
import com.moblima.exception.TransactionException;
import com.moblima.movie.Cineplex;
import com.moblima.movie.Movie;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieListing.Status;
import com.moblima.movie.Seat;
import com.moblima.movie.SeatLayout;
import com.moblima.movie.ShowTime;
import com.moblima.transaction.Transaction;
import com.moblima.users.Authority;
import com.moblima.users.Customer;
import com.moblima.users.Public;
import com.moblima.users.Staff;
import com.moblima.users.User;

public class Main {
	private static final int PUBLIC_LOGIN = 1;			//done	//no bug
	private static final int PUBLIC_REGISTER = 2;		//done	//no bug
	private static final int PUBLIC_MOVIE_SHOWING = 3;	//done	//no bug	
	private static final int PUBLIC_MOVIE_COMING = 4;	//done	//no bug
	private static final int PUBLIC_SHOWTIME = 5;		//done	//no bug
	private static final int PUBLIC_TICKET_BOOK = 6;	//done	//no bug
	private static final int PUBLIC_TICKET_CHECK = 7;	//done	//no bug
	private static final int PUBLIC_EXIT = 8;
	
	private static final int CUSTOMER_MOVIE_SHOWING = 1;	//done	//no bug
	private static final int CUSTOMER_MOVIE_COMING = 2;		//done	//no bug
	private static final int CUSTOMER_SHOWTIME = 3;			//done	//no bug
	private static final int CUSTOMER_TICKET_BOOK = 4;		//done	//no bug
	private static final int CUSTOMER_TICKET_CHECK = 5;		//done	//no bug
	private static final int CUSTOMER_EXIT = 6;
	
	private static final int STAFF_MOVIE_ALL = 1;		//done	//no bug
	private static final int STAFF_ADD_MOVIE = 2;		//done	//no bug
	private static final int STAFF_UPDATE_MOVIE = 3;	//done	//no bug
	private static final int STAFF_REMOVE_MOVIE = 4;	//done	//no bug
	private static final int STAFF_SHOWTIME = 5;		//done	//no bug
	private static final int STAFF_ADD_SHOWTIME = 6;	//done	//no bug
	private static final int STAFF_UPDATE_SHOWTIME = 7;	//done	//no bug
	private static final int STAFF_REMOVE_SHOWTIME = 8;	//done	//no bug
	private static final int STAFF_REPORT_CINEPLEX = 9;	//done	//no bug
	private static final int STAFF_REPORT_PERIOD = 10;	//done	//no bug
	private static final int STAFF_REPORT_MOVIE = 11;	//done	//no bug
	private static final int STAFF_ADD_HOLIDAY = 12;	//done	//no bug
	private static final int STAFF_REMOVE_HOLIDAY = 13;	//done	//no bug
	private static final int STAFF_UPADTE_PRICE = 14;	//done	//no bug
	private static final int STAFF_EXIT = 15;
	
	private static final Scanner in = new Scanner(System.in);
	
	private static User person;
	
	public static void main(String[] args) {
		int userOption;
		person = new Public();
				
		while(true){

			try {
				printMenu();
				
				userOption = in.nextInt();
				in.nextLine();
				
				doUserOption(userOption);
			} catch(InputMismatchException|IndexOutOfBoundsException e){
				in.nextLine();
				System.out.println("Please enter a correct option");
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}
	}
	private static void doUserOption(int userOption) throws Exception {
		if(person.getUsrAuthority()==Authority.Public){
			doPublicOption(userOption);
		}
		else if(person.getUsrAuthority()==Authority.Customer){
			doCustomerOption(userOption);
		}
		else{
			doStaffOption(userOption);
		}
	}
	private static void doPublicOption(int userOption) throws LoginException, TransactionException, MovieListingException, CineplexException, ShowTimeException {
		int userInput = 0;
		
		switch (userOption) {
			case PUBLIC_LOGIN:
				publicLogin();
				break;
			case PUBLIC_REGISTER:
				publicRegister();
				break;
			case PUBLIC_MOVIE_SHOWING:
				printMovie(Status.NOW_SHOWING,false);
				break;
			case PUBLIC_MOVIE_COMING:
				printMovie(Status.COMING_SOON,false);
				break;
			case PUBLIC_SHOWTIME:
				printShowTime();
				break;
			case PUBLIC_TICKET_BOOK:

				System.out.println("You need to login first in order to book movie ticket");
				System.out.println("Do you have an account?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				
				userInput = in.nextInt();
				in.nextLine();
				
				switch(userInput) {
				case 1:
					publicLogin();
					customerBookTicket();
					break;
				case 2:
					publicRegister();
					customerBookTicket();
					break;
				}
				break;
			case PUBLIC_TICKET_CHECK:
				
				System.out.println("You need to login first in order to check booking status");
				System.out.println("Do you have an account?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				
				userInput = in.nextInt();
				in.nextLine();
				
				switch(userInput) {
				case 1:
					publicLogin();
					customerCheckStatus();
				case 2:
					publicRegister();
					customerCheckStatus();
				}
				break;
			case PUBLIC_EXIT:
				System.out.println("Hope to see you again");
				System.exit(0);
				break;
			}
		}
	private static void doCustomerOption(int userOption) throws TransactionException, MovieListingException, CineplexException, ShowTimeException {
		switch (userOption) {
		case CUSTOMER_MOVIE_SHOWING:
			printMovie(Status.NOW_SHOWING,false);
			break;
		case CUSTOMER_MOVIE_COMING:
			printMovie(Status.COMING_SOON,false);
			break;
		case CUSTOMER_SHOWTIME:
			printShowTime();
			break;
		case CUSTOMER_TICKET_BOOK:
			customerBookTicket();
			break;
		case CUSTOMER_TICKET_CHECK:
			customerCheckStatus();
			break;
		case CUSTOMER_EXIT:
			System.out.println("Hope to see you again");
			System.exit(0);
			break;
		default:
			break;
		}
	}
	private static void doStaffOption(int userOption) throws Exception {
		String movieName,movieType = null,movieRating=null;
		double price;
		Status status=null;
		GregorianCalendar dateTime = null;
		Cineplex cineplex = null;
		Integer cinema = null;
		Movie movie = null;
		GregorianCalendar date;
		String report;
		ArrayList<ShowTime> showtimes;
		int index = 0;
		switch (userOption) {
		case STAFF_MOVIE_ALL:
			printMovie(null,true);
			break;
		case STAFF_SHOWTIME:
			printShowTime();
			break;
		case STAFF_ADD_MOVIE:
			System.out.print("Movie name: ");
			movieName = in.nextLine();
			movieType = movieTypePicker();
			movieRating = movieRatingPicker();
			System.out.println("");
			status = statusPicker();
			((Staff)person).addMovie(movieName, movieType, movieRating, status);
			break;
		case STAFF_UPDATE_MOVIE:
			movie = moviePicker(null,true);
			status = statusPicker();
			((Staff)person).updateMovie(movie, status);
			break;
		case STAFF_REMOVE_MOVIE:
			movie = moviePicker(Status.NOW_SHOWING,false);
			((Staff)person).removeMovie(movie);
			break;
		case STAFF_ADD_SHOWTIME:
			dateTime = dateTimePicker();
			movie = moviePicker(Status.NOW_SHOWING,false);
			cineplex = cineplexPicker();
			cinema = cinemaPicker(cineplex);
			((Staff)person).addShowTime(dateTime, cineplex, cinema, movie);
			System.out.println("Showtime was added");
			break;
		case STAFF_UPDATE_SHOWTIME:
			showtimes = printShowTime();
			if(showtimes!=null && showtimes.size()!=0){
				System.out.print("Please select a showtime to be updated: ");
				index = in.nextInt();
				in.nextLine();
				index = index-1;

				//pick all please =)
				dateTime = dateTimePicker();
				movie = moviePicker(Status.NOW_SHOWING,false);
				cineplex = cineplexPicker();
				cinema = cinemaPicker(cineplex);
				
				((Staff)person).updateShowTime(
						showtimes.get(index).getDateTime(), 
						showtimes.get(index).getCineplex(), 
						showtimes.get(index).getNoOfCinema(), 
						showtimes.get(index).getMovie(), 
						dateTime, cineplex, cinema, movie);
			}else{
				System.out.println("There is no slot now");
			}
			break;
		case STAFF_REMOVE_SHOWTIME:
			showtimes = printShowTime();
			if(showtimes!=null && showtimes.size()!=0){
				System.out.print("Please select a movie: ");
				index = in.nextInt();
				index -= 1;
				in.nextLine();
				((Staff)person).removeShowTime(
						showtimes.get(index).getDateTime(), 
						showtimes.get(index).getCineplex().getName(), 
						showtimes.get(index).getMovie().getName(),
						showtimes.get(index).getNoOfCinema());
			}else{
				System.out.println("There is no slot now");
			}
			break;
		case STAFF_REPORT_CINEPLEX:
			report = ((Staff)person).generateReportByCineplex();
			System.out.println(report);
			break;
		case STAFF_REPORT_PERIOD:
			System.out.println("Year");
			int year = in.nextInt();
			in.nextLine();
			report = ((Staff)person).generateReportByPeriod(year);
			System.out.println(report);
			break;
		case STAFF_REPORT_MOVIE:
			report = ((Staff)person).generateReportByMovie();
			System.out.println(report);
			break;
		case STAFF_ADD_HOLIDAY:
			date = datePicker();
			((Staff)person).addHoliday(date);
			break;
		case STAFF_REMOVE_HOLIDAY:
			date = datePicker();
			((Staff)person).removeHoliday(date);
			break;
		case STAFF_UPADTE_PRICE:
			movieType = movieTypePicker();
			System.out.println("New price: ");
			price = in.nextDouble();
			in.nextLine();
			((Staff)person).updatePrice(movieType, price);
			break;
		case STAFF_EXIT:
			System.out.println("Hope to see you again");
			System.exit(0);
			break;
		default:
			break;
		}
	}
	private static void customerBookTicket() throws TransactionException, MovieListingException, CineplexException, ShowTimeException {
		ArrayList<ShowTime> st;
		Transaction transaction;
		int index;
		int row;
		int col;
		int[] rowCol;
		//start booking
		//show a list of movie
		st = printShowTime();	//index start from 1
		
		//choose movie
		//print st
		System.out.print("Please choose a movie: ");
		index = in.nextInt();
		index -= 1;
		in.nextLine();
		
		//printLayout
		SeatLayout layout = st.get(index).getLayout();
		printLayout(layout);
		
		if(layout.isFullyBooked()){
			throw new TransactionException("It is fully booked");
		}
		
		rowCol = seatPicker(layout);
		row = rowCol[0];
		col = rowCol[1];
		
		ShowTime showtime = st.get(index);
	
		transaction = ((Customer)person).bookShowTime(
				((Customer) person).getUserName(), 
				showtime.getMovie(), 
				showtime.getCineplex(), 
				showtime.getNoOfCinema(), 
				showtime.getDateTime(), 
				row, col);
		
		System.out.println("Transaction successs");
		printInvoice(transaction);
		
	}
	private static void customerCheckStatus() throws TransactionException {
		ArrayList<Transaction> transaction;
		transaction = ((Customer)person).checkBookingStatus(((Customer) person).getUserName());
		if(transaction!=null){
			for (Transaction t : transaction) {
				printInvoice(t);
			}
		}
	}
	private static void printInvoice(Transaction transaction) {
		System.out.println("MOBLIMA MOVIE TICKET");
		System.out.println("Customer username: "+transaction.getUsername());
		System.out.println("Transaction ID" + transaction.getTransactionID());;
		System.out.println("Movie: "+transaction.getMovie());
		System.out.println("Date and time: "+calendarToSting(transaction.getMovieDateTime()));
		System.out.println("Location: "+transaction.getCineplex());
		System.out.println("Cinema: "+transaction.getCinemaID());
		System.out.println("Seat: "+transaction.getSeatID());
		System.out.println("Price: "+transaction.getProfit());
		System.out.println("");
		if(((Customer)person).getAge()>60){
			System.out.println("Note: validation will be done upon entering the cinema");
		}
	}
	//	private static void printReport(LinkedList<String> report){
//		if(report==null || report.size()==0)
//			throw new NullPointerException("No report found");
//		for (String string : report) {
//			System.out.println(string);
//		}
//	}
	private static void printLayout(SeatLayout layout) {
		Seat[][] seat = layout.getSeats();
		int row = layout.getRow();
		int col = layout.getCol();
		char c;
		
		System.out.println("");
		System.out.println("------Seat Layout------");
		//print the top row
		System.out.print(" \t ");
		for(int j=0; j<col; j++){
			System.out.print(j+1+" ");
			if (j == (col/2)-1) {
				System.out.print("   ");
			}
		}

		System.out.println("");
		
		for(int i=0; i<row; i++){
			//print a line
			System.out.print(" \t");
			for(int j=0; j<col; j++){
				if (j == (col/2)) {
					System.out.print("-");
					System.out.print("  ");
				}
				System.out.print("--");
			}
			System.out.println("-");
			
			c = (char) ('A'+i);
			System.out.print(c+"\t|");
			
			for(int j=0; j<col; j++){
				if (j == (col/2)) {
					System.out.print("  ");
					System.out.print("|");
				}
				
				if(seat[i][j].getAvailable()){
					System.out.print("X");
				}
				else{
					System.out.print(" ");
				}
				System.out.print("|");
			}
			
			System.out.println("\t"+c);	
		}
		System.out.println("");
		System.out.println("\t\tSCREEN");
		
	}
	private static void printMovie(Status status, boolean showStatus) throws MovieListingException {
		ArrayList<MovieListing> movie;
		movie = person.queryMovie(status);
		if(movie==null||movie.size()==0){
			System.out.println("There isn't any movie now!");
		} else if(showStatus){
			int i = 1;
			System.out.println("Index\tName\tType\tRating\tPrice\tStatus");
			for (MovieListing movieListing : movie) {
				System.out.println(i+". \t"+
						movieListing.getMovie().getName()+"\t"+
						movieListing.getMovie().getType()+"\t"+
						movieListing.getMovie().getRating()+"\t"+
						person.getPrice(movieListing.getMovie().getType())+"\t"+
						movieListing.getStatus());
				i++;
			}
		}
		else{
			int i = 1;
			System.out.println("Index\tName\tType\tRating\tPrice");
			for (MovieListing movieListing : movie) {
				System.out.println(i+". \t"+
						movieListing.getMovie().getName()+"\t"+
						movieListing.getMovie().getType()+"\t"+
						movieListing.getMovie().getRating()+"\t"+
						person.getPrice(movieListing.getMovie().getType())+"\t");
				
				i++;
			}
		}
	}
	private static void printMenu() {
		System.out.println("");
		System.out.println("------------------------------------------");
		System.out.println("Welcome to Moblima Movie Booking System");
		System.out.println("------------------------------------------");

		if(person.getUsrAuthority()==Authority.Public){
			printPublicMenu();			
		}
		else if(person.getUsrAuthority()==Authority.Customer){
			printCustomerMenu();
		}
		else{
			printStaffMenu();
		}
		
	}
	private static void printPublicMenu() {
		System.out.println("Please select your option");
		System.out.println("CUSTOMER AND STAFF LOGIN");
		System.out.println("1.Login");
		System.out.println("2.Register Account");
		System.out.println("");
		System.out.println("MOVIE HIGHLIGHTS");
		System.out.println("3.Now Showing");
		System.out.println("4.Coming Soon");
		System.out.println("");
		System.out.println("SHOWTIME");
		System.out.println("5.Select Cinema, Select Movie, Select Date and Time");
		System.out.println("");
		System.out.println("TICKETING");
		System.out.println("6.Book Ticket");
		System.out.println("7.Check Booking Status");
		System.out.println("");
		System.out.println("8.Exit");
	}
	private static void printCustomerMenu() {
		System.out.println("Please select your option");
		System.out.println("MOVIE HIGHLIGHTS");
		System.out.println("1.Now Showing");
		System.out.println("2.Coming Soon");
		System.out.println("");
		System.out.println("SHOWTIME");
		System.out.println("3.Select Cinema, Select Movie, Select Date and Time");
		System.out.println("");
		System.out.println("TICKETING");
		System.out.println("4.Book Ticket");
		System.out.println("5.Check Booking Status");
		System.out.println("");
		System.out.println("6.Exit");
	}
	private static void printStaffMenu() {
		System.out.println("Hello "+((Staff)person).getUserName());
		System.out.println("Please select your option");
		System.out.println("");
		System.out.println("MOVIE SETTINGS");
		System.out.println("1.Show All Movie");
		System.out.println("2.Add Movie");
		System.out.println("3.Update Movie Status");
		System.out.println("4.Remove Movie");
		System.out.println("");
		System.out.println("SHOWTIME SETTINGS");
		System.out.println("5.View All Showtime");
		System.out.println("6.Add Showtime");
		System.out.println("7.Update Showtime Details");
		System.out.println("8.Remove Showtime");
		System.out.println("");
		System.out.println("SALE REVENUE REPORT");
		System.out.println("9.Print by Cineplex");
		System.out.println("10.Print by Period");
		System.out.println("11.Print by Movie");
		System.out.println("");
		System.out.println("HOLIDAY");
		System.out.println("12.Add Holiday");
		System.out.println("13.Remove Holiday");
		System.out.println("");
		System.out.println("PRICE");
		System.out.println("14.Update Price");
		System.out.println("");
		System.out.println("15.Exit");
	}
	private static void publicRegister() throws LoginException {
		String username;
		String password;
		String name;
		String mobileNumber;
		String email;
		int age;
		boolean status;
		System.out.print("Username: ");
		username = in.nextLine();
		System.out.print("Password: ");
		password = in.nextLine();
		System.out.print("Name: ");
		name = in.nextLine();
		System.out.print("Age (if >60 as senior citizen): ");
		age = in.nextInt();
		in.nextLine();
		System.out.print("Mobile Number: ");
		mobileNumber = in.nextLine();
		System.out.print("E-mail: ");
		email = in.nextLine();
		status = ((Public)person).registerAccount(username, password, name, age, mobileNumber, email);
		
		if(status){
			person = ((Public)person).login(username, password);
			System.out.println("Register success");
			System.out.println("You have login");
		}else{
			System.out.println("Your username has been used");
		}
	}
	private static void publicLogin() throws LoginException {
		String username;
		String password;
		System.out.print("Username: ");
		username = in.nextLine();
		System.out.print("Password: ");
		password = in.nextLine();
		person = ((Public)person).login(username, password);
		if(!(person instanceof Public)){
			System.out.println("login success");
		}else{
			System.out.println("login failed");
		}
	}
	
	private static ArrayList<ShowTime> printShowTime() throws MovieListingException, CineplexException, ShowTimeException {
		String location=null,movieName=null;
		GregorianCalendar date=null;
		int userInput;
		ArrayList<ShowTime> st;
		userInput = 0;
		while(userInput!=4){
			printQueryMovieSelectionMenu();
			userInput = in.nextInt();
			in.nextLine();
			
			switch (userInput) {
			case 1:
				Cineplex cineplex = cineplexPicker();
				location = cineplex.getName();
				break;
			case 2:
				Movie movie = moviePicker(Status.NOW_SHOWING,false);
				movieName = movie.getName();
				break;
			case 3:
				date = datePicker();
				break;
			}
		}
		st = person.queryMovie(movieName, location, date);	//showtime

		int i = 1;
		System.out.println("Index\tDate\t    Time\tMovie\t\tType\t\tCineplex\t\tCinema\tUsual Price");
		for (ShowTime showtime : st) {
			System.out.println(i+". \t"+
					calendarToSting(showtime.getDateTime())+"\t"+
					showtime.getMovie().getName()+"\t"+
					showtime.getMovie().getType() + "\t" +
					showtime.getCineplex().getName()+"\t\t"+
					showtime.getNoOfCinema()+ "\t$ " + person.getPrice(showtime.getMovie().getType()));
			i++;
		}
		return st;
	}
	private static void printQueryMovieSelectionMenu() {
		System.out.println("Please select your options");
		System.out.println("Or enter 4 to continue");
		System.out.println("1.Select location");
		System.out.println("2.Select movie");
		System.out.println("3.Select date");
		System.out.println("4.Continue");
	}
	public static String calendarToSting(GregorianCalendar date){
		String s;
		StringBuilder sb = new StringBuilder();
		if((date.get(Calendar.DAY_OF_MONTH))<10){
			sb.append("0");
		}
		sb.append(date.get(Calendar.DAY_OF_MONTH));
		sb.append("/");
		if((date.get(Calendar.MONTH)+1)<10){
			sb.append("0");
		}
		sb.append((date.get(Calendar.MONTH)+1));
		sb.append("/");
		sb.append(date.get(Calendar.YEAR));
		sb.append("  ");
		if((date.get(Calendar.HOUR_OF_DAY))<10){
			sb.append("0");
		}
		
		sb.append(date.get(Calendar.HOUR_OF_DAY));
		sb.append(":");
		if((date.get(Calendar.MINUTE))<10){
			sb.append("0");
		}
		sb.append(date.get(Calendar.MINUTE));
		s = sb.toString();
		return s;
	}
	private static String movieRatingPicker() {
		int input=0;
		String rating=null;
		while (input>4 || input<1){
			System.out.println("Please pick a rating");
			System.out.println("1." + Movie.RATING_PG);
			System.out.println("2." + Movie.RATING_U);
			System.out.println("3." + Movie.RATING_18);
			//not a good way
			input = in.nextInt();
			in.nextLine();
			if (input == 1) {
				rating = Movie.RATING_PG;
			} else if (input == 2) {
				rating = Movie.RATING_U;
			} else if (input == 3) {
				rating = Movie.RATING_18;
			} else{
				System.out.println("Please enter again");
			}
		}
		return rating;
	}
	private static int[] seatPicker(SeatLayout layout){
		int[] rowCol;
		System.out.print("Please pick a seat: ");
		String input = in.nextLine();
		char[] c = input.toCharArray();
		rowCol = new int[2];
		rowCol[0] = Character.toUpperCase(c[0]) - 'A';
		rowCol[1] = c[1] - '1';
		if(c.length==3&&c[1]=='1'&&c[2]=='0'){
			rowCol[1] = 10-1;
		}
		while (layout.getSeats(rowCol[0], rowCol[1]).getAvailable()){
			System.out.println("The seat is not available");
			System.out.println("Please try again");
			printLayout(layout);
			System.out.print("Please pick a seat: ");
			input = in.nextLine();
			c = input.toCharArray();
			rowCol = new int[2];
			rowCol[0] = c[0] - 'A';
			rowCol[1] = c[1] - '1';
			if(c.length==3&&c[1]=='1'&&c[2]=='0'){
				rowCol[1] = 10;
			}
		}
		return rowCol;
	}
	private static String movieTypePicker() {
		int input=0;
		String type=null;
		while (input>4 || input<1){
			System.out.println("Please pick a movie type");
			System.out.println("1." + Movie.TYPE_REGULAR);
			System.out.println("2." + Movie.TYPE_DIGITAL);
			System.out.println("3." + Movie.TYPE_3D);
			//not a good way
			input = in.nextInt();
			in.nextLine();
			if (input == 1) {
				type= Movie.TYPE_REGULAR;
			} else if (input == 2) {
				type= Movie.TYPE_DIGITAL;
			} else if (input == 3) {
				type= Movie.TYPE_3D;
			} else{
				System.out.println("Please enter again");
			}
		}
		return type;
	}
	private static Status statusPicker() {
		Status status = null;
		int userOption;
		System.out.println("Please select a status:");
		System.out.println("1."+Status.COMING_SOON);
		System.out.println("2."+Status.PREVIEW);
		System.out.println("3."+Status.NOW_SHOWING);
		System.out.println("4."+Status.END_OF_SHOWING);
		userOption = in.nextInt();
		in.nextLine();
		switch (userOption) {
		case 1:
			status = Status.COMING_SOON;
			break;
		case 2:
			status = Status.PREVIEW;
			break;
		case 3:
			status = Status.NOW_SHOWING;
			break;
		case 4:
			status = Status.END_OF_SHOWING;
			break;
		default:
			break;
		}
		return status;
	}
	private static Movie moviePicker(Status status,boolean showStatus) throws MovieListingException {
		int index=0;
		int userInput;
		ArrayList<MovieListing> ml = person.queryMovie(status);
	
		if(!showStatus){
			for (MovieListing movie : ml) {
				System.out.println(index+"\t"+movie.getMovie().getName());
				index++;
			}
		}
		else{
			for (MovieListing movie : ml) {
				System.out.println(index+"\t"+movie.getMovie().getName()+"\t"+movie.getStatus());
				index++;
			}
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
	private static Cineplex cineplexPicker() throws CineplexException {
		int index=0;
		int userInput;
		ArrayList<Cineplex> cineplexes = person.getCineplex();
		if(cineplexes==null||cineplexes.size()==0){
			throw new NullPointerException("No cineplex found");
		}
		
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
