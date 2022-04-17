package com.technovator.api.common.utils;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	public static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);
	private static final FastDateFormat GERMAN_TIMESTAMP = FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss.SSS");
	public static final String GERMAN_DATE_TIME = "dd.MM.yyyy HH:mm:ss";
	public static final String JSON_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
	public static final String GERMAN_DATE = "dd.MM.yyyy";
	public static final String JSON_DATE = "yyyy-MM-dd";
	public static final FastDateFormat germanDateTimeFormat =FastDateFormat.getInstance(GERMAN_DATE_TIME);
	public static final FastDateFormat germanDateTimeFormatUtc = FastDateFormat.getInstance(GERMAN_DATE_TIME);
	public static final FastDateFormat jsonDateTimeFormat = FastDateFormat.getInstance(JSON_DATE_TIME);
	public static final FastDateFormat germanDateFormat = FastDateFormat.getInstance(GERMAN_DATE);
	public static final FastDateFormat jsonDateFormat = FastDateFormat.getInstance(JSON_DATE);
	
	public static String formatTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		
		return GERMAN_TIMESTAMP.format(date);
	}
	public static Date toStartOfDay(Date date) {
		if (date == null)
			return null;
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static Date toEndOfDay(Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));

		return cal.getTime();
	}

	public static String msToNamedTimeInterval(long milliseconds) {
		long millis = milliseconds % 1000;
		long second = (milliseconds / 1000) % 60;
		long minute = (milliseconds / (1000 * 60)) % 60;
		long hour = (milliseconds / (1000 * 60 * 60)) % 24;	
		StringBuilder sb = new StringBuilder();
		if (hour > 0) {
			sb.append(hour).append(hour==1?" hour":" hours");
		}
		if (minute > 0) {
			sb.append(sb.length()==0?"":" ").append(minute).append(minute==1?" minute":" minutes");
		}
		if (second > 0) {
			sb.append(sb.length()==0?"":" ").append(second).append(second==1?" second":" seconds");
		}
		if (millis > 0) {
			sb.append(sb.length()==0?"":" ").append(millis).append(millis==1?" millisecond":" milliseconds");
		}
		
		return sb.toString();
	}
	public static boolean isDateTimeFromJsonFormat(String jsonDateTimeString) {
		return toDateTimeFromJsonFormat(jsonDateTimeString) != null;
	}

	public static boolean isDateFromJsonFormat(String jsonDateString) {
		return toDateFromJsonFormat(jsonDateString) != null;
	}
	public static Date toDateTimeFromJsonFormat(String jsonDateTimeString) {
		try {
			return jsonDateTimeFormat.parse(jsonDateTimeString);
		} catch (ParseException e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}
	public static Date toDateTimeFromGermanFormat(String jsonDateTimeString) {
		try {
			return germanDateTimeFormat.parse(jsonDateTimeString);
		} catch (ParseException e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}
	public static Date toDateFromJsonFormat(String jsonDateString) {
		try {
			return jsonDateFormat.parse(jsonDateString);
		} catch (ParseException e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}
	
	public static ZonedDateTime  toZonedDateTime(String str) {
		if (str == null) return null;
		try {
			return ZonedDateTime.parse(str);
		} catch (DateTimeParseException e) {
			LOG.warn(e.getMessage());
			return null;
		}
	}
}
