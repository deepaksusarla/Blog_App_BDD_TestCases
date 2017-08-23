package com.blog.pages;

import com.blog.library.ResourceType;
import com.blog.library.Locators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.blog.library.JsonReader;

public class HomePage extends Page{

	private String FileName = "HomePageObj.json";
	private JsonReader jsonReader = JsonReader.getInstance();
	
	private By login= null;
	private By homePagePosts=null;
	private By homePagePostsTitle=null;
	
	private HomePage(){
		String filePath=this.getResourcesPath(HomePage.class, ResourceType.OBJECTREPOSITORY+"/");
		jsonReader.setJsonFile(filePath+FileName);
		 login= this.getElementLocator(jsonReader.getJsonValue("Login").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("Login").get(Locators.LOCATORVALUE.toString()));
		
		 homePagePosts= this.getElementLocator(jsonReader.getJsonValue("HomePagePosts").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("HomePagePosts").get(Locators.LOCATORVALUE.toString()));
		
		 homePagePostsTitle= this.getElementLocator(jsonReader.getJsonValue("HomePagePostsTitle").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("HomePagePostsTitle").get(Locators.LOCATORVALUE.toString()));
	}
	
	private static class HomePageInstance {
		public static final HomePage INSTANCE = new HomePage();
	}

	public static HomePage getInstance() {

		return HomePageInstance.INSTANCE;
	}
	
	
	public LoginPage NavigateToLogin(){
		this.Click(login);
		return new LoginPage();
	}
	
	public Map<String,Object> checkIsPostArleadyPublished(String postTitle){
		Map<String,Object> postPublished= new HashMap<String,Object>();
		Boolean isPublished=false;
		WebElement element=this.getElement(homePagePosts);
		List<WebElement> elements=this.getElementsByWebElement(element, homePagePostsTitle);
		for(int i=0;i<elements.size();i++){
			String text=elements.get(i).findElement(By.tagName("a")).getText();
			if(text.equals(postTitle)){
				isPublished=true;
				String hrefUrl=this.getAttributeValueByTagNameWebElement(elements.get(i), "a", "href");
				postPublished.put("isPublished",isPublished);
				postPublished.put("hrefUrl",hrefUrl);
				break;
			}
		}
		return postPublished;
	}
	
	public PostDetailsPage NavigateToCreatedPost(String createdPostUrl){
		WebElement element=this.getElement(homePagePosts);
		List<WebElement> elements=this.getElementsByWebElement(element, homePagePostsTitle);
		for(int i=0;i<elements.size();i++){
			String hrefUrl=this.getAttributeValueByTagNameWebElement(elements.get(i), "a", "href");
			if(hrefUrl.equals(createdPostUrl)){
				driver.navigate().to(createdPostUrl);
				break;
			}
		}
		return new PostDetailsPage();
	}
}
