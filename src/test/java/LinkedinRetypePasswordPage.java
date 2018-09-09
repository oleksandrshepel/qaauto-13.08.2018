import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

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

    public LinkedinRetypePasswordPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded() {
        return getCurrentUrl().contains("https://www.linkedin.com/checkpoint/rp/password-reset")
                && getCurrentTitle().contains("Reset Your Password | LinkedIn")
                && contentHeaderText.isDisplayed();
    }

    public void enterNewPassword(String newPassword){
        newPasswordField.sendKeys(newPassword+Keys.TAB);
    }

    public void enterRetypeNewPassword(String newPassword){
        retypeNewPasswordField.sendKeys(newPassword+Keys.TAB);
    }

    public void checkRequireAllDevices(){
        requireAllDevicesCheckbox.click();
    }

    public <T> T clickSubmitButton(){
        submitButton.click();
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(getCurrentUrl().contains("/checkpoint/rp/password-reset-submit"))
            return (T) new LinkedinPasswordResetSubmitPage(driver);
        else return (T) this;
    }
}
