package com.example.demo.utils;

import java.util.Date;

public class CafeUtils {

	public  static String getUUID() {
		Date date = new Date();
		long time = date.getTime();
		return "Bill"+time;
	}
}
