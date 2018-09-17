package page;

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
    private String mainURL = "https://www.linkedin.com/";

    @FindBy(xpath = "//input[@id='login-email']") //аннотация заменяет initElements
    private WebElement userEmailField;

    @FindBy(xpath = "//input[@id='login-password']")
    private WebElement userPasswordField;

    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement signInButton;

    @FindBy(xpath = "//a[@class='link-forgot-password']")
    private WebElement forgotPasswordLink;

    /**
     * Constructor for LinkedinLoginPage.
     *
     * @param driver - driver instance from tests.
     */
    public LinkedinLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); //Можем вычитать из другого класса тогда вместо this ставим page.LinkedinHomePage.class
        waitUntilElementVisible(signInButton, 10);
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
        userEmailField.sendKeys(userEmail);
        userPasswordField.sendKeys(userPassword);
        signInButton.click();
        if (getCurrentUrl().contains("/feed")) {
            return (T) new LinkedinHomePage(driver);
        }
        if (getCurrentUrl().contains("/login-submit")) {
            return (T) new LinkedinLoginSubmitPage(driver);
        }
        else {
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
        forgotPasswordLink.click();
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
                && signInButton.isDisplayed();
    }
}
