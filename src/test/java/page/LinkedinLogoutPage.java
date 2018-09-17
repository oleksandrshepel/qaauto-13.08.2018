package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

/**
 * LinkedinLogout PageObject class
 */
public class LinkedinLogoutPage extends LinkedinBasePage {

    @FindBy(css = "#login-email")//(xpath="//input[@id='login-email']")
    private WebElement userEmailField;

    @FindBy(css = "#login-password")//(xpath="//input[@id='login-password']")
    private WebElement userPasswordField;

    @FindBy(css="#login-submit")//(xpath="//input[@id='login-submit']")
    private WebElement signInButton;

    @FindBy (xpath = "//div[@id='global-alert-queue']")
    private WebElement globalAlertMessage;

    @FindBy (xpath = "//span[@id='session_password-login-error']")
    private WebElement alertMessagePassword;


    /**
     * Constructor for LinkedinLogout PageObject
     *
     * @param driver - driver instance from test
     */
    public LinkedinLogoutPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitUntilElementVisible(userEmailField, 10);
    }

    /**
     * Defines whether webElement displayed
     *
     * @return - boolean
     */
    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/m/logout/")
                && getCurrentTitle().contains("LinkedIn")
                && userEmailField.isDisplayed() //--не видит xpath
                ;
    }

    /**
     * User login username/password.
     *
     * @param userEmail - character sequence with user email for login
     * @param userPassword - character sequence with user password for login
     * @param <T> -generic type
     * @return - returns an appropriate PageObject depending current url
     * (LinkedinHomePage, LinkedinLoginSubmitPage or LinkedinLoginPage)
     */
    public LinkedinHomePage login(String userEmail, String userPassword) {
        userEmailField.clear();
        userEmailField.sendKeys(userEmail);
        userPasswordField.sendKeys(userPassword);
        signInButton.click();
        return new LinkedinHomePage(driver);
    }

    /**
     * Retrieves a global/top alert message text
     *
     * @return - string of message text
     */
    public String getAlertMessageText(){
        return globalAlertMessage.getText();
    }

    /**
     * Retrieves a password alert message text
     *
     * @return - string of message text
     */
    public String getUserPasswordAlertText(){
        return alertMessagePassword.getText();
    }
}
