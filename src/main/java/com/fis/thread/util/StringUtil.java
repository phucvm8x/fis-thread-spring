package com.fis.thread.util;

/**
 * @author PhucVM3
 * @date 16/05/2018 14:39:24
 */
public class StringUtil {

	public static boolean isNotNullOrEmpty(String st) {
		return st != null && !st.equals("");
	}
	
	public static String addZeroBefore(String s, int maxLength) {
		if (s == null) {
			return s;
		}
		String zeroStr = "";
		for (int i = 1; i <= maxLength - s.length(); i++) {
			zeroStr += "0";
		}
		return zeroStr + s;
	}
}
