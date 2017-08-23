Feature: Blog application testing

Scenario: Blog publish Tests
Given a user is logged in "test_user" and "testuser@123"
When user adds a blog with Title "This is a blog by a test_user" and Content "Test user likes to blog. The content of the blog can be anything." and bold "anything" 
Then unauthenticated users should be able to see the blog title on the blog index page.
Then viewing the blog should show the "anything" in bold for Content "Test user likes to blog. The content of the blog can be anything."