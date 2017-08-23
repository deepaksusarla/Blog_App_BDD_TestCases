package com.blog.pages;

import org.openqa.selenium.By;

import com.blog.library.JsonReader;
import com.blog.library.Locators;
import com.blog.library.ResourceType;

public class DashboardPage extends Page{
	
	private String FileName = "DashBoardPageObj.json";
	private JsonReader jsonReader = JsonReader.getInstance();
	String filePath=this.getResourcesPath(DashboardPage.class, ResourceType.OBJECTREPOSITORY+"/");
	private By newPostOrMedia= null;
	private By newPost=null;
	private By userProfile=null;
	private By userLogOut=null;
	
	public DashboardPage(){
		
		jsonReader.setJsonFile(filePath+FileName);
		newPostOrMedia= this.getElementLocator(jsonReader.getJsonValue("NewPostOrMedia").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("NewPostOrMedia").get(Locators.LOCATORVALUE.toString()));
		
		newPost= this.getElementLocator(jsonReader.getJsonValue("NewPost").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("NewPost").get(Locators.LOCATORVALUE.toString()));
		
		userProfile= this.getElementLocator(jsonReader.getJsonValue("UserProfile").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("UserProfile").get(Locators.LOCATORVALUE.toString()));
		
		userLogOut= this.getElementLocator(jsonReader.getJsonValue("UserLogOut").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("UserLogOut").get(Locators.LOCATORVALUE.toString()));
	}
	
	private static class DashboardPageInstance {
		public static final DashboardPage INSTANCE = new DashboardPage();
	}

	public static DashboardPage getInstance() {

		return DashboardPageInstance.INSTANCE;
	}
	
	public String getLoggedInUserName(){
		return this.getText(userProfile);
	}
	
	public PostPage createNewPost(){
		this.mouseOver(newPostOrMedia);
		this.Click(newPost);
		return PostPage.getInstance();
	}
	
	public LoginPage logOutUser(){
		this.mouseOver(userProfile);
		this.Click(userLogOut);
		return LoginPage.getInstance();
	}

}
