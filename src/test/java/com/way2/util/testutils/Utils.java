package com.way2.util.testutils;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

	private static final Logger log = LogManager.getLogger(Utils.class);

	public static String generateGUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();

	}

	// Below function is to generate the code verifier for PKCE    
	public static String generateCodeVerifier() throws UnsupportedEncodingException {
		SecureRandom secureRandom = new SecureRandom();
		byte[] codeVerifier = new byte[32];
		secureRandom.nextBytes(codeVerifier);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
	}

	// Below function is to generate the code_challenge =>    
	// BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))    
	public static String generateCodeChallange(String codeVerifier)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] bytes = codeVerifier.getBytes("US-ASCII");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(bytes, 0, bytes.length);
		byte[] digest = md.digest();
		return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
	}

	public static String timeStamp() {

		Date currentUtcTime = Date.from(Instant.now());
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		return sdf.format(currentUtcTime);
	}

	// This is to print log for the beginning of the test case
	public static void startTestCase(String TestCaseName) {

		log.info("****************************************************************************************");
		log.info("****************************************************************************************");
		log.info("$$$$$$$$$$$$$$$$$$$$$ " + TestCaseName + " $$$$$$$$$$$$$$$$$$$$$$$$$");
		log.info("****************************************************************************************");
		log.info("****************************************************************************************");

	}

	// This is to print log to mark end of the test case
	public static void endTestCase() {

		log.info("XXXXXXXXXXXXXXXXXXXXXXX       " + "-E---N---D-" + "     XXXXXXXXXXXXXXXXXXXXXX");
		log.info("XXXXXXXXXXXXXXXXXXXXXXX***********************************************XXXXXXXXXXXXXXXXXXXXXX");
		log.info("XXXXXXXXXXXXXXXXXXXXXXX***********************************************XXXXXXXXXXXXXXXXXXXXXX");
		log.info("X");
		log.info("X");
		log.info("X");
		log.info("X");
		log.info("X");

	}
	
	public static void threadSleep(long seconds)
	{
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
