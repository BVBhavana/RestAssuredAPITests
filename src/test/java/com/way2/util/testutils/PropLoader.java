package com.way2.util.testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.way2.cucumber.steps.Common;


public class PropLoader {
	Common common = new Common();

	private static Logger logger = LogManager.getLogger(PropLoader.class);
	private Properties properties;
	private File file;
	private InputStream input;
	private static String envName;
	private static String regionName;
	private static String path;

	public PropLoader() throws IOException {


		try {

			envName = System.getProperty("env");
			regionName = System.getProperty("region");
//			common.setEnv(envName);
					
			if(regionName != null) {
				findRegionInList(regionName, new String[] {Constant.DEFAULTREGION, Constant.SECONDREGION});
			}
						
			if (common.getEnv() == null && regionName == null ) {
				logger.info("We are on development environment and pointing to us-east-1 region");
				path = "src/test/resources/Propertiesfiles/config_development_us-east-1.properties";
				envName = Constant.DEFAULTENV.toLowerCase();
				regionName = Constant.DEFAULTREGION;
				logger.info("Dev properties are loaded by default");
			} 
			else {
				logger.info("We are on " + envName + " environment and pointing to " + regionName + " region");
				path = "src/test/resources/Propertiesfiles/config_" + envName + "_" + regionName + ".properties";				
			}

			file = new File(path);
			input = new FileInputStream(file);
			properties = new Properties();
			properties.load(input);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Failed to Load Property File");
			// assertFail("Failed to Load Property File");
		}
	}

	public String propertyReader(String key) {
		return properties.getProperty(key);
	}

	public static Function<String, String> props = (String key) -> {
		try {
			String appValue = new PropLoader().propertyReader(key);
			logger.info("Application Configuration " + key + " value " + "'" + appValue + "'" + " loaded successfully");
			return new PropLoader().propertyReader(key);
		} catch (IOException e) {
			logger.info("No Such variable exists");
			// assertFail("No Such variable exists");
		}
		return null;
	};
	
	public static boolean findRegionInList(String inputRegion, String[] items) {
	    return Arrays.stream(items).anyMatch(inputRegion::contains);
	}
}
