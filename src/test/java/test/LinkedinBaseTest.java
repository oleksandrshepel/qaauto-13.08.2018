package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import page.*;

import java.util.concurrent.TimeUnit;

/**
 * Parent class for all tests. Here the driver instance is being created.
 * The class contains before and after methods and declares all pageObjects.
 */
public class LinkedinBaseTest {
    protected WebDriver driver;
    protected LinkedinLoginPage linkedinLoginPage;
    protected LinkedinHomePage linkedinHomePage;
    protected LinkedinLoginSubmitPage linkedinLoginSubmitPage;
    protected LinkedinSearchPage linkedinSearchPage;
    protected LinkedinPasswordResetPage linkedinPasswordResetPage;
    protected LinkedinRequestPasswordResetSubmitPage linkedinRequestPasswordResetSubmitPage;
    protected LinkedinRetypePasswordPage linkedinRetypePasswordPage;
    protected LinkedinPasswordResetSubmitPage linkedinPasswordResetSubmitPage;
    protected LinkedinLogoutPage linkedinLogoutPage;

    private String mainURL;


    /**
     * Before method to create webdriver instance and get to the landing page
     */
    @BeforeMethod
    public void beforeMethod(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mainURL = "https://www.linkedin.com/";
        driver.get(mainURL);
        linkedinLoginPage = new LinkedinLoginPage(driver);
    }

    /**
     * After method to quit webdriver
     */
    @AfterMethod
    public void afterMethod(){
        driver.quit();
    }

}
