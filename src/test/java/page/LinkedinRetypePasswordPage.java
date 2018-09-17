package page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

/**
 * Linkedin Retype Password PageObject class
 */
public class LinkedinRetypePasswordPage extends LinkedinBasePage {

    @FindBy(xpath="//header[@class='content__header' and contains(text(),'Finally, choose a new password.')]")
    private WebElement contentHeaderText;

    @FindBy(xpath = "//input[@id='newPassword']")
    private WebElement newPasswordField;

    @FindBy(xpath = "//input[@id='confirmPassword']")
    private WebElement retypeNewPasswordField;

    @FindBy(xpath = "//label[@for='deleteAllSession']")//input[@name='deleteAllSession']
    private WebElement requireAllDevicesCheckbox;

    @FindBy(xpath = "//button[@id='reset-password-submit-button']")
    private WebElement submitButton;

    /**
     * Constructor for Linkedin Retype Password PageObject class
     *
     * @param driver - a webdriver instance from a test
     */
    public LinkedinRetypePasswordPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        assertWebElementIsVisible(contentHeaderText,10);
    }

    /**
     * Defines whether page loaded by checking current url, title and visibility of a webelement
     *
     * @return - boolean
     */
    public boolean isPageLoaded() {
        return getCurrentUrl().contains("https://www.linkedin.com/checkpoint/rp/password-reset")
                && getCurrentTitle().contains("Reset Your Password | LinkedIn")
                && contentHeaderText.isDisplayed();
    }

    /**
     * Types new user's password
     *
     * @param newPassword - a char sequence of a new password
     */
    public void enterNewPassword(String newPassword){
        newPasswordField.sendKeys(newPassword+Keys.TAB);
    }
    /**
     * Retypes new user's password
     *
     * @param newPassword - a char sequence of a new password
     */
    public void enterRetypeNewPassword(String newPassword){
        retypeNewPasswordField.sendKeys(newPassword+Keys.TAB);
    }

    /**
     * Clicks the checkbox
     */
    public void checkRequireAllDevices(){
        requireAllDevicesCheckbox.click();
    }

    /**
     * Clicks submit button to confirm password resetting
     *
     * @return - Linkedin Password Reset Submit Page object
     */
    public LinkedinPasswordResetSubmitPage clickSubmitButton(){
        submitButton.click();
        return new LinkedinPasswordResetSubmitPage(driver);
    }
}
