package com.ats.adminpanel.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateConvertor {

	public static String convertToYMD(String date) {

		String convertedDate = null;
		try {
			SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dmySDF = new SimpleDateFormat("dd-MM-yyyy");
			Date dmyDate = dmySDF.parse(date);

			convertedDate = ymdSDF.format(dmyDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDate;

	}

	public static String convertToDMY(String utildate) {

		String convertedDate = null;
		try {
			SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-mm-dd");
			SimpleDateFormat ymdSDF2 = new SimpleDateFormat("yyyy-mm-dd");

			SimpleDateFormat dmySDF = new SimpleDateFormat("dd-mm-yyyy");

			Date ymdDate = ymdSDF2.parse(utildate);

			convertedDate = dmySDF.format(ymdDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDate;

	}

	public static java.sql.Date convertToSqlDate(String date) {

		java.sql.Date convertedDate = null;

		try {
			SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-mm-dd");
			SimpleDateFormat dmySDF = new SimpleDateFormat("dd-MM-yyyy");

			Date dmyDate = dmySDF.parse(date);

			System.out.println("converted util date commons " + dmyDate);

			convertedDate = new java.sql.Date(dmyDate.getTime());
			System.out.println("converted sql date commons " + convertedDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return convertedDate;

	}

	public static long findDifference(String start_date, String end_date) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		long noOfDays = 0;

		try {

			Date d1 = sdf.parse(start_date);
			Date d2 = sdf.parse(end_date);
			
			
			// Calucalte time difference
			// in milliseconds
			long difference_In_Time = d2.getTime() - d1.getTime();

			// Calucalte time difference in seconds,
			// minutes, hours, years, and days
			long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;

			long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;

			long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;

			long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;

			long difference_In_Years = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;

			noOfDays = difference_In_Days;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return noOfDays;
	}

}
