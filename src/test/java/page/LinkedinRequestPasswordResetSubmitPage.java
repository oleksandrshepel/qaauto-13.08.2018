package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Linkedin Request Password Reset Submit PageObject class
 */
public class LinkedinRequestPasswordResetSubmitPage extends LinkedinBasePage {

    @FindBy(xpath="//header[contains(text(),'We just emailed you a link')]")
    private WebElement contentHeaderText;

    @FindBy(xpath = "//button[@id='resend-url']")
    private WebElement resendLinkButton;

    /**
     * Constructor for Linkedin Request Password Reset Submit PageObject
     *
     * @param driver - webdriver instance from a test
     */
    public LinkedinRequestPasswordResetSubmitPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Defines whether page object is loaded by checking current url, title and visibility of webelements
     *
     * @return - boolean
     */
    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/checkpoint/rp/request-password-reset-submit")
                && getCurrentTitle().contains("Please check your mail for reset password link")
                && contentHeaderText.isDisplayed()
                && resendLinkButton.isDisplayed();
    }

    /**
     * Uses gMailService class to get a link for resetting user's password
     *
     * @return -  Linkedin Retype Password Page object
     */
    public LinkedinRetypePasswordPage navigateToLinkFromEmail(){
        String messageSubject = "here's the link to reset your password";
        String messageTo = "qaauto13082018@gmail.com";
        String messageFrom = "security-noreply@linkedin.com";
        driver.get(handleEmailMessage(gMailService.waitMessage(messageSubject, messageTo, messageFrom, 60)));
        return new LinkedinRetypePasswordPage(driver);
    }

    /**
     * Cuts out a proper link from an email message being got via gMailService util
     *
     * @param message -  a char sequence of an email message
     * @return - a char sequence of a link to Linkedin Retype Password Page
     */
    private String handleEmailMessage(String message){
        StringBuilder emailMessage = new StringBuilder(message);
        int startIndex = emailMessage.indexOf("http", emailMessage.indexOf("To change your LinkedIn password, click <a href=\""));
        int endIndex = emailMessage.indexOf("\" style", startIndex);
        String linkToUrl = emailMessage.substring(startIndex,
                endIndex).replace("amp;","");
        //Более простой способ альтернативный indexOf это применить методі класса StringUtils, однако в pom.xml нужно добавить в dependency библиотеку org.apache.commons.lang3 версии 3.8
        //String resetPasswordLink = StringUtils.substringBetween(message,"To change your LinkedIn password, click <a href=\"","\" style").replace("amp;","");
        return linkToUrl;
    }

    /*public void clickResendLinkButton(){
        resendLinkButton.click();
    } */
}
