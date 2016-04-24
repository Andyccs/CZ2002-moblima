package com.moblima.transaction;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.moblima.util.Connector;

public class PublicHoliday extends Connector{
	private LinkedList<GregorianCalendar> publicHoliday;
	private static final String FILE_PATH="entities/publicHoliday";
	
	public PublicHoliday() {
	}

	public boolean addHoliday(GregorianCalendar date){
		long dateMS = date.getTimeInMillis();
		long calendarMS;
		publicHoliday = (LinkedList<GregorianCalendar>) openEntity(LinkedList.class,FILE_PATH);
		for (GregorianCalendar calendar : publicHoliday) {
			calendarMS = calendar.getTimeInMillis();
			if(calendarMS==dateMS){
				writeToFile(publicHoliday, FILE_PATH);
				return false;
			}
		}
		publicHoliday.add(date);
		writeToFile(publicHoliday, FILE_PATH);
		return true;
	}
	
	public boolean deleteHoliday(GregorianCalendar date){
		long dateMS = date.getTimeInMillis();
		long calendarMS;
		publicHoliday = (LinkedList<GregorianCalendar>) openEntity(LinkedList.class,FILE_PATH);	
		
		for (GregorianCalendar calendar : publicHoliday) {
			calendarMS = calendar.getTimeInMillis();
			if(calendarMS==dateMS){
				publicHoliday.remove(calendar);
				writeToFile(publicHoliday, FILE_PATH);
				return true;
			}
		}
		writeToFile(publicHoliday, FILE_PATH);
		return false;
	}
	
	
	public boolean isHoliday(GregorianCalendar date){
		date.set(GregorianCalendar.HOUR_OF_DAY, 0);
		date.set(GregorianCalendar.MINUTE, 0);
		long dateMS = date.getTimeInMillis();
		long calendarMS;
		
		publicHoliday = (LinkedList<GregorianCalendar>) openEntity(LinkedList.class,FILE_PATH);

		for (GregorianCalendar calendar : publicHoliday) {
			calendarMS = calendar.getTimeInMillis();
			if(calendarMS==dateMS){
				return true;
			}
		}
		writeToFile(publicHoliday, FILE_PATH);
		return false;
	}
	
	public LinkedList<GregorianCalendar> getPublicHoliday() {
		publicHoliday = (LinkedList<GregorianCalendar>) openEntity(LinkedList.class,FILE_PATH);

		LinkedList<GregorianCalendar> temp = publicHoliday;
		writeToFile(publicHoliday, FILE_PATH);
		return temp;
	}
}
