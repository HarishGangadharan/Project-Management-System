package com.ideas2it.employeemanagementsystem.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.ideas2it.employeemanagementsystem.commons.constants.Constants;
import com.ideas2it.employeemanagementsystem.exception.PMSApplicationException;

/**
 * <p>
 * Consist of certain methods which performs operations regarding dates like 
 * obtaining the current date, calculating the year difference.
 * </p>
 *
 * @author Harish
 */
public class DateUtil {

    private static String PARSEEXCEPTION = "Error: given date cannot be parsed";

    /**
     * <p>
     * Calculate the year difference by obtaining the current year from the
     * specified date.
     * </p>
     *
     * @param   date      date from which year is to calculated.
     *
     * @return            calculated year difference.
     */
    public static int calculateYearDifference(Date date) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTime(date);
        return (currentYear - calendar.get(Calendar.YEAR));
    }
   
    /**
     * <p>
     * Converts the given input date to string format using simpledateformat.
     * </p>
     *
     * @param      inputDate     input in the form of date.
     *
     * @return                   date which is convert to the string format.
     */
    public static String convertDateToString(Date inputDate) {
       SimpleDateFormat dateFormat =
            new SimpleDateFormat(Constants.LABEL_DOB_FORMAT);
        return (dateFormat.format(inputDate));
    }

    /**
     * <p>
     * Converts the given input String  to date format using simpledateformat.
     * </p>
     *
     * @param      inputString     input in the form of string.
     *
     * @return     date            string which is convert to the date format.
     */
    public static Date convertStringToDate(String inputString) 
            throws PMSApplicationException {

        SimpleDateFormat dateFormat =
            new SimpleDateFormat(Constants.LABEL_DOB_FORMAT);
        Date date= null;
        try {
            date = dateFormat.parse(inputString);
        } catch (ParseException exception) {
            throw new PMSApplicationException(PARSEEXCEPTION, exception);
        }
        return date;
    }

    /**
     * <p>
     *  Get current date.
     * </p>
     *
     * @param     current date 
     */
    public static Date getCurrentDate() throws PMSApplicationException {
        SimpleDateFormat dateFormat = 
            new SimpleDateFormat(Constants.LABEL_DOB_FORMAT);
	Date date = new Date();
        return convertStringToDate(dateFormat.format(date));
    }

    /**
     * <p>
     *  Get date which is 18 years less than todays date.
     * </p>
     *
     * @param     years   Number of years to be reduced. 
     * 
     * @param     date    date with the reduced years.
     */
    public static Date getDateReducedByYears(int years)
            throws PMSApplicationException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentDate());
        cal.add(Calendar.YEAR, -years);
        Date date = cal.getTime();
        return date;
    }
}
