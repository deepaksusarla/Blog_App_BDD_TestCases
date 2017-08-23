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

- Added BDD and Normal testSuites. 

- BDD test Cases are in "CucumberTestSuite.xml" and Normal test Cases are in "TestSuite.xml"

# Project Structure

 - ![ScreenShot](https://raw.github.com/deepaksusarla/Blog_App_BDD_TestCases/master/ProjectStructure.png)

Note: For reply comment test scenario, waited for 15 secs. Otherwise throughing error "reply is too fast."
