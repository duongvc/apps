package com.travel.gate365.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.travel.gate365.R;

import android.content.Context;

public class DateTimeHelper {

	public DateTimeHelper() {
	}
	
    public static synchronized String convertTimeToString(Context context, long pDateTime){
    	Date date = new Date(pDateTime);
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd - hh:mm a");
		/*StringBuilder stDate = new StringBuilder().append(pad(date.getDate()))
				.append("-").append(pad(date.getYear() + 1900))
				.append("-").append(date.getMonth() + 1)
				.append(" - ").append(pad(date.getHours()))
				.append(":").append(pad(date.getMinutes()));*/
				//.append(":").append(pad(date.getSeconds()));
		String [] dayOfWeeks = context.getResources().getStringArray(R.array.daysOfWeek);
				
		//return stDate.toString();
		return dayOfWeeks[date.getDay()].toString() + ", " + dateFormat.format(date);
    }
	
	public static synchronized String[] convertDateStringToWWW_MMMddyyyy(Context context, String dateTime, String time){
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

	public static synchronized String convertDateStringToWWW_ddMMMyyyy(Context context, String dateTime){
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

	public static synchronized String convertDateStringToddMMyyyy(String dateTime){
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

	/**
	 * Add padding to numbers less than ten
	 * @param pC
	 * @return
	 */
    public static synchronized String pad(int pC) {
        if (pC >= 10)
            return String.valueOf(pC);
        else
            return "0" + String.valueOf(pC);
    }	
    
	
}
