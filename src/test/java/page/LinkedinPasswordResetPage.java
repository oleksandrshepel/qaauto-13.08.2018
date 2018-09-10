package page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.GMailService;

import static java.lang.Thread.sleep;

public class LinkedinPasswordResetPage extends LinkedinBasePage {

    @FindBy(xpath="//header[@class='content__header' and contains(text(),\"First, let's find your account\")]")
    private WebElement contentHeaderText;

    @FindBy(xpath="//input[@name='userName']")
    private WebElement userNameField;

    @FindBy(xpath="//button[@id='reset-password-submit-button']")
    private WebElement findAccountButton;

    public LinkedinPasswordResetPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/uas/request-password-reset?trk=uno-reg-guest-home-forgot-password")
                && getCurrentTitle().contains("Reset Password | LinkedIn")
                && contentHeaderText.isDisplayed();
    }

    public void enterUserEmail(String email){
        userNameField.sendKeys(email+ Keys.TAB);
    }

    public <T> T clickFindAccount(){
        //добавляем утилиту GMailService чтобы запустить сессию на ожидание нового письма.
        // Коннект нужно сделать до метода findAccountButton.click(), иначе письмо пропустится.
        // утилита ожидает именно новое письмо после конекта и не "роется" в старой почте
        String messageSubject = "here's the link to reset your password";
        String messageTo = "qaauto13082018@gmail.com";
        String messageFrom = "security-noreply@linkedin.com";

        GMailService gMailService = new GMailService();
        gMailService.connect();
        findAccountButton.click();

        String message = gMailService.waitMessage(messageSubject, messageTo, messageFrom, 120);
        System.out.println("Content: " + message);

        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(getCurrentUrl().contains("/rp/request-password-reset-submit"))
        return (T) new LinkedinRequestPasswordResetSubmitPage(driver);
        else return (T) this;
    }
}
