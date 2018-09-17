package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
     Base method for waiting  webelement visibility
     * @param webElement
     * @param timeOutInSeconds
     * @return - webelement
     */
    protected WebElement waitUntilElementVisible(WebElement webElement, int timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.visibilityOf(webElement));
    }
}
