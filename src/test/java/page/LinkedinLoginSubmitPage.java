package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LinkedinLoginSubmit PageObject class
 */
public class LinkedinLoginSubmitPage extends LinkedinBasePage{
    private String loginSubmitPageUrl = "https://www.linkedin.com/uas/login-submit";

    @FindBy (xpath = "//a[@title='Join now']")
    private WebElement buttonJoinNow;

    @FindBy (xpath = "//div[@id='global-alert-queue']")
    private WebElement globalAlertMessage;

    @FindBy (xpath = "//span[@id='session_key-login-error']")
    private WebElement alertMessageLogin;

    @FindBy (xpath = "//span[@id='session_password-login-error']")
    private WebElement alertMessagePassword;

    /**
     * Constructor for LinkedinLoginSubmit PageObject
     * @param driver - instance of webDriver from test
     */
    public LinkedinLoginSubmitPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        assertWebElementIsVisible(buttonJoinNow,10);

    }

    /**
     * Defines whether pageObject loaded by checking url,title and webElement visibility
     *
     * @return - boolean
     */
    public boolean isPageLoaded(){
        return getCurrentUrl().contains(loginSubmitPageUrl)
                && getCurrentTitle().equals("Sign In to LinkedIn")
                && buttonJoinNow.isDisplayed();
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
     * Retrieves a login alert message text
     *
     * @return - string of message text
     */
    public String getUserEmailAlertText(){
        return waitUntilElementVisible(alertMessageLogin, 10).getText();
    }

    /**
     * Retrieves a password alert message text
     *
     * @return - string of message text
     */
    public String getUserPasswordAlertText(){
        return waitUntilElementVisible(alertMessagePassword,10).getText();
    }

    /*public boolean isAlertMessageDisplayed(String alertMessage){
        return getCurrentUrl().equals("https://www.linkedin.com/uas/login-submit")
                && getCurrentTitle().equals("Sign In to LinkedIn")
                && globalAlertMessage.getText().equals(alertMessage);
    }*/

    /*public boolean isAlertMessageEmailDisplayed(String alertMessage){
        return getCurrentUrl().equals("https://www.linkedin.com/uas/login-submit")
                && getCurrentTitle().equals("Sign In to LinkedIn")
                && alertMessageLogin.getText().equals(alertMessage);
    }*/
    /*public boolean isAlertMessagePasswordDisplayed(String alertMessage){
        return getCurrentUrl().equals("https://www.linkedin.com/uas/login-submit")
                && getCurrentTitle().equals("Sign In to LinkedIn")
                && alertMessagePassword.getText().equals(alertMessage);
    }*/

}
