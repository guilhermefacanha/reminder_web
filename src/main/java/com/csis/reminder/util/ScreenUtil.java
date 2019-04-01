package com.csis.reminder.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import org.joda.time.DateTime;

import com.csis.reminder.entity.Event;

/**
 * @author Reminder Group
 * Util class for SWING screen
 */
public class ScreenUtil {

	
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	public static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm";
	private static LocalTime t = LocalTime.now(ZoneId.of("America/Los_Angeles"));
	private static LocalDate today = LocalDate.now(ZoneId.of("America/Los_Angeles"));
	

	/**
	 * Method to resize screen based on the users monitor size
	 * @param rate {@link Double} percent rate of the screen to cover
	 * @return {@link Rectangle} rectangle object with dimensions for the screen
	 */
	public static Rectangle resizeScreen(double rate) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Double width = screenSize.getWidth() * rate;
		Double height = screenSize.getHeight() * rate;

		return new Rectangle(100, 100, width.intValue(), height.intValue());
	}
	
	/**
	 * Method to center screen for a window
	 * @param frame {@link Window} class be centered
	 */
	public static void centerWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
	
	public static MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}

	public static boolean isDateValid(String text, String dateTimeFormat) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateTimeFormat);
			format.setLenient(false);
			format.parse(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public static boolean isDatePassed(String text, String dateTimeFormat) {
						
	//	if ( t.getHour() >= this.startTime  &&   t.getHour() <= this.endTime )	{
		boolean status = false;
		
		try {
		//	SimpleDateFormat format = new SimpleDateFormat(dateTimeFormat);
    	     LocalDate dt = LocalDate.parse(text, DateTimeFormatter.ofPattern(dateTimeFormat));
	 		if(dt.isBefore(today))  status = true;
	   } catch (Exception e) {
			status = false;
		   }
		return status;
	}
	
	public static boolean isNotificationDatePastEvent(String notificationDateStr, String dateTimeFormat, Event event)	{
		boolean status = false;
		
		try	{
			// Parses the notificationDate from String to LocalDate
			LocalDate notificationDate= LocalDate.parse(notificationDateStr, DateTimeFormatter.ofPattern(dateTimeFormat));
			LocalDate eventLocalDate = LocalDate.parse(event.getDateStr(), DateTimeFormatter.ofPattern(dateTimeFormat ));
			
			// Compares if the notificationDate is after the eventDate, which it should not
			if (notificationDate.isAfter(eventLocalDate))	{
				status = true;
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error parsing dates!","Date Parsing Error", JOptionPane.ERROR_MESSAGE);			
		}
		return status;
	}
	
	public static String getStringDeData(Date data, String formato) {
		DateFormat formatter = new SimpleDateFormat(formato);
		return formatter.format(data).toString();
	}
	
	public static Date getDateAddMinute(Date date, int add) {
		DateTime time = new DateTime(date);
		time = time.plusMinutes(add);
		return time.toDate();
	}
	
}
