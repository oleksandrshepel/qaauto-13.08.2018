package page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

/**
 * LinkedinPasswordReset PageObject class
 */
public class LinkedinPasswordResetPage extends LinkedinBasePage {

    @FindBy(xpath="//header[@class='content__header' and contains(text(),\"First, let's find your account\")]")
    private WebElement contentHeaderText;

    @FindBy(xpath="//input[@name='userName']")
    private WebElement userNameField;

    @FindBy(xpath="//button[@id='reset-password-submit-button']")
    private WebElement findAccountButton;

    /**
     * Construc tor for LinkedinPasswordReset PageObject class
     * @param driver - webDriver instance from test
     */
    public LinkedinPasswordResetPage (WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitUntilElementVisible(contentHeaderText, 10);
    }

    /**
     * Defines whether webElement displayed
     *
     * @return - boolean
     */
    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/uas/request-password-reset?trk=uno-reg-guest-home-forgot-password")
                && getCurrentTitle().contains("Reset Password | LinkedIn")
                && contentHeaderText.isDisplayed();
    }

    /**
     * Types user email for searching
     *
     * @param email - char sequence with valid user email
     */
    public void enterUserEmail(String email){
        userNameField.sendKeys(email+ Keys.TAB);
    }

    /**
     * Click button find account for searching
     *
     * @return LinkedinRequestPasswordResetSubmit PageObject
     */
    public LinkedinRequestPasswordResetSubmitPage clickFindAccount(){
        /* Утилита GMailService ожидает поступления на почту нового (именно нового) письма с определ'нніми параметрами.
           Метод connect() необходимо вызвать до того, как Linkedin отправит письмо, в нашем случае до вызова
           метода findAccountButton.click(), иначе письмо пропустится.
           (!) Утилита ожидает именно новое письмо после конекта и не "роется" в старой почте
        */
        gMailService.connect();
        findAccountButton.click();
        return new LinkedinRequestPasswordResetSubmitPage(driver);
    }
}
