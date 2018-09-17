package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Linkedin Home page object class
 */
public class LinkedinHomePage extends LinkedinBasePage {

    @FindBy (xpath = "//li[@id='profile-nav-item']")
    private WebElement profileNavItem;

    @FindBy(xpath = "//input[@placeholder and @role='combobox']")
    private WebElement searchField;

    @FindBy (xpath="//button[@id='nav-settings__dropdown-trigger']")
    private WebElement profileNavButton;

    //@FindBy (xpath="//a[@data-control-name='nav.settings_account_manage_account']")
    //private WebElement settingsAndPrivacyLink;

    /**
     * Constructor for LinkedinHomePage.
     *
     * @param driver - driver instance from tests.
     */
    public LinkedinHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitUntilElementVisible(profileNavItem, 10);
    }

    /**
     * Defines whether PageObject loaded by checking current url, title and webelement visibility
     *
     * @return - boolean
     */
    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/feed/")
                && getCurrentTitle().contains("LinkedIn")
                && profileNavItem.isDisplayed();
    }

    /**
     * Defines whether webElement displayed
     *
     * @return - boolean
     */
    public boolean isNavSearchFieldDisplayed() {
        return searchField.isDisplayed();
    }

    /**
     * Make search by term
     *
     * @param requestData - search term
     * @return - Linkedin Search PageObject
     */
    public LinkedinSearchPage makeSearchRequest(String requestData) {
        searchField.click();
        searchField.sendKeys(requestData + Keys.ENTER);
        return new LinkedinSearchPage(driver);
    }

    /**
     * Click profile navigate button
     */
    public void clickProfileNavButton(){
        profileNavButton.click();
    }

    /**
     * Choose a dropdown item
     *
     * Work with Prifile navigate dropdown
     * @param itemName - dropdown item name
     * @param <T> - generic type
     * @return - return an appropriate pageObject
     */
    public <T> T selectProfileNavDropdownItem(String itemName){
        WebElement dropdownItem = driver.findElement(
                By.xpath("//ul[@id='nav-settings__dropdown-options']/descendant:: a[contains(.,'"+itemName+"')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", dropdownItem);
        dropdownItem.click();
        if (getCurrentUrl().contains("/psettings"))
            return (T) new LinkedinProfileSettingsPage(driver, wait);
        if (getCurrentUrl().contains("/help"))
            return (T) new LinkedinHelpPage(driver);
        if (getCurrentUrl().contains("/recent-activity"))
            return (T) new LinkedinActivityPage(driver);
        if (getCurrentUrl().contains("/mjobs/jobPosting"))
            return (T) new LinkedinJobsPage(driver);
        if(getCurrentUrl().contains("/m/logout"))
            return (T) new LinkedinLogoutPage(driver);
        if(getCurrentUrl().equals("https://www.linkedin.com/"))
            return (T) new LinkedinLoginPage(driver);
        else return (T) this;
    }
}
