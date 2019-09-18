package page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LinkedinLoginSubmit PageObject class
 */
public class LinkedinLoginSubmitPage extends LinkedinBasePage{
    private String loginSubmitPageUrl = "https://www.linkedin.com/uas/login-submit";
    private static Logger LOG;

    @FindBy (xpath = "//*[@href='/start/join']")
    private WebElement buttonJoinNow;

    @FindBy (xpath = "//*[@class='header__content__heading']")
    private WebElement headerContent;

    @FindBy (xpath = "//*[@id='error-for-username']")
    private WebElement alertMessageLogin;

    @FindBy (xpath = "//*[@id='error-for-password']")
    private WebElement alertMessagePassword;

    /**
     * Constructor for LinkedinLoginSubmit PageObject
     * @param driver - instance of webDriver from test
     */
    public LinkedinLoginSubmitPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        LOG = Logger.getLogger(LinkedinLoginSubmitPage.class);
        assertWebElementIsVisible(buttonJoinNow,10);
        LOG.info("LinkedinLoginSubmitPage is loaded");
    }

    /**
     * Defines whether pageObject loaded by checking url,title and webElement visibility
     * @return - boolean
     */
    //@Step
    public boolean isPageLoaded(){
        return getCurrentUrl().contains(loginSubmitPageUrl)
                && getCurrentTitle().contains("LinkedIn Sign in")
                && buttonJoinNow.isDisplayed();
    }

    /**
     * Retrieves a global/top alert message text
     * @return - string of message text
     */
    //@Step
    public String getHeaderContent(){
        return headerContent.getText();
    }

    /**
     * Retrieves a login alert message text
     * @return - string of message text
     */
    //@Step
    public String getUserEmailAlertText(){
        return waitUntilElementVisible(alertMessageLogin, 10).getText();
    }

    /**
     * Retrieves a password alert message text
     * @return - string of message text
     */
    //@Step
    public String getUserPasswordAlertText(){
        return waitUntilElementVisible(alertMessagePassword,10).getText();
    }

}
