package com.blog.stepDefintions;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.blog.library.BaseWebDriver;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features="src/test/resources/features/")
public class TestRunner extends AbstractTestNGCucumberTests{
	WebDriver driver= null;	
	@BeforeClass 
	public void setUp(){ 
		driver= BaseWebDriver.getDriver(); 
	   } 
	
	@AfterClass 
	public void cleanUp(){ 
	      driver.quit(); 
	   } 

}
