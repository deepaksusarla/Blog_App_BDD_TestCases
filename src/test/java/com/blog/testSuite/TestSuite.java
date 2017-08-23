package com.blog.testSuite;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.blog.library.BaseWebDriver;
import com.blog.library.Utils;
import com.blog.pages.DashboardPage;
import com.blog.pages.HomePage;
import com.blog.pages.LoginPage;
import com.blog.pages.PostDetailsPage;
import com.blog.pages.PostPage;


public class TestSuite {
	WebDriver driver=null;
	LoginPage login=LoginPage.getInstance();
	HomePage homePage=HomePage.getInstance();
	DashboardPage dashBoradPage=DashboardPage.getInstance();
	PostPage postPage=PostPage.getInstance();
	PostDetailsPage postDetailsPage=PostDetailsPage.getInstance();
	String baseUrl="http://ec2-54-90-154-147.compute-1.amazonaws.com";
	int statusCode=0;
	String usrName="test_user";
	String usrPass="testuser@123";
	
	@BeforeClass
	public void setUp(){
		driver= BaseWebDriver.getDriver();
		statusCode=Utils.getHttpGetReponse(baseUrl);
		driver.get(baseUrl);
	}
	
	@AfterClass
	public void tearDown(){
		
		driver.quit();
	}
	
	@Test(enabled=true,description = "Verifying Home Page title")
	public void test1(){
		String homePageTitle=driver.getTitle();
		Assert.assertEquals(200, statusCode);
		Assert.assertEquals("user's Blog! – Just another WordPress site", homePageTitle);
	}
	
	@Test(enabled=true,description = "Verifying correct user logged in")
	public void test2(){
	    login= homePage.NavigateToLogin();
	    login.EnterLoginDetails(usrName, usrPass);
		dashBoradPage=login.SubmitLogin();
		Assert.assertEquals("test user", dashBoradPage.getLoggedInUserName());
	}
	
	@Test(enabled=true,description="Creating post and verifying unauthorized user is able to see the blog title")
	public void test3(){
	    postPage= dashBoradPage.createNewPost();
	    postPage.createPost("This is a blog by a test_user", "Test user likes to blog. The content of the blog can be anything.","anything","Text", true);
	    String postUrl=postPage.getPostLink();
	    postPage.publishPost();
	    login=dashBoradPage.logOutUser();
	    homePage= login.NavigateToHomePage();
	    postDetailsPage=homePage.NavigateToCreatedPost(postUrl);
	    String postTitle=driver.getTitle();
		Assert.assertEquals("This is a blog by a test_user – user's Blog!", postTitle);
	}
	
	@Test(enabled=true,description="Verifying postContent word 'anything' is bold or not")
	public void test4(){
		String postContent=postDetailsPage.getPostContent();
		Assert.assertEquals(postDetailsPage.getBoldText("Test user likes to blog. The content of the blog can be anything.","anything"), postContent);
	}
	
	@SuppressWarnings("unchecked")
	@Test(enabled=true,description="Verifying comment is added to the post of user 1")
	public void test5()  throws InterruptedException{
		postDetailsPage.postCommentOrReply("comment", "Comment by user 1", "User 1", "user1@typeset.io");
		Map<String,String> commentDetails=postDetailsPage.getCommentAttributes();
		Assert.assertEquals("User 1", commentDetails.get("authorName"));
		Assert.assertEquals("Comment by user 1", commentDetails.get("comment"));
	}
	
	@SuppressWarnings("unchecked")
	@Test(enabled=true,description="Verifying reply of user 2 is added to the comment of user 1")
	public void test6()  throws InterruptedException{
		Thread.sleep(15000);
		postDetailsPage.postCommentOrReply("reply", "Comment by user 2", "User 2", "user2@typeset.io");
		Map<String,String> commentDetails=postDetailsPage.getCommentAttributes();
		Assert.assertEquals("User 2", commentDetails.get("authorName"));
		Assert.assertEquals("Comment by user 2", commentDetails.get("comment"));
	}
	
}
