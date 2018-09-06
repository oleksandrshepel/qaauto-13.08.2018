import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    public LinkedinLoginSubmitPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    public boolean isPageLoaded(){
        return getCurrentUrl().equals(loginSubmitPageUrl)
                && getCurrentTitle().equals("Sign In to LinkedIn")
                && buttonJoinNow.isDisplayed();
    }

    public String getAlertMessageText(){
        return globalAlertMessage.getText();
    }

    public String getUserEmailAlertText(){
        return alertMessageLogin.getText();
    }

    public String getUserPasswordAlertText(){
        return alertMessagePassword.getText();
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
