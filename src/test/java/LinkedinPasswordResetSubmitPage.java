import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

public class LinkedinPasswordResetSubmitPage extends LinkedinBasePage {

    @FindBy(xpath="//header[@class='content__header' and contains(text(),'Your password has been changed successfully')]")
    private WebElement contentHeaderText;

    @FindBy(xpath = "//button[@id='reset-password-submit-button']")
    private WebElement goHomepageButton;

    public LinkedinPasswordResetSubmitPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/checkpoint/rp/password-reset-submit")
                && getCurrentTitle().contains("You've successfully reset your password. | LinkedIn")
                && contentHeaderText.isDisplayed();
    }

    public <T> T clickGoHomepage(){
        goHomepageButton.click();
        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (getCurrentUrl().contains("/feed"))
            return (T) new LinkedinHomePage(driver, wait);
        else return (T) this;

    }
}
