package com.way2.setup;

import static net.serenitybdd.rest.SerenityRest.setDefaultRequestSpecification;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import com.way2.util.testutils.Constant;
import com.way2.util.testutils.PropLoader;
import com.way2.util.testutils.Utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

public class SetupHeaders {

	private static final Logger log = LogManager.getLogger(SetupHeaders.class);
	public static String oAuth2Token;
	public static String oAuthToken;
	private static String platform = Constant.OS_LIN;

	public static void initialSetupNBGServiceToken() {
		RestAssured.useRelaxedHTTPSValidation();
		setDefaultRequestSpecification((new RequestSpecBuilder())

				.setContentType(Constant.CONTENT_TYPE).setAccept("*/*")
				.setBaseUri(PropLoader.props.apply("nbgservicetokenhostname")).build());
	}

	public static void initialSetupWay2RestAssured(String apihostname) {
		RestAssured.useRelaxedHTTPSValidation();
		setDefaultRequestSpecification((new RequestSpecBuilder()).setContentType(Constant.CONTENT_TYPE)
		.setAccept("*/*").setBaseUri(apihostname).build());
	}

	/**
	 * Summary: Generate OAuth2 token for OAUTH client
	 * 
	 * @param oAuth2Token
	 * @return
	 * 
	 * @return OAuth2 token
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws AWTException
	 */

	public static String generateOauth2TokenForLinux()
			throws UnsupportedEncodingException, NoSuchAlgorithmException, AWTException, InterruptedException {

		log.info("Dynamic Token Generation in Linux Environment");
		WebDriver driver = SetupChromeWebdriver.getInstance("chrome", "Linux");
		String userName = PropLoader.props.apply("nbgTestUsername");
		String passWord = PropLoader.props.apply("nbgTestPassword");
		String url = PropLoader.props.apply("nbgdevui");
		driver.get(url);
		Utils.threadSleep(1000);
		driver.findElement(By.id("identifierInput")).sendKeys(userName);
		driver.findElement(By.id("postButton")).click();
		driver.findElement(By.id("password")).sendKeys(passWord);
		driver.findElement(By.xpath("/html/body/form/div[3]/div/a")).click();
		Utils.threadSleep(1000);
		Utils.threadSleep(5000);
		log.info("Url name " + driver.getCurrentUrl());
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String sessionStorage = (String) jsExecutor.executeScript(String.format(
				"return window.sessionStorage.getItem('%s');", "0-FOMAWBBWPR_BelowWingPayloadPlanningRules_OIDC_SI"));

		String[] st = sessionStorage.split(",");
		String[] d = st[11].split("\"access_token\":\"");
		oAuth2Token = d[1].substring(0, d[1].length() - 1);
		log.info("Oauth Token:" ,oAuth2Token);
		driver.quit();
		driver.quit();
		return oAuth2Token;
	}

	public static String generateOauth2TokenForWindows()
			throws UnsupportedEncodingException, NoSuchAlgorithmException, AWTException, InterruptedException {

		log.info("Dynamic Token Generation in Windows Environment");
		WebDriver driver = SetupChromeWebdriver.getInstance("chrome", "Windows");
		String userName = PropLoader.props.apply("nbgTestUsername");
		String passWord = PropLoader.props.apply("nbgTestPassword");
		String url = PropLoader.props.apply("nbgdevui");
		driver.get(url);
		Robot robot = new Robot();
		for (int i = 0; i < 3; i++) {
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Utils.threadSleep(1000);
		driver.findElement(By.id("identifierInput")).sendKeys(userName);
		driver.findElement(By.id("postButton")).click();
		driver.findElement(By.id("password")).sendKeys(passWord);
		driver.findElement(By.xpath("/html/body/form/div[3]/div/a")).click();
		Utils.threadSleep(1200);

		if (driver.getCurrentUrl().contains("authorization.ping")) {
			for (int i = 0; i < 3; i++) {
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
			}
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}
		Utils.threadSleep(15000);
		log.info("Url name " + driver.getCurrentUrl());
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		//Session storage 
		String sessionStorage = (String) jsExecutor.executeScript(String.format(
				"return window.sessionStorage.getItem('%s');", "0-FOMAWBBWPR_BelowWingPayloadPlanningRules_OIDC_SI"));
//		log.info("Session Data" ,sessionStorage);
		String[] st = sessionStorage.split(",");
//		log.info("Data : " ,st[11]);
		String[] d = st[11].split("\"access_token\":\"");
//		log.info("Data : " ,d[1]);
		oAuth2Token = d[1].substring(0, d[1].length() - 1);
		log.info("Oauth Token:" +oAuth2Token);
		driver.quit();
		return oAuth2Token;

	}

	public static String generateOauth2Token()
			throws UnsupportedEncodingException, NoSuchAlgorithmException, AWTException, InterruptedException {

		if (platform.contains("Windows")) {
			oAuthToken = generateOauth2TokenForWindows();
		}
				
						
		else {
			oAuthToken = generateOauth2TokenForLinux();
		}

		return oAuthToken;

	}

}
