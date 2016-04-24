package com.moblima.transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.moblima.movie.Cineplex;
import com.moblima.movie.CineplexEntityConnector;
import com.moblima.movie.MovieListing;
import com.moblima.movie.MovieListingEntityConnector;

public class RevenueReport{
	private TransactionEntityConnector transactionRecord;
	public RevenueReport() {
		transactionRecord = new TransactionEntityConnector();
	}

	public String getReportByPeriod(int year){
		StringBuilder sb = new StringBuilder(10000);
		ArrayList<Cineplex> cineplexes = new CineplexEntityConnector().queryCineplex();

		for (int month = 0; month < 12; month++) {
			GregorianCalendar startTime = new GregorianCalendar(year,month,1);
			GregorianCalendar endTime = new GregorianCalendar(year,month+1,1);
			double cineProfit = 0;
			double monthProfit = 0;
			endTime.add(Calendar.DAY_OF_YEAR, -1);
			sb.append("Period: ").append(new SimpleDateFormat("dd MMM YYYY").format(startTime.getTime())).append(" - ")
					.append(new SimpleDateFormat("dd MMM YYYY").format(endTime.getTime())).append("\n");
			ArrayList<Transaction> resultTransaction = transactionRecord.getTransactionByPeriod(startTime, endTime);

			for (Cineplex cineplex : cineplexes) {
				sb.append(cineplex.getName() + ": ");
				cineProfit = getCineplexProfit(resultTransaction, cineplex);
				sb.append(cineProfit).append("\n");
				monthProfit+=cineProfit;
			}
			sb.append("\n").append(new SimpleDateFormat("MMM").format(new GregorianCalendar(year,month,1).getTime())+" Profit: $" + monthProfit);
			sb.append("\n\n-------------------------\n\n");
		}
		return sb.toString();
	}

	private double getCineplexProfit(ArrayList<Transaction> resultTransaction, Cineplex cine) {
		double cineProfit;
		cineProfit = 0;
		for (Transaction trans : resultTransaction) {
			if (cine.getName().compareTo(trans.getCineplex()) == 0) {
				cineProfit += trans.getProfit();
			}
		}
		return cineProfit;
	}
	
	public String getReportByMovie(){
		StringBuilder sb = new StringBuilder(1000);
		ArrayList<Transaction> transactions;
		ArrayList<Cineplex> cineplexes = new CineplexEntityConnector().queryCineplex();
		ArrayList<MovieListing> movies = new MovieListingEntityConnector().queryMovie();
		
		Calendar c = Calendar.getInstance();
		
		
		for (MovieListing movie: movies) {
			String movieName = movie.getMovie().getName();
			double movieProfit = 0;
			transactions = transactionRecord.queryTransactionByMovie(movieName);
			
			sb.append(movieName).append(" - ").append(new SimpleDateFormat("MMM YYYY").format(c.getTime())).append("\n");
			for (Cineplex cine : cineplexes) {
				double cineplexProfit = 0;
				for (Transaction trans : transactions) {
					if ((trans.getTranMonth() == c.get(Calendar.MONTH) + 1)
							&& trans.getTranYear() == c.get(Calendar.YEAR)){	//get current month
						if (trans.getCineplex().compareTo(cine.getName()) == 0) {
							cineplexProfit += trans.getProfit();
						}
					}
				}
				sb.append(cine.getName()).append(": $").append(cineplexProfit).append("\n");
				movieProfit += cineplexProfit;
			}
			sb.append("\nMovie Profit: $"+movieProfit);
			sb.append("\n------------------------\n\n");
		}
		return sb.toString();
	}
	
	public String getReportByCineplex(){
		ArrayList<Cineplex> cineplexes = new CineplexEntityConnector().queryCineplex();
		StringBuilder sb = new StringBuilder(1000);
		ArrayList<MovieListing> movies = new MovieListingEntityConnector().queryMovie();
		ArrayList<Transaction> transactions;
		double cineplexesProfit=0;
		Calendar c = Calendar.getInstance();
		for(Cineplex cine:cineplexes) {
			String cineplex = cine.getName();
			//String s;
			double movieProfit = 0, cineProfit = 0;
			transactions = transactionRecord
					.queryTransactionByCineplex(cineplex);
			sb.append(cineplex + " - "
					+ new SimpleDateFormat("MMM YYYY").format(c.getTime())
					+ "\n");
			for (MovieListing m : movies) {
				movieProfit = 0;
				sb.append(m.getMovie().getName() + " : $ ");

				for (Transaction trans : transactions) {
					if ((trans.getTranMonth() == c.get(Calendar.MONTH) + 1)
							&& trans.getTranYear() == c.get(Calendar.YEAR))//get current month only
					{
						if (m.getMovie().getName().compareTo(trans.getMovie()) == 0)//get current month only
						{
							movieProfit += trans.getProfit();

						}
					}
				}
				cineProfit += movieProfit;
				sb.append(movieProfit + "\n");
			}
			cineplexesProfit += cineProfit;
			sb.append("\nCineplex profit: $"+ cineProfit + "\n");
			sb.append("--------------------------\n\n");
		}
		sb.append("Total Profit of "+ new SimpleDateFormat("MMM YYYY").format(c.getTime())+": $"+cineplexesProfit+"\n\n\n");
		return sb.toString();
	}

}
