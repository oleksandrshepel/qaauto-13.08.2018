package page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

/**
* LinkedinLogin Page Object class.
 */

public class LinkedinLoginPage extends LinkedinBasePage {
    private String landingPageTitle = "LinkedIn: Log In or Sign Up";
    private static final String mainURL = "https://www.linkedin.com/";
    private static Logger LOG;

    @FindBy(xpath = "//*[@class='nav__button-secondary']")
    private WebElement signInUpperButton;

    @FindBy(xpath = "//*[@name='session_key']") //аннотация заменяет initElements
    private WebElement userEmailField;

    @FindBy(xpath = "//*[@name='session_password']")
    private WebElement userPasswordField;

    @FindBy(xpath = "//*[@class='sign-in-form__submit-btn']")
    private WebElement signInButton;

    @FindBy(xpath = "//a[@name='guest_homepage-basic_forgot_password']")
    private WebElement forgotPasswordLink;

    @FindBy (xpath = "//div[@class='input input--error']//p[@role='alert']")
    private WebElement alertMessageLoginPassword;

    /**
     * Constructor for LinkedinLoginPage.
     * @param driver - driver instance from tests.
     */
    public LinkedinLoginPage(WebDriver driver) {
        this.driver = driver;
        LOG = Logger.getLogger(LinkedinLoginPage.class);
        PageFactory.initElements(driver, this); //Можем вычитать из другого класса тогда вместо this ставим page.LinkedinHomePage.class
        assertWebElementIsVisible(signInUpperButton,10);
    }

    /**
     * User login username/password.
     *
     * @param userEmail - character sequence with user email for login
     * @param userPassword - character sequence with user password for login
     * @param <T> -
     * @return - returns an appropriate PageObject depending current url
     * (LinkedinHomePage, LinkedinLoginSubmitPage or LinkedinLoginPage)
     */
    public <T> T login(String userEmail, String userPassword) {
        enterTextIn(userEmailField, userEmail);
        enterTextIn(userPasswordField, userPassword);
        clickOn(signInButton);
        if (isUrlContains("/feed", 5)) {
            LOG.info("Use has been logged in successfully");
            return (T) new LinkedinHomePage(driver);
        }
        if (isUrlContains("/login-submit", 5)) {
            LOG.info("Use got navigated to the LinkedinLoginSubmitPage");
            return (T) new LinkedinLoginSubmitPage(driver);
        }
        else {
            LOG.info("Use has been logged in successfully and stayed on the Login page");
            return (T) this; // или Т()this;
                            // или(T)PageFactory.initElements(driver, page.LinkedinLoginPage.class); - эта реализация вернет new page.LinkedinLoginPage() с проинициализированными полями веб елементов
                           // даную конструкцию следует использовать, если в конструкции возвращаемой пейджи мы не прописываем PageFactory.initElements(driver, this)
        }
    }

    /**
     * Click link Forgot password so that password would be changed
     *
     * @return - LinkedinPasswordReset PageObject instance
     */
    public LinkedinPasswordResetPage clickForgotPasswordLink(){
        clickOn(forgotPasswordLink);
        LOG.info("Forgot password link was clicked");
        return new LinkedinPasswordResetPage(driver);
    }

    /**
     * Defines whether page loaded by checking url< title and webElement visibility
     *
     * @return - boolean
     */
    public boolean isPageLoaded(){
        return getCurrentUrl().equals(mainURL)
                && getCurrentTitle().equals(landingPageTitle)
                && signInUpperButton.isDisplayed();
    }

    /**
     * Retrieves a password alert message text
     * @return - string of message text
     */
    public String getUserLoginPasswordAlertText(){
        return waitUntilElementVisible(alertMessageLoginPassword,10).getText();
    }

}
