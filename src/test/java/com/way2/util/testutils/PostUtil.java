package com.way2.util.testutils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class PostUtil {
	public static String  generateGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
		
	}
	
	public static String timeStamp() {
		Date currentUtcTime  = Date.from(Instant.now());
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		return sdf.format(currentUtcTime);
	}
}
