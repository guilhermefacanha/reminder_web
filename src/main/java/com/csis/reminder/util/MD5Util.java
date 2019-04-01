package com.csis.reminder.util;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

/**
 * @author Reminder Group
 * Util class to generate hash md5
 */
public class MD5Util {
	/**
	 * Method to generate hash md5 from string
	 * @param text {@link String} string to generate md5 hash
	 * @return {@link String} string with hash generated
	 */
	public static String getMd5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] digest = md.digest();
			String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

			return myHash.toUpperCase();

		} catch (Exception e) {
			return "";
		}
	}
}
