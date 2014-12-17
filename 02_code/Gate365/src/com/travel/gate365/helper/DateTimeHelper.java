package com.travel.gate365.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.travel.gate365.R;

import android.content.Context;

public class DateTimeHelper {

	public DateTimeHelper() {
	}

	public static synchronized String convertTimeToString(Context context, long pDateTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd - hh:mm a", Locale.getDefault());
		String[] dayOfWeeks = context.getResources().getStringArray(R.array.daysOfWeek);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(pDateTime);
		return dayOfWeeks[cal.get(Calendar.DAY_OF_WEEK) - 1] + ", " + dateFormat.format(cal.getTime());
	}

	public static synchronized String[] convertDateStringToWWW_MMMddyyyy(Context context, String dateTime, String time) {
		try {
			String[] arr = dateTime.split(" ");
			String[] arrDate = arr[0].split("/");
			String[] arrTime = time.split(":");

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrDate[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(arrDate[1]) - 1);
			cal.set(Calendar.YEAR, Integer.parseInt(arrDate[2]));
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrTime[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(arrTime[1]));

			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
			String[] dayOfWeeks = context.getResources().getStringArray(R.array.daysOfWeek);
			return new String[] { dayOfWeeks[cal.get(Calendar.DAY_OF_WEEK) - 1] + ", " + dateFormat.format(cal.getTime()),
					timeFormat.format(cal.getTime()) };
		} catch (Throwable t) {
		}
		return new String[] { "", "" };
	}

	public static synchronized String convertDateStringToWWW_ddMMMyyyy(Context context, String dateTime) {
		try {
			String[] arr = dateTime.split(" ");
			String[] arrDate = arr[0].split("/");
			String[] arrTime = arr[1].split(":");

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrDate[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(arrDate[1]) - 1);
			cal.set(Calendar.YEAR, Integer.parseInt(arrDate[2]));
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrTime[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(arrTime[1]));

			SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
			String[] dayOfWeeks = context.getResources().getStringArray(R.array.daysOfWeek);

			return new String(dayOfWeeks[cal.get(Calendar.DAY_OF_WEEK) - 1] + ", " + dateFormat.format(cal.getTime()) + " - "
					+ timeFormat.format(cal.getTime()));
		} catch (Throwable t) {
		}
		return "";
	}

	public static synchronized String convertDateStringToddMMyyyy(String dateTime) {
		String[] arr = dateTime.split(" ");
		String[] arrDate = arr[0].split("/");
		String[] arrTime = arr[1].split(":");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arrDate[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(arrDate[1]) - 1);
		cal.set(Calendar.YEAR, Integer.parseInt(arrDate[2]));
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(arrTime[0]));
		cal.set(Calendar.MINUTE, Integer.parseInt(arrTime[1]));

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault());

		return new String(dateFormat.format(cal.getTime()));
	}
}