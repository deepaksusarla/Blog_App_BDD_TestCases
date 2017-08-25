package com.blog.library;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


/*
 * This class holds all the generic methods used by different Pages.
 */
public class Utils {
	public static WebDriver driver = null;
	public static JavascriptExecutor js=null;
	static HttpClient client = HttpClientBuilder.create().build();

	/**
	 * @param LocatorType
	 * @param LocatorValue
	 * @return By
	 */
	public By getElementLocator(Object LocatorType, Object LocatorValue) {

		By locator = null;
		if (LocatorType.toString().equalsIgnoreCase("id"))
			locator = By.id(LocatorValue.toString());

		if (LocatorType.toString().equalsIgnoreCase("name"))
			locator = By.name(LocatorValue.toString());

		if (LocatorType.toString().equalsIgnoreCase("className"))
			locator = By.className(LocatorValue.toString());

		if (LocatorType.toString().equalsIgnoreCase("tagName"))
			locator = By.tagName(LocatorValue.toString());

		if (LocatorType.toString().equalsIgnoreCase("xpath"))
			locator = By.xpath(LocatorValue.toString());

		if (LocatorType.toString().equalsIgnoreCase("css"))
			locator = By.cssSelector(LocatorValue.toString());

		if (LocatorType.toString().equalsIgnoreCase("linkText"))
			locator = By.linkText(LocatorValue.toString());

		if (LocatorType.toString().equalsIgnoreCase("partialLinkText"))
			locator = By.partialLinkText(LocatorValue.toString());

		return locator;
	}
	
	
	
	public boolean isElementPresent(final By selector) {

		boolean returnVal = true;
		WebElement element =null;
		WebDriverWait wait = new WebDriverWait(driver, 30);
		try {
			element=wait.until(new ExpectedCondition<WebElement>() {
				
				        public WebElement apply(WebDriver webDriver) {
				
				            System.out.println("Waiting for "+selector+" to be found");
				
				            return webDriver.findElement(selector);
				            
				        }
				
				    });
			highlightElement(element);
			String desc="Element : " + selector + " found";
			System.out.println("Wait"+ desc+ " passed");
			
		} catch (Exception e) {
			returnVal = false;
			e.printStackTrace();
		}

		return returnVal;

	}
	
	/**
	 * @param element
	 */
	public void highlightElement(WebElement element) {
		try {
			js.executeScript("arguments[0].style.border='2px solid green'", element);
			Thread.sleep(500);
			unhighlightElement(element);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param element
	 */
	public void unhighlightElement(WebElement element) {
		js.executeScript("arguments[0].style.border=''", element);

	}

	/**
	 * @param url
	 */
	public void openUrl(String url,String appType) {
		
		driver.get(url);
	}
	
	/**
	 * @param locator
	 * @return WebElement
	 */
	public WebElement getElement(By locator) {
		
		return driver.findElement(locator);
	}
	
	/**
	 * @param WebElement
	 * @param locator
	 * @return List
	 */
	public List<WebElement> getElementsByWebElement(WebElement element,By locator) {
		
		return element.findElements(locator);
	}
	
	/**
	 * @param locator
	 * @return void
	 */
	public void Click(By locator) {
	try{
		if(isElementPresent(locator)){
			driver.findElement(locator).click();
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
		
	}
	
	
	/**
	 * @param locator
	 * @return void
	 */
	public void JSclick(By locator) {
	try{
		if(isElementPresent(locator)){
			js.executeScript("arguments[0].click();", driver.findElement(locator));
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
		
	}
	
	/**
	 * @param locator
	 * @param value
	 * @return void
	 */
	public void Type(By locator,String value) {
		try{
			if(isElementPresent(locator)){
				driver.findElement(locator).sendKeys(value);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param locator
	 * @return void
	 */
	public void Clear(By locator) {
		try{
			if(isElementPresent(locator)){
				driver.findElement(locator).clear();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param locator
	 * @param tagName
	 * @param attributeValue
	 * @return string
	 */
	public String getAttributeValueByTagName(By locator,String tagName,String attributeValue) {
		String attrValue=null;
		try{
			if(isElementPresent(locator)){
				attrValue= driver.findElement(locator).findElement(By.tagName(tagName)).getAttribute(attributeValue);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return attrValue;
	}
	
	/**
	 * @param WebElement
	 * @param tagName
	 * @param attributeValue
	 * @return string
	 */
	public String getAttributeValueByTagNameWebElement(WebElement element,String tagName,String attributeValue) {
		return element.findElement(By.tagName(tagName)).getAttribute(attributeValue);
	}


	/**
	 * @param locator
	 * @return void
	 */
	public void mouseHover(By locator) {
		Actions action = new Actions(driver);
		try {
			if(isElementPresent(locator)){
				action.moveToElement(driver.findElement(locator)).build().perform();
				Thread.sleep(1000);
			}
		
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * @param locator
	 * @return void
	 */
	public void mouseHoverJs(By locator) {
		try {
			if(isElementPresent(locator)){
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
										((JavascriptExecutor) driver).executeScript(mouseOverScript,
												driver.findElement(locator));
				Thread.sleep(1000);
			}
		
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
	 * @param locator
	 * @return String
	 */
	public String getText(By locator) {
		String text=null;
		try{
			if(isElementPresent(locator)){
				text= driver.findElement(locator).getText();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * @param locator
	 * @param tagName
	 * @return string
	 */
	public String getTextByTagName(By locator,String tagName) {
		String text=null;
		try{
			if(isElementPresent(locator)){
				text=  driver.findElement(locator).findElement(By.tagName(tagName)).getText();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return text;
		
	}
	
	
	/**
	 * @param className
	 * @param path
	 * @return String
	 */
	public String getResourcesPath(Class<?> className,String path){
		
		String resource_path=null;
		try {
			
			URL url = className.getClassLoader().getResource(path);
			resource_path=url.toURI().getPath();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resource_path;
	}
	
	/**
	 * @param strValue
	 * @return String
	 */
	public String stringReplace(String strValue){
		return strValue.replaceAll("[\\t\\n\\r]+",",");
	}
	
	/**
	 * @param strValue
	 * @return String
	 */
	public String getBoldText(String strValue,String tobeBoldValue){
		String text = strValue;
		int start=0;
		int end=0;
	    String wordToFind = tobeBoldValue;
	    Pattern word = Pattern.compile(wordToFind);
	    Matcher match = word.matcher(text);

	    while (match.find()) {
	    	start=match.start();
	    	end=(match.end());
	    }
	    String text1=text.substring(0, start);
	    String text2=text1+"<strong>";
	    text1=text.substring(start,end);
	   text2=text2+text1+"</strong>"+text.substring(end);
	    return text2;
	}
	

	/**
	 * @param locator
	 * @param tagName
	 * @return int
	 */
	public int getCountByTagName(By locator,String tagName){
		List<WebElement> elements=driver.findElement(locator).findElements(By.tagName(tagName));
		return elements.size();
	}
	
	/**
	 * @param string
	 * @return httpStatusCode
	 */
	public static int getHttpGetReponse(String url){
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response.getStatusLine().getStatusCode();
	}
		
}
