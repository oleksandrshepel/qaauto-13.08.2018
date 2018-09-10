package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

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



    public LinkedinLogoutPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/m/logout/")
                && getCurrentTitle().contains("LinkedIn")
               // && userEmailField.isDisplayed() --не видит xpath
                ;
    }

    public <T> T login(String userEmail, String userPassword) {
        userEmailField.clear();
        userEmailField.sendKeys(userEmail);
        userPasswordField.sendKeys(userPassword);
        signInButton.click();
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (getCurrentUrl().contains("/feed"))
            return (T) new LinkedinHomePage(driver, wait);
        else return (T) this;
    }

    public String getAlertMessageText(){
        return globalAlertMessage.getText();
    }

    public String getUserPasswordAlertText(){
        return alertMessagePassword.getText();
    }
}
