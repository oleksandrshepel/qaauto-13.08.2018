package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

/**
 * Linkedin Password Reset Submit PageObject
 */
public class LinkedinPasswordResetSubmitPage extends LinkedinBasePage {

    @FindBy(xpath="//header[@class='content__header' and contains(text(),'Your password has been changed successfully')]")
    private WebElement contentHeaderText;

    @FindBy(xpath = "//button[@id='reset-password-submit-button']")
    private WebElement goHomepageButton;

    /**
     * Conctructor for LinkedinPasswordResetSubmitPage
     *
     * @param driver - webdriver instance from a test
     */
    public LinkedinPasswordResetSubmitPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        assertWebElementIsVisible(contentHeaderText,10);
    }

    /**
     * Defines whether page object is loaded by checking current url, title and visibility of a webelement
     *
     * @return - boolean
     */
    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/checkpoint/rp/password-reset-submit")
                && getCurrentTitle().contains("You've successfully reset your password. | LinkedIn")
                && contentHeaderText.isDisplayed();
    }

    /**
     * Makes click on reset password submit button
     *
     * @return - Linkedin Home Page object
     */
    public LinkedinHomePage clickGoHomepage(){
        goHomepageButton.click();
        return new LinkedinHomePage(driver);
    }
}
