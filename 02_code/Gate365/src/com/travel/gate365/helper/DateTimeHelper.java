package com.travel.gate365.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.travel.gate365.R;

import android.content.Context;

public class DateTimeHelper {

	public DateTimeHelper() {
	}
	
	public static String[] convertDateStringToWWW_MMMddyyyy(Context context, String dateTime, String time){
		Date date = new Date();
		String[] arr = dateTime.split(" ");
		String[] arrDate = arr[0].split("/");
		String[] arrTime = time.split(":");
		date.setDate(Integer.parseInt(arrDate[0]));
		date.setMonth(Integer.parseInt(arrDate[1]) - 1);
		date.setYear(Integer.parseInt(arrDate[2]) - 1900);
		date.setHours(Integer.parseInt(arrTime[0]));
		date.setMinutes(Integer.parseInt(arrTime[1]));
		
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		String [] dayOfWeeks = context.getResources().getStringArray(R.array.daysOfWeek);
				
		return new String[] {dayOfWeeks[date.getDay()].toString() + ", " + dateFormat.format(date), timeFormat.format(date)};
	}

	public static String convertDateStringToWWW_ddMMMyyyy(Context context, String dateTime){
		Date date = new Date();
		String[] arr = dateTime.split(" ");
		String[] arrDate = arr[0].split("/");
		String[] arrTime = arr[1].split(":");
		date.setDate(Integer.parseInt(arrDate[0]));
		date.setMonth(Integer.parseInt(arrDate[1]) - 1);
		date.setYear(Integer.parseInt(arrDate[2]) - 1900);
		date.setHours(Integer.parseInt(arrTime[0]));
		date.setMinutes(Integer.parseInt(arrTime[1]));
		
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		String [] dayOfWeeks = context.getResources().getStringArray(R.array.daysOfWeek);
				
		return new String(dayOfWeeks[date.getDay()].toString() + ", " + dateFormat.format(date) + " - " + timeFormat.format(date));
	}

	public static String convertDateStringToddMMyyyy(Context context, String dateTime){
		Date date = new Date();
		String[] arr = dateTime.split(" ");
		String[] arrDate = arr[0].split("/");
		String[] arrTime = arr[1].split(":");
		date.setDate(Integer.parseInt(arrDate[0]));
		date.setMonth(Integer.parseInt(arrDate[1]) - 1);
		date.setYear(Integer.parseInt(arrDate[2]) - 1900);
		date.setHours(Integer.parseInt(arrTime[0]));
		date.setMinutes(Integer.parseInt(arrTime[1]));
		
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
				
		return new String(dateFormat.format(date));
	}
	
}
