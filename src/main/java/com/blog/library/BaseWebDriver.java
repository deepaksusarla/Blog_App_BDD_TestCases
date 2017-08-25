package com.blog.library;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
/*
 * This class is for providing base web driver instance
 */
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class BaseWebDriver {
	private static WebDriver driver = null;
	private static final String drivers_path="src/main/resources/Drivers/";
	
	
	/*
	 * This method returns driver object
	 */
	public static WebDriver getDriver(){
		String os=System.getProperty("os.name");
		if(os.startsWith("Mac"))
			System.setProperty("webdriver.chrome.driver", drivers_path+"chromedriver");
		if(os.startsWith("Windows"))
			System.setProperty("webdriver.chrome.driver", drivers_path+"chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		driver= new ChromeDriver(options);
	driver.manage().window().maximize();
	Utils.driver=driver;
	Utils.js=(JavascriptExecutor) driver;
	//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	return driver;
	}

}
