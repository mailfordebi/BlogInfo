package com.dd.blog.resources;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static final String DATE_FORMAT = "MMMM dd, yyyy";

	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(Util.DATE_FORMAT);
		if (date != null) {
			return formatter.format(date);
		}
		return "";
	}
}
