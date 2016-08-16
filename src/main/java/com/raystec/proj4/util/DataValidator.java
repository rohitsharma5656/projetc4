package com.raystec.proj4.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class validates input data
 * 
 * @author Builder Pattern
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

public class DataValidator {
	/**
	 * Checks if value is Null
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isNull(String val) 
	{
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
		
		
	}

	

	
	/**
	 * Checks if value is NOT Null
	 * 
	 * @param val
	 * @return
	 */

	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	/**
	 * Checks if value is an Integer
	 * 
	 * @param val
	 * @return
	 */

	public static boolean isInteger(String val) {
		if (isNotNull(val)) {
			try {
				int i = Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is Long
	 * 
	 * @param val
	 * @return
	 */

	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				long i = Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks if value is valid Email ID
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isEmail(String val) {

		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/*tue jan 9 8:19:00 IST 2017
	EE:MM:dd  HH:mm;ss IST Y
*/
	/**
	 * Checks if value is Date
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isDate(String val) {

		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);

		}
		return d != null;
	}

	public static boolean isValidDate(Date date) {

		Date d = new Date();
		SimpleDateFormat sfd2 = new SimpleDateFormat("MM/dd/yyyy");
		d = new Date(sfd2.format(d));

		if (date.compareTo(d) <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean dateValidate(Date d1) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date currentDate = new Date();
		Date todayDate = new Date(formatter.format(currentDate));
		System.out.println("current date -" + todayDate);
		System.out.println("date         -" + d1);

		if (d1.compareTo(todayDate) < 0) {

			return true;

		} else {

			return false;
		}

	}

	/*
	 * public static boolean startDate(Date date) { Calendar
	 * last=Calendar.getInstance(); last.setTime(date); Calendar
	 * today=Calendar.getInstance(); int start= }
	 */
	public static boolean ageLimit(Date date) {

		Calendar dob = Calendar.getInstance();
		dob.setTime(date);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
			age--;
		if (age >= 17) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean dateLimit3(Date date) {

		Calendar dob = Calendar.getInstance();
		dob.setTime(date);
		Calendar today = Calendar.getInstance();
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
			age--;
		if (age >= 50) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * public static boolean isAtValidDate(Date date) { Date d=new Date();
	 * SimpleDateFormat sfd2=new SimpleDateFormat(); if(Date) }
	 **/

	public static boolean isName(String val) {

		String name = "^[a-zA-Z\\s]*$";
		if (isNotNull(val)) {
			try {
				return val.matches(name);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isLastName(String val) {

		String name = "[a-zA-Z]*";
		if (isNotNull(val)) {
			try {
				return val.matches(name);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isPassword(String val) {
		if (val.length() >= 6 && val.length() <= 12) {
			return true;
		}
		return false;
	}

	public static boolean isPhoneNo(String val) {

		String phoneno = "^[0-9]*$";

		if (val.length() == 10 && val.matches(phoneno)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isMobileNo(String val) {
		String reg = "^[7-9][0-9]{9}$";

		if (val.matches(reg)) {
			return true;
		} else

			return false;

	}

	public static boolean isNumber(String val) {
		String reg = "[A-Za-z !@#$%^&*_=-|?/><.,]*";

		if (val.matches(reg)) {
			return false;
		} else
			return true;

	}

	public static boolean isSpecial(String val) {
		String reg = "[A-Za-z0-9 ]*";

		if (val.matches(reg)) {
			return false;
		} else
			return true;
	}

	public static boolean isLengthTen(String val) {
		if (val.length() == 10) {
			return true;
		}
		return false;
	}

	/**
	 * checks password length
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isLengthPass(String val) {
		if (val.length() > 5 && val.length() < 12) {
			return true;
		}
		return false;
	}

	/**
	 * checks if value is Space
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isWhiteSpace(String name) {
		if (name.matches(".*\\s+.*")) {
			return false;

		} else {
			return true;

		}
	}

	public static boolean isSpace(String val) {
		String reg = "[A-Za-z0-9!@#$%^&*_=-|?/><.,]*";
		if (val.matches(reg)) {
			return false;
		} else
			return true;
	}

	public static boolean isAlpha(String name) {
		if (name.matches("!=^[a-zA-Z0-9]*$") || name.matches("!=[a-zA-Z]+$")) {
			return true;
		} else {
			if (name.matches("[0-9]+")) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNegative(String val) {
		String reg = "[+-]";
		if (val.matches(reg)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotAlpha(String name) {
		if (name.matches("!=[a-zA-Z0-9]") || name.matches("!=[0-9]")) {
			return true;
		} else {
			if (name.matches("(?i)[a-z]+(\\s+[a-z]+)*")) {

				return false;
			}

		}
		return true;
	}

	public static boolean isDate1(String val) {

		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate1(val);
		}
		return d != null;
	}

	public static boolean isDateCompare(String startDate, String endDate) {
		if (DataValidator.isNull(startDate)) {
			return false;
		}
		if (DataValidator.isNull(endDate)) {
			return false;
		}
		Date startDateOne = new Date(startDate);
		Date endDateOne = new Date(endDate);
		if (startDateOne.compareTo(endDateOne) >= 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isStartDateCompare(String startDate) {
		Date startDateOne = new Date(startDate);
		Date endDateOne = new Date();
		if (startDateOne.compareTo(endDateOne) < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Test above methods
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// System.out.println(!DataValidator.isWhiteSpace("djd djd"));

		System.out.println(DataValidator.isDateCompare("03/04/16", "03/04/16"));

	}
}
