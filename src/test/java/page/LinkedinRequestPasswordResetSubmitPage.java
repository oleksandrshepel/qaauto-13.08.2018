package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LinkedinRequestPasswordResetSubmitPage extends LinkedinBasePage {

    @FindBy(xpath="//header[contains(text(),'We just emailed you a link')]")
    private WebElement contentHeaderText;

    @FindBy(xpath = "//button[@id='resend-url']")
    private WebElement resendLinkButton;

    public LinkedinRequestPasswordResetSubmitPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/checkpoint/rp/request-password-reset-submit")
                && getCurrentTitle().contains("Please check your mail for reset password link")
                && contentHeaderText.isDisplayed()
                && resendLinkButton.isDisplayed();
    }

    /*public void clickResendLinkButton(){
        resendLinkButton.click();
    } */
}
