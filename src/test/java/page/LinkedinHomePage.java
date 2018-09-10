package page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;


public class LinkedinHomePage extends LinkedinBasePage {

    @FindBy (xpath = "//li[@id='profile-nav-item']")
    private WebElement profileNavItem;

    @FindBy(xpath = "//input[@placeholder and @role='combobox']")
    private WebElement searchField;

    @FindBy (xpath="//button[@id='nav-settings__dropdown-trigger']")
    private WebElement profileNavButton;

    //@FindBy (xpath="//a[@data-control-name='nav.settings_account_manage_account']")
    //private WebElement settingsAndPrivacyLink;

    public LinkedinHomePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/feed/")
                && getCurrentTitle().contains("LinkedIn")
                && profileNavItem.isDisplayed();
    }

    public boolean isNavSearchFieldDisplayed() {
        return searchField.isDisplayed();
    }

    public LinkedinSearchPage makeSearchRequest(String requestData) {
        searchField.click();
        searchField.sendKeys(requestData + Keys.ENTER);
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new LinkedinSearchPage(driver);
    }

    public void clickProfileNavButton(){
        profileNavButton.click();
    }

    public <T> T selectProfileNavDropdownItem(String itemName){
        WebElement dropdownItem = driver.findElement(
                By.xpath("//ul[@id='nav-settings__dropdown-options']/descendant:: a[contains(.,'"+itemName+"')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", dropdownItem);
        dropdownItem.click();
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
