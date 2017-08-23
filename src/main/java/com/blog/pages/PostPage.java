package com.blog.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.blog.library.JsonReader;
import com.blog.library.Locators;
import com.blog.library.ResourceType;

public class PostPage extends Page{
	
	private String FileName = "PostPageObj.json";
	private JsonReader jsonReader = JsonReader.getInstance();
	String filePath=this.getResourcesPath(PostPage.class, ResourceType.OBJECTREPOSITORY+"/");
	private By postTitle= null;
	private By visualTab=null;
	private By textTab=null;
	private By textTabArea =null;
	private By textTabAreaTextBold=null;
	private By postContentFrame=null;
	private By postContent=null;
	private By editorToolBarBoldText=null;
	private By generatedPostLink=null;
	private By publishPost=null;
	
	
	public PostPage(){
		
		jsonReader.setJsonFile(filePath+FileName);
		postTitle= this.getElementLocator(jsonReader.getJsonValue("PostTitle").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("PostTitle").get(Locators.LOCATORVALUE.toString()));
		
		visualTab= this.getElementLocator(jsonReader.getJsonValue("VisualTab").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("VisualTab").get(Locators.LOCATORVALUE.toString()));
		
		textTab= this.getElementLocator(jsonReader.getJsonValue("TextTab").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("TextTab").get(Locators.LOCATORVALUE.toString()));
		
		textTabArea= this.getElementLocator(jsonReader.getJsonValue("TextTabArea").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("TextTabArea").get(Locators.LOCATORVALUE.toString()));
		
		textTabAreaTextBold= this.getElementLocator(jsonReader.getJsonValue("TextTabAreaTextBold").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("TextTabAreaTextBold").get(Locators.LOCATORVALUE.toString()));
		
		postContentFrame= this.getElementLocator(jsonReader.getJsonValue("PostContentFrame").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("PostContentFrame").get(Locators.LOCATORVALUE.toString()));
		
		postContent= this.getElementLocator(jsonReader.getJsonValue("PostContent").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("PostContent").get(Locators.LOCATORVALUE.toString()));
		
		editorToolBarBoldText= this.getElementLocator(jsonReader.getJsonValue("EditorToolBarBoldText").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("EditorToolBarBoldText").get(Locators.LOCATORVALUE.toString()));
		
		generatedPostLink= this.getElementLocator(jsonReader.getJsonValue("GeneratedPostLink").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("GeneratedPostLink").get(Locators.LOCATORVALUE.toString()));
		
		publishPost= this.getElementLocator(jsonReader.getJsonValue("PublishPost").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("PublishPost").get(Locators.LOCATORVALUE.toString()));
	}
	
	private static class PostPageInstance {
		public static final PostPage INSTANCE = new PostPage();
	}

	public static PostPage getInstance() {

		return PostPageInstance.INSTANCE;
	}
	
	public void createPost(String postTitleValue,String postContentValue,String wordToBeBold,String postTabValue,boolean isBold){
	
		this.Type(postTitle, postTitleValue);
		
		if(postTabValue.equals("Visual")){
			this.Click(visualTab);
			driver.switchTo().frame(this.getElement(postContentFrame));
			this.Type(postContent, postContentValue);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(isBold){
				
				String selectAll = Keys.chord(Keys.ALT, Keys.SHIFT,Keys.ARROW_LEFT);
				this.Type(postContent, selectAll);
				driver.switchTo().defaultContent();
				this.Click(editorToolBarBoldText);
				
			}else{
				driver.switchTo().defaultContent();
			}
		}
		if(postTabValue.equals("Text")){
			if(isBold){
				this.Type(textTabArea, this.getBoldText(postContentValue,wordToBeBold));
			}else{
				this.Type(textTabArea, postContentValue);
			}
		}
		
	}
	
	public String getPostLink(){
		return this.getText(generatedPostLink);
	}
	
	public void publishPost(){
		this.Click(publishPost);
	}

}
