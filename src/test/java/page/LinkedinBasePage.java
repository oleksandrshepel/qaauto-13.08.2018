package page;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import util.GMailService;

/**
 * Linkedin parent object class
 *
 * Contains declaration of WebDriver instance, WebDriverWait instance and create gMailService instance
 */
public class LinkedinBasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static GMailService gMailService = new GMailService();

    /**
     * Common method to get current url of an appropriate pageobject class
     * @return - current url
     */
    protected String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    /**
     * Common method to get current title of an appropriate pageobject class
     * @return - current title
     */
    protected String getCurrentTitle(){
        return driver.getTitle();
    }

    /**
     * Base method for waiting  webelement visibility
     * @param webElement
     * @param timeOutInSeconds
     * @return - webelement
     */
    protected WebElement waitUntilElementVisible(WebElement webElement, int timeOutInSeconds){
       wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Returns boolean depending the fact whether url contains or not partial text
     * @param partialUrl - a char sequence of
     * @param timeOutInSec - timeout parameter in seconds
     * @return - boolean
     */
    protected boolean isUrlContains(String partialUrl, int timeOutInSec){
        wait = new WebDriverWait(driver, timeOutInSec);
        try{
            return wait.until(ExpectedConditions.urlContains(partialUrl));
        } catch(TimeoutException e){
            return false;//Программистам нельзя "проглатывать" exception, нам же можно, поскольку метод должен возвращать true/false
        }
    }

    protected void assertWebElementIsVisible(WebElement element, int timeOutInSec){
        try {
            waitUntilElementVisible(element, timeOutInSec);
        } catch(TimeoutException e){
            //выброс assert-а можно оставить в пейдже, но сами asserts (assertTrue,assertEquals etc.) нужно писать исключительно в тестах
            throw new AssertionError("Linkedin page is not loaded");
        }
    }
}
