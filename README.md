Blog_App_BDD_TestScripts
===

- Setup SSH Keys: https://help.github.com/articles/generating-ssh-keys/

- Clone our repository : `git@github.com:deepaksusarla/Blog_App_BDD_TestCases.git`

# Basic Setup

### Install java and maven

- Java Download: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

- Maven Download: https://maven.apache.org/download.cgi

Please follow the steps provided in below url 

http://www.baeldung.com/install-maven-on-windows-linux-mac

# Running Automation Tests

    cd Blog_App_BDD_TestCases
    mvn clean install -DskipTests
    
    mvn test -DsuiteXmlFile=CucumberTestSuite.xml
    
# Adding/Modifying Automation Tests

  - Cucumber feature files are at "src/test/resources/features/" location. From here feature files can added or modified
  
  - Cucumber step defintions are at "src/test/java/" location. Files are in "com.blog.stepDefintions". From here stepDefinitions can added or modified.
  
  For better clarity please look into below project structure image.

- Added BDD and Normal testSuites. 

- BDD test Cases are in "CucumberTestSuite.xml" and Normal test Cases are in "TestSuite.xml"

# Project Structure

 - ![ScreenShot](https://raw.github.com/deepaksusarla/Blog_App_BDD_TestCases/master/ProjectStructure.png)

Note: For reply comment test scenario, waited for 15 secs. Otherwise throughing error "reply is too fast."
