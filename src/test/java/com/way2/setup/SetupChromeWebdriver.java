package com.way2.setup;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class SetupChromeWebdriver {
	private static WebDriver driver = null;
	//	private static String os = System.getProperty("os.name");
	private static ChromeDriverService service;
	private static ChromeOptions options;
	private static final Logger log = LogManager.getLogger(SetupChromeWebdriver.class);

	public static WebDriver getInstance(String browserName, String platform) {
		if (driver == null) {
			driver = getABrowser(browserName, platform);
		}
		return driver;
	}

	private static WebDriver getABrowser(String nameOfBrowser, String os) {

		// System.out.println("OS>>>" + os);
		if ("chrome".equals(nameOfBrowser)) {
			if (os.contains("Windows")) {
				String chromeDriver = System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromeDriver);
				options = getLocalChromeOptionsForWindows();

			} else {
				System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
				service = new ChromeDriverService.Builder()
						.usingDriverExecutable(new File("/usr/local/bin/chromedriver")).usingAnyFreePort().build();

				options = getLocalChromeOptionsForLinux();
				try {
					service.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		
		// => this is chrome driver with custom options
		//return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
		return new ChromeDriver(options);
	}

	private static ChromeOptions getLocalChromeOptionsForLinux() {
		log.info("Linux Chrome options");
		ChromeOptions options = new ChromeOptions();
		options.setBinary("/usr/bin/google-chrome");
		options.addArguments("--disable-dev-shm-usage");
		//options.addArguments("--incognito");// overcome limited resource problems
		options.addArguments("--headless");
		options.addArguments("start-maximized"); // open Browser in maximized mode
		options.addArguments("--disable-gpu"); // applicable to windows os only
		options.addArguments("--no-sandbox"); // Bypass OS security model
		options.addArguments("chrome.switches","--disable-extensions"); 
		options.addArguments("--disable-save-password");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-notifications");
		return options;
	}

	private static ChromeOptions getLocalChromeOptionsForWindows() {
		log.info("Windows Chrome options");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		options.addArguments("chrome.switches","--disable-extensions"); 
		options.addArguments("--disable-save-password");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-notifications");
		//options.addArguments(Arrays.asList("--headless", "--disable-gpu"));
		return options;
	}
}
