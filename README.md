*** JDK installing on Windows ***
Step 1. Download JDK
Go to the link https://www.oracle.com/technetwork/java/javase/downloads/index.html. 
Accept the license agreement
Download the latest version of Java JDK for your OS.

Step 2. Install JDK and JRE
After the download is complete, run exe to install the JDK. Click Next.
When the installation is complete, click Close.
By default:

JDK is installed in directory "C:\Program Files\Java\jdk-10.0.{x}", where {x} denotes the upgrade number; and
JRE is installed in "C:\Program Files\Java\jre-10.0.{x}".
Notes: In 64-bit Windows, "Program Files" is meant for 64-bit programs; while "Program Files (x86)" for 32-bit programs.

Accept the defaults and follow the screen instructions to install JDK and JRE.

Step 3. Include JDK's "bin" Directory in the PATH
Windows' Shell searches the current directory and the directories listed in the PATH environment variable (system variable) for executable programs. JDK's programs (such as Java compiler javac.exe and Java runtime java.exe) reside in the sub-directory "bin" of the JDK installed directory. You need to include "bin" in the PATH to run the JDK programs.

To edit the PATH environment variable in Windows 7/8/10:

Launch "Control Panel" ⇒ (Optional) System and Security ⇒ System ⇒ Click "Advanced system settings" on the left pane.
Switch to "Advanced" tab ⇒ Push "Environment Variables" button.
Under "System Variables" (the bottom pane), scroll down to select "Path" ⇒ Click "Edit...".
For Windows 10 (newer releases):
You shall see a TABLE listing all the existing PATH entries (if not, goto next step). Click "New" ⇒ Enter the JDK's "bin" directory "c:\Program Files\Java\jdk-10.0.{x}\bin" (Replace {x} with your installation number!!!) ⇒ Select "Move Up" to move this entry all the way to the TOP.

Step 4: Verify the JDK Installation
Launch a CMD shell via one of the following means:

Click "Search" button ⇒ Enter "cmd" ⇒ Choose "Command Prompt", or
right-click "Start" button ⇒ run... ⇒ enter "cmd", or
(Prior to Windows 10) click "Start" button ⇒ All Programs ⇒ Accessories (or Windows System) ⇒ Command Prompt, or
(Windows 10) click "Start" button ⇒ Windows System ⇒ Command Prompt.
Issue the following commands to verify your JDK installation:

Issue "path" command to list the contents of the PATH environment variable. Check to make sure that your <JAVA_HOME>\bin is listed in the PATH.

// Display the PATH entries
prompt> path
PATH=c:\Program Files\Java\jdk-10.0.{x}\bin;[other entries...]
Issue the following commands to verify that JDK/JRE are properly installed and display their version:

// Display the JRE version
prompt> java -version
java version "10.0.{x}" 2018-07-17
Java(TM) SE Runtime Environment 18.3 (build 10.0.1+10)
Java HotSpot(TM) 64-Bit Server VM 18.3 (build 10.0.1+10, mixed mode)

// Display the JDK version
prompt> javac -version
javac 10.0.{x}

*** Installing IntelliJ IDEA on Windows ***
Step 1. Download IDEA
Go to the link https://www.jetbrains.com/idea/download
Choose the Windows tab (defaulted) and click the “Download Community” link
Step 2. Install IDEA
Open the .exe and choose next
Choose default install location and menu
Choose to associate .java files (unless you don’t want to, this will make a double click of a .java file open in IDEA)
Choose the run checkbox, so we can finish installation
Leave the default chosen for importing (again unless you do have files)
Choose “Skip Remaining and Set Defaults”
Finally if you get warned about Firewall access. Choose allow (unless you are feeling paranoid).

*** Creating a project ***
Step 1. 
Run IDEA
Step 2. 
If no project is currently open in IntelliJ IDEA, click Create New Project on the Welcome screen. Otherwise, select File | New | Project.
As a result, the New Project wizard opens.
Step 3.
In the left-hand pane, select Maven or Gradle. (We want a Maven-enabled project to be created, or, to be more exact, a project with a Maven module.)
Step 4.
Specify the JDK that you want to use in your project (the Project SDK field). Do one of the following:
Select the JDK from the list.
If the desired JDK is already available on your computer but is missing from the list, click New and, in the dialog that opens, select the JDK installation directory.
Click Download JDK. Click Next.
Step 5.
Enter GroupID, ArtifactId and Version (by default 1.0-SNAPSHOT)
Click Next
Step 6.
Enter Project name and set Project location (recommended C:\Users\'User'\Projects\'Project name'
Click Finish

*** Installing Chrome browser 3.1. ***
Step 1.
Find in google.com «Crome browser».
Step 2.
Choose last version installer.
Step 3.
Download installer
Step 4.
Run installer

*** Installing ChromeDriver 4.1. ***
Step 1.
Find in google.com «ChromeDriver» or download installer ChromeDriver from: https://sites.google.com/a/chromium.org/chromedriver/downloads.
Step 2.
Choose last version file.
Step 3.
Extract the content from downloaded file to your directory 4.3.	Include ChromeDriver into PATH
Launch button “Start”. Click right mouse button on “Computer”. Select “Properties” On left side of screen “System” click “Advanced system settings”
Switch to tab “Advanced” and click “Environment Variables”
In “System variables” select “Path” and push button “Edit”
Add the ChromeDriver location directory location to Path

*** Installing Selenium WD 5.1. ***
Step 1.
Find in google.com «Selenium webdriver» or download installer Selenium webdriver from: https://www.seleniumhq.org/download/.
Step 2.
Choose last version file.
Step 3.
Extract the content from downloaded file to your directory

*** Running tests with Maven ***
enter in commandline $ mvn clean test -DsuiteXmlFile=login-test.xml

