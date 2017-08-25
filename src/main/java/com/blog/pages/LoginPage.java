package com.blog.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.blog.library.JsonReader;
import com.blog.library.Locators;
import com.blog.library.ResourceType;

public class LoginPage extends Page{
	
	private String FileName = "LoginPageObj.json";
	private JsonReader jsonReader = JsonReader.getInstance();
	String filePath=this.getResourcesPath(LoginPage.class, ResourceType.OBJECTREPOSITORY+"/");
	private By usrName= null;
	private By usrPass=null;
	private By submitLogin=null;
	private By backToBlog=null;
	
	public LoginPage(){
		jsonReader.setJsonFile(filePath+FileName);
		usrName= this.getElementLocator(jsonReader.getJsonValue("UserName").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("UserName").get(Locators.LOCATORVALUE.toString()));
		
		usrPass= this.getElementLocator(jsonReader.getJsonValue("UserPass").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("UserPass").get(Locators.LOCATORVALUE.toString()));
		
		submitLogin= this.getElementLocator(jsonReader.getJsonValue("LoginSubmit").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("LoginSubmit").get(Locators.LOCATORVALUE.toString()));
		
		backToBlog= this.getElementLocator(jsonReader.getJsonValue("BackToBlog").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("BackToBlog").get(Locators.LOCATORVALUE.toString()));
	}
	
	private static class LoginPageInstance {
		public static final LoginPage INSTANCE = new LoginPage();
	}

	public static LoginPage getInstance() {

		return LoginPageInstance.INSTANCE;
	}
	
	public void EnterLoginDetails(String userName,String userPass){
		this.Type(usrName, userName);
		this.Type(usrPass, userPass);
	}
	
	public DashboardPage SubmitLogin(){
		this.Click(submitLogin);
		return new DashboardPage();
	}
	
	public HomePage NavigateToHomePage(){
		this.Click(backToBlog);
		return HomePage.getInstance();
	}

}
