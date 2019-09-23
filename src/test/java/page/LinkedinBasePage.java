package page;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.GMailService;
import util.Parameters;
import util.ServicesFunctions;

import java.util.concurrent.TimeUnit;

/**
 * Linkedin parent object class
 *
 * Contains declaration of WebDriver instance, WebDriverWait instance and create gMailService instance
 */
public class LinkedinBasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static Logger LOG;
    protected static GMailService gMailService = new GMailService();
    private int ATTEMPTS = 5;

    public LinkedinBasePage (){
        this.LOG = Logger.getLogger(LinkedinBasePage.class);
    }

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
        WebDriverWait wait = new WebDriverWait(driver, Parameters.timeoutWaitDialogs);
        return wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Returns boolean depending the fact whether url contains or not partial text
     * @param partialUrl - a char sequence of
     * @param timeOutInSec - timeout parameter in seconds
     * @return - boolean
     */

    protected boolean isUrlContains(String partialUrl, int timeOutInSec){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSec);
        try{
            return wait.until(ExpectedConditions.urlContains(partialUrl));
        } catch(TimeoutException e){
            return false;//Программистам нельзя "проглатывать" exception, нам же можно, поскольку метод должен возвращать true/false
        }
    }

    /**
     * Asserts whether webelement is visible or not
     *
     * @param element - a webelement that is expected to be visible
     * @param timeOutInSec - a time period in seconds
     */

    protected void assertWebElementIsVisible(WebElement element, int timeOutInSec){
        try {
            waitUntilElementVisible(element, timeOutInSec);
        } catch(TimeoutException e){
            //выброс assert-а можно оставить в пейдже, но сами asserts (assertTrue,assertEquals etc.) нужно писать исключительно в тестах
            throw new AssertionError("Linkedin page is not loaded");
        }
    }

    /**
     * Wait visibility and clickability of WebElement by Locator and click on it
     */

    public void clickOn(By locator) {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (StaleElementReferenceException e) {
            // System.out.println("StaleElementReferenceException: " + e);
            ATTEMPTS--;
            ServicesFunctions.threadSleep(500);
            if (ATTEMPTS > 0)
                clickOn(locator);
            else
                throw new AssertionError(
                        "There are too many StaleElementReferenceException while trying to click on element!!");
        } catch (ElementClickInterceptedException ex) {
            ATTEMPTS--;
            if (ATTEMPTS > 0)
                clickOn(locator);
            else
                throw new AssertionError("Превышено кол-во появлений ElementClickInterceptedException!");
        } catch (Exception e) {
            driver.findElement(locator).click();
        }
    }
    /**
     * Wait visibility and clickability of WebElement by Locator and sends keys in it
     */

    public void enterTextIn(By locator, String value) {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value+Keys.TAB);
        } catch (StaleElementReferenceException e) {
            // System.out.println("StaleElementReferenceException: " + e);
            ATTEMPTS--;
            ServicesFunctions.threadSleep(500);
            if (ATTEMPTS > 0)
                enterTextIn(locator, value);
            else
                throw new AssertionError(
                        "There are too many StaleElementReferenceException while trying to click on element!!");
        } catch (ElementClickInterceptedException ex) {
            ATTEMPTS--;
            if (ATTEMPTS > 0)
                enterTextIn(locator, value);
            else
                throw new AssertionError("Превышено кол-во появлений ElementClickInterceptedException!");
        } catch (Exception e) {
            driver.findElement(locator).sendKeys(value+Keys.TAB);
        }
        LOG.info("Text '"+value+"'is entered in the field");
    }
    /**
     * Wait visibility and clickability of WebElement by Locator and sends keys in it
     */

    public void enterTextIn(WebElement element, String value) {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(element)).sendKeys(value+Keys.TAB);
        } catch (StaleElementReferenceException e) {
            // System.out.println("StaleElementReferenceException: " + e);
            ATTEMPTS--;
            ServicesFunctions.threadSleep(500);
            if (ATTEMPTS > 0)
                enterTextIn(element, value);
            else
                throw new AssertionError(
                        "There are too many StaleElementReferenceException while trying to click on element!!");
        } catch (ElementClickInterceptedException ex) {
            ATTEMPTS--;
            if (ATTEMPTS > 0)
                enterTextIn(element, value);
            else
                throw new AssertionError("Превышено кол-во появлений ElementClickInterceptedException!");
        } catch (Exception e) {
            element.sendKeys(value+Keys.TAB);
        }
        LOG.info("Text '"+value+"'is entered in the field");
    }
    /**
     * Wait visibility and clickability of WebElement and click on it
     */

    public void clickOn(WebElement element) {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (StaleElementReferenceException e) {
            ATTEMPTS--;
            ServicesFunctions.threadSleep(500);
            if (ATTEMPTS > 0)
                clickOn(element);
            else
                throw new AssertionError(
                        "There are too many StaleElementReferenceException while trying to click on element!!");
        } catch (ElementClickInterceptedException ex) {
            ATTEMPTS--;
            if (ATTEMPTS > 0)
                clickOn(element);
            else
                throw new AssertionError("Превышено кол-во появлений ElementClickInterceptedException!");
        } catch (Exception e) {
            find(element).click();
        }

    }

    public WebElement find(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException: " + e);
            ATTEMPTS--;
            ServicesFunctions.threadSleep(500);
            if (ATTEMPTS > 0)
                find(locator);
            else
                throw new AssertionError("Превышено кол-во появлений StaleElementReferenceException!");
        } catch (ElementClickInterceptedException ex) {
            ATTEMPTS--;
            if (ATTEMPTS > 0)
                find(locator);
            else
                throw new AssertionError("Превышено кол-во появлений ElementClickInterceptedException!");
        }
        return driver.findElement(locator);
    }

    public WebElement find(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException: " + e);
            ATTEMPTS--;
            ServicesFunctions.threadSleep(500);
            if (ATTEMPTS > 0)
                find(element);
            else
                throw new AssertionError("Превышено кол-во появлений StaleElementReferenceException!");
        } catch (ElementClickInterceptedException ex) {
            ATTEMPTS--;
            if (ATTEMPTS > 0)
                find(element);
            else
                throw new AssertionError("Превышено кол-во появлений ElementClickInterceptedException!");
        }
        return element;
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }


}
