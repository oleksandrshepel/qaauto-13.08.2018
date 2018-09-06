import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;


public class LinkedinHomePage extends LinkedinBasePage {

    @FindBy (xpath = "//li[@id='profile-nav-item']")
    private WebElement profileNavItem;

    @FindBy(xpath = "//input[@placeholder and @role='combobox']")
    private WebElement searchField;

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
}
