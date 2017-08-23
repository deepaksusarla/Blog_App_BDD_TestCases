package com.blog.stepDefintions;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.blog.library.BaseWebDriver;
import com.blog.library.Utils;
import com.blog.pages.DashboardPage;
import com.blog.pages.HomePage;
import com.blog.pages.LoginPage;
import com.blog.pages.PostDetailsPage;
import com.blog.pages.PostPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BlogTestSuite {
	
	WebDriver driver= Utils.driver;
	LoginPage login=LoginPage.getInstance();
	HomePage homePage=HomePage.getInstance();
	DashboardPage dashBoradPage=DashboardPage.getInstance();
	PostPage postPage=PostPage.getInstance();
	PostDetailsPage postDetailsPage=PostDetailsPage.getInstance();
	String postUrl=null;
	String commentUsr=null;
	String commentContent=null;
	int statusCode=0;
	
	
	@Given("^user opens blog home page \"([^\"]*)\"$")
	public void user_opens_blog_home_page(String baseUrl){
		statusCode=Utils.getHttpGetReponse(baseUrl);
		driver.get(baseUrl);
	}
	
	@Then("^verify the home page title$")
	public void verify_the_home_page_title(){
		String homePageTitle=driver.getTitle();
		Assert.assertEquals(200, statusCode);
		Assert.assertEquals("user's Blog! – Just another WordPress site", homePageTitle);
	}
	
	/*
	 * Publish tests
	 */
	
	@Given("^a user is logged in \"([^\"]*)\" and \"([^\"]*)\"$")
	public void a_user_is_logged_in_and(String usrName, String usrPass){
		
		login= homePage.NavigateToLogin();
		login.EnterLoginDetails(usrName, usrPass);
		dashBoradPage=login.SubmitLogin();
	}
	
	@When("^user adds a blog with Title \"([^\"]*)\" and Content \"([^\"]*)\" and bold \"([^\"]*)\"$")
	public void user_adds_a_blog_with_Title_and_Content(String title, String content,String arg3) {
		postPage= dashBoradPage.createNewPost();
	    postPage.createPost(title, content,arg3,"Text", true);
	    postUrl=postPage.getPostLink();
	    postPage.publishPost();
	    login=dashBoradPage.logOutUser();
	}
	
	@Then("^unauthenticated users should be able to see the blog title on the blog index page\\.$")
	public void unauthenticated_users_should_be_able_to_see_the_blog_title_on_the_blog_index_page() {
		homePage= login.NavigateToHomePage();
	    postDetailsPage=homePage.NavigateToCreatedPost(postUrl);
	    String postTitle=driver.getTitle();
		Assert.assertEquals("This is a blog by a test_user – user's Blog!", postTitle);
	}
	
	@Then("^viewing the blog should show the \"([^\"]*)\" in bold for Content \"([^\"]*)\"$")
	public void viewing_the_blog_should_show_the_in_bold_for_Content(String arg1, String arg2){
		String postContent=postDetailsPage.getPostContent();
		Assert.assertEquals(postDetailsPage.getBoldText(arg2,arg1), postContent);
	}
	
	@Given("^a user is not logged in$")
	public void a_user_is_not_logged_in() {
		postDetailsPage.navigateToHomePage();
	}
	
	@When("^a blog with Title \"([^\"]*)\" Content \"([^\"]*)\" Is already published$")
	public void a_blog_with_Title_Content_Is_already_published(String postTitle, String arg2) {
		Map<String,Object> postPublished=homePage.checkIsPostArleadyPublished(postTitle);
	    Assert.assertEquals(true, postPublished.get("isPublished"));
	    postDetailsPage=homePage.NavigateToCreatedPost(postPublished.get("hrefUrl").toString());
	}
	
	@And("^they add a comment with Name: \"([^\"]*)\" Email: \"([^\"]*)\" Comment: \"([^\"]*)\"$")
	public void they_add_a_comment_with_Name_Email_Comment(String arg1, String arg2, String arg3)  {
		postDetailsPage.postCommentOrReply("comment", arg3, arg1, arg2);
		commentUsr=arg1;
		commentContent=arg3;
	}
	
	@Then("^viewing the blog should show the comment with correct attributes\\.$")
	public void viewing_the_blog_should_show_the_comment_with_correct_attributes()  {
		Map<String,String> commentDetails=postDetailsPage.getCommentAttributes();
		Assert.assertEquals(commentUsr, commentDetails.get("authorName"));
		Assert.assertEquals(commentContent, commentDetails.get("comment"));
	}
	
	@And("^a comment with Name: \"([^\"]*)\" Email: \"([^\"]*)\" Comment: \"([^\"]*)\" Already exists$")
	public void a_comment_with_Name_Email_Comment_Already_exists(String arg1, String arg2, String arg3) throws Throwable {
		Map<String,String> commentDetails=postDetailsPage.getCommentAttributes();
		Assert.assertEquals(arg1, commentDetails.get("authorName"));
		Assert.assertEquals(arg3, commentDetails.get("comment"));
	}
	
	@And("^they add a comment with Name: \"([^\"]*)\" Email: \"([^\"]*)\" Comment: \"([^\"]*)\" As a reply to the existing comment by \"([^\"]*)\",$")
	public void they_add_a_comment_with_Name_Email_Comment_As_a_reply_to_the_existing_comment_by(String arg1, String arg2, String arg3, String arg4) throws InterruptedException  {
		Thread.sleep(15000);
		postDetailsPage.postCommentOrReply("reply", "Comment by user 2", "User 2", "user2@typeset.io");
		commentUsr=arg1;
		commentContent=arg3;
	}
	
	@Then("^viewing the blog should show the reply-comment with correct attributes\\.$")
	public void viewing_the_blog_should_show_the_reply_comment_with_correct_attributes() throws Throwable {
		Map<String,String> commentDetails=postDetailsPage.getCommentAttributes();
		Assert.assertEquals("User 2", commentDetails.get("authorName"));
		Assert.assertEquals("Comment by user 2", commentDetails.get("comment"));
	}
}
