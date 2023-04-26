package com.Inova.ClickupDashboard.utils;

import com.Inova.ClickupDashboard.exception.impl.AppsCommonErrorCode;
import com.Inova.ClickupDashboard.exception.impl.AppsException;
import com.Inova.ClickupDashboard.exception.impl.AppsRuntimeException;
import org.mortbay.log.Log;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarUtil {

    public static final String DEFAULT_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

    public static final String DEFAULT_DATE_TIME_FORMAT_SECONDS = "dd/MM/yyyy HH:mm:ss";

    public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public static final String MYSQL_DATE_FORMAT = "yyyy-MM-dd";

    public static final String MYSQL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_TIME_FORMAT = "HH:mm";

    public static Date getParsedDate(String date, String dateFormat) throws AppsException {

        Date parsedDate;
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            parsedDate = format.parse(date);
            return parsedDate;
        } catch (ParseException e) {
            throw new AppsException(AppsCommonErrorCode.APPS_EXCEPTION_INVALID_DATE_FORMAT);
        }

    }
    public static String milisocondsToHours(String res){
        long milliseconds = Long.parseLong(res);
        long minutes = (milliseconds / 1000) / 60 % 60;
        long hours=(milliseconds / 1000) / 60/60;
        long seconds = (milliseconds / 1000) % 60;
        String fullTime=String.valueOf(hours)+":"+ String.valueOf(minutes)+":"+String.valueOf(seconds);
        return fullTime;
    }

    public static Date getDefaultParsedDateTime(String date) throws AppsException {
        return getParsedDate(date, DEFAULT_DATE_TIME_FORMAT);
    }

    public static Date getDefaultParsedDateTimeMaskNull(String date) throws AppsException {
        return date != null ? getParsedDate(date, DEFAULT_DATE_TIME_FORMAT) : null;
    }

    public static Date getParsedDateTime(String date) throws AppsException {
        return getParsedDate(date, DEFAULT_DATE_TIME_FORMAT_SECONDS);
    }

    public static Date getDefaultParsedDateOnly(String date) throws AppsException {
        return getParsedDate(date, DEFAULT_DATE_FORMAT);
    }
    public static List<Date> getDatesBetween(String start, String end){
       List<Date> dates = new ArrayList<Date>();
       String str_date = start;
       String end_date = end;
       DateFormat formatter;
       formatter = new SimpleDateFormat("dd/MM/yyyy");
       Date startDate = null;
       try
       {
           startDate = (Date) formatter.parse(str_date);
       } catch (ParseException e)
       {
           e.printStackTrace();
       }
       Date endDate = null;
       try
       {
           endDate = (Date) formatter.parse(end_date);
       } catch (ParseException e)
       {
           e.printStackTrace();
       }
       long interval = 24 * 1000 * 60 * 60;
       long endTime = endDate.getTime();
       long curTime = startDate.getTime();
       while (curTime <= endTime)
       {
           dates.add(new Date(curTime));
           curTime += interval;
       }

       return dates;
  }
    public static Date addYears(Date date, int numberOfYears) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, numberOfYears);

        return calendar.getTime();
    }

    public static Date addMonths(Date date, int numberOfMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, numberOfMonths);

        return calendar.getTime();
    }

    public static Date addDate(Date date, int numberOfDates) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numberOfDates);

        return calendar.getTime();
    }

    public static Date addMinutes(Date date, int numberOfMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, numberOfMinutes);

        return calendar.getTime();
    }

    public static Date addSeconds(Date date, int numberOfSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, numberOfSeconds);

        return calendar.getTime();
    }

    public static Date minusMinutes(Date date, int numberOfMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, (-1) * numberOfMinutes);

        return calendar.getTime();
    }

    public static String getDefaultDateTimeFormat() {
        return DEFAULT_DATE_TIME_FORMAT;
    }

    public static String getDefaultDateFormat() {
        return DEFAULT_DATE_FORMAT;
    }

    public static String getDefaultTimeFormat() {
        return DEFAULT_TIME_FORMAT;
    }

    public static String getDefaultFormattedDateOnly(Date date) {
        return getFormattedDate(date, DEFAULT_DATE_FORMAT);
    }

    public static String getDefaultFormattedDateMaskNull(Date date) {
        if (date != null) {
            return CalendarUtil.getDefaultFormattedDateOnly(date);
        }
        return null;
    }

    public static String getDefaultFormattedDateTimeMaskNull(Date date) {
        if (date != null) {
            return CalendarUtil.getDefaultFormattedDateTime(date);
        }
        return null;
    }

    public static String getDefaultFormattedTimeOnly(Date date) {
        return getFormattedDate(date, DEFAULT_TIME_FORMAT);
    }

    public static String getDefaultFormattedDateTime(Date date) {
        return getFormattedDate(date, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String getDefaultFormattedTimeMaskNull(Date date) {
        if (date != null) {
            return CalendarUtil.getDefaultFormattedTimeOnly(date);
        }
        return null;
    }

    public static String getFormattedDateMaskNull(Date date, String dateFormat) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            return formatter.format(date);
        } else {
            return null;
        }
    }


    public static String getFormattedDate(Date date, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static Date getParsedStartDateTime(String date) throws AppsException {
        date = date.trim().concat(" 00:00:00");
        return getParsedDateTime(date);
    }

    public static Date getParsedEndDateTime(String date) throws AppsException {
        date = date.trim().concat(" 23:59:59");
        return getParsedDateTime(date);
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws AppsException {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;

        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
            xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException ex) {
            throw new AppsException(AppsCommonErrorCode.APPS_INTERNAL_SERVER_ERROR);
        }

        return xmlCalendar;
    }

    public static String getMysqlDateOnlyString(Date date) {
        if (date == null) {
            return null;
        }
        return getFormattedDate(date, MYSQL_DATE_FORMAT);
    }

    public static String getMysqlDateTimeString(Date date) {
        if (date == null) {
            return null;
        }
        return getFormattedDate(date, MYSQL_DATE_TIME_FORMAT);
    }


    public static Date getDateOnly(Date date) {
        return getDateOnly(date, MYSQL_DATE_FORMAT);

    }

    public static Date getDefaultParsedDateOnly(Date date) {
        return getDateOnly(date, DEFAULT_DATE_FORMAT);

    }

    public static Date getDateOnly(Date date, String format) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(format);
        Date todayWithZeroTime = null;
        try {
            todayWithZeroTime = formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            throw new AppsRuntimeException(AppsCommonErrorCode.APPS_EXCEPTION_INVALID_DATE_FORMAT);
        }

        return todayWithZeroTime;
    }

    public static Date getCombinedDateTime(Date date, String time) throws AppsException {
        String formattedDate = CalendarUtil.getDefaultFormattedDateOnly(date);
        formattedDate = formattedDate + " " + time;
        return CalendarUtil.getDefaultParsedDateTime(formattedDate);
    }

    public static String extractTime(Date date) {
        return CalendarUtil.getDefaultFormattedDateTime(date).substring(11, 16);
    }

    public static int getDay(Date date) {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getMonth(Date date) {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(Date date) {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getDate(Date date) {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    public static boolean isBeforeOrEqual(Date checkDate, Date checkAgainstDate) {
        boolean status = false;
        GregorianCalendar checkDateCalendar = getCalendar(checkDate);
        GregorianCalendar checkAgainstDateCalendar = getCalendar(checkAgainstDate);

        if (checkDateCalendar.equals(checkAgainstDateCalendar) || checkDate.before(checkAgainstDate)) {
            status = true;
        }
        return status;
    }

    public static boolean isBetween(Date parseDate, Date fromDate, Date toDate) {

        boolean status = false;
        GregorianCalendar compareDate = getCalendar(parseDate);
        GregorianCalendar dateFrom = getCalendar(fromDate);
        GregorianCalendar dateTo = getCalendar(toDate);

        if (compareDate.after(dateFrom) && compareDate.before(dateTo)) {
            status = true;
        }
        return status;
    }

    public static boolean isBetweenOrEqual(Date parseDate, Date fromDate, Date toDate) {

        boolean status = false;
        GregorianCalendar compareDate = getCalendar(parseDate);
        GregorianCalendar dateFrom = getCalendar(fromDate);
        GregorianCalendar dateTo = getCalendar(toDate);


        if (compareDate.equals(dateFrom) || compareDate.equals(dateTo) || (compareDate.after(
                dateFrom) && compareDate.before(dateTo))) {
            status = true;
        }
        if (dateFrom.after(dateTo)) {
            status = false;
        }
        return status;
    }

    public static boolean isEqual(Date date1, Date date2) {
        GregorianCalendar dateFrom = getCalendar(date1);
        GregorianCalendar dateTo = getCalendar(date2);

        return dateFrom.equals(dateTo);
    }

    private static GregorianCalendar getCalendar(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar;
    }

}