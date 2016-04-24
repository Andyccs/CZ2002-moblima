package com.moblima.transaction;

import java.util.GregorianCalendar;

import com.moblima.movie.Cinema;
import com.moblima.movie.Cineplex;
import com.moblima.movie.Movie;

public class Discount {

	public Discount(){
		
	}
	
	public double getDiscount(int age, GregorianCalendar dateTime,Cineplex cineplex, int cinema, Movie movie){
		double discount=0;
		PublicHoliday holiday=new PublicHoliday();
		
		if(age>60)	//if senior citizen, don't care public holiday
			discount -= 5.5;
		else{ //not senior citizen
			if(holiday.isHoliday((GregorianCalendar)dateTime.clone()))	//public holiday
				discount+=5;
			else if(dateTime.get(GregorianCalendar.DAY_OF_WEEK)==GregorianCalendar.MONDAY||
					dateTime.get(GregorianCalendar.DAY_OF_WEEK)==GregorianCalendar.TUESDAY||
					dateTime.get(GregorianCalendar.DAY_OF_WEEK)==GregorianCalendar.WEDNESDAY){
				if(!(movie.getType().equals(Movie.TYPE_3D))){
					discount -=1;
				}
			}
			else if(dateTime.get(GregorianCalendar.DAY_OF_WEEK)==GregorianCalendar.FRIDAY||
					dateTime.get(GregorianCalendar.DAY_OF_WEEK)==GregorianCalendar.SATURDAY||
					dateTime.get(GregorianCalendar.DAY_OF_WEEK)==GregorianCalendar.SUNDAY){
				if(movie.getType().equals(Movie.TYPE_3D)){
					discount += 3;
				}else{
					discount +=1.5;
				}
			}	
		}
		
		//no matter who you are
		if(cineplex.getCinema().get(cinema-1).getCinemaClass().equals(Cinema.CINEMA_CLASS_PLATINIUM)){
			discount+=5;
		}
		return discount;
	}
	

	
}
