Feature: Blog application Comments testing for post

Scenario: Add Comment to Post
Given a user is not logged in
When a blog with Title "This is a blog by a test_user" Content "Test user likes to blog. The content of the blog can be anything." Is already published
And they add a comment with Name: "User 1" Email: "user1@typeset.io" Comment: "Comment by user 1"
Then viewing the blog should show the comment with correct attributes.

Scenario: Reply to Comment
Given a user is not logged in
When a blog with Title "This is a blog by a test_user" Content "Test user likes to blog. The content of the blog can be anything." Is already published
And a comment with Name: "User 1" Email: "user1@typeset.io" Comment: "Comment by user 1" Already exists
And they add a comment with Name: "User 2" Email: "user2@typeset.io" Comment: "Comment by user 2" As a reply to the existing comment by "User 1",
Then viewing the blog should show the reply-comment with correct attributes.