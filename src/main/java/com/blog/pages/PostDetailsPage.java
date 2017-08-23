package com.blog.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.blog.library.JsonReader;
import com.blog.library.Locators;
import com.blog.library.ResourceType;

public class PostDetailsPage extends Page{
	
	private String FileName = "PostDetailsPageObj.json";
	private JsonReader jsonReader = JsonReader.getInstance();
	String filePath=this.getResourcesPath(PostDetailsPage.class, ResourceType.OBJECTREPOSITORY+"/");
	private By postContent=null;
	private By postComment= null;
	private By commentAuthorName=null;
	private By authorEmail=null;
	private By commentSubmit=null;
	private By replyComment=null;
	private By commentsList=null;
	private By commentedAuthorName=null;
	private By commentContent=null;
	private By siteTitle=null;
	
	private Map<String,String> commentDetails= new HashMap<String,String>();
	
	public PostDetailsPage(){
		jsonReader.setJsonFile(filePath+FileName);
		
		siteTitle= this.getElementLocator(jsonReader.getJsonValue("SiteTitle").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("SiteTitle").get(Locators.LOCATORVALUE.toString()));
		
		postContent= this.getElementLocator(jsonReader.getJsonValue("PostContent").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("PostContent").get(Locators.LOCATORVALUE.toString()));
		
		postComment= this.getElementLocator(jsonReader.getJsonValue("PostComment").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("PostComment").get(Locators.LOCATORVALUE.toString()));
		
		commentAuthorName= this.getElementLocator(jsonReader.getJsonValue("CommentAuthorName").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("CommentAuthorName").get(Locators.LOCATORVALUE.toString()));
		
		authorEmail= this.getElementLocator(jsonReader.getJsonValue("AuthorEmail").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("AuthorEmail").get(Locators.LOCATORVALUE.toString()));
		
		commentSubmit= this.getElementLocator(jsonReader.getJsonValue("CommentSubmit").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("CommentSubmit").get(Locators.LOCATORVALUE.toString()));
		
		replyComment= this.getElementLocator(jsonReader.getJsonValue("ReplyComment").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("ReplyComment").get(Locators.LOCATORVALUE.toString()));
		
		commentsList= this.getElementLocator(jsonReader.getJsonValue("CommentsList").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("CommentsList").get(Locators.LOCATORVALUE.toString()));
		
		commentedAuthorName= this.getElementLocator(jsonReader.getJsonValue("CommentedAuthorName").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("CommentedAuthorName").get(Locators.LOCATORVALUE.toString()));
		
		commentContent= this.getElementLocator(jsonReader.getJsonValue("CommentContent").get(Locators.LOCATOR.toString()),
				jsonReader.getJsonValue("CommentContent").get(Locators.LOCATORVALUE.toString()));
	}
	
	private static class PostDetailsPageInstance {
		public static final PostDetailsPage INSTANCE = new PostDetailsPage();
	}

	public static PostDetailsPage getInstance() {

		return PostDetailsPageInstance.INSTANCE;
	}
	
	public void navigateToHomePage(){
		this.Click(siteTitle);
	}
	public String getPostContent(){
	return this.getAttributeValueByTagName(postContent,"p", "innerHTML");
	}
	
	public void postCommentOrReply(String value,String comment,String authorName,String authorEmailId){
		if(value.equals("reply")){
			this.Click(replyComment);
			createComment(comment,authorName,authorEmailId);
		}else{
			createComment(comment,authorName,authorEmailId);
		}
	}
	
	private void createComment(String comment,String authorName,String authorEmailId){
		this.Clear(postComment);
		this.Type(postComment, comment);
		this.Clear(commentAuthorName);
		this.Type(commentAuthorName, authorName);
		this.Clear(authorEmail);
		this.Type(authorEmail, authorEmailId);
		this.Click(commentSubmit);
	}
	
	public Map<String,String> getCommentAttributes(){
		WebElement element= this.getElement(commentsList);
		List<WebElement> elements= this.getElementsByWebElement(element, By.tagName("li"));
		for(int i=0;i<elements.size();i++){
			String authorName=elements.get(i).findElement(commentedAuthorName).getText();
			if(commentDetails.get(authorName)==null){
				commentDetails.put("authorName", authorName);
				String comment=elements.get(i).findElement(commentContent).getText();
				commentDetails.put("comment", comment);
			}
			
		}
		return commentDetails;
	}

}
