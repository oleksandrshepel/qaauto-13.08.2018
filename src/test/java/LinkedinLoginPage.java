import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

public class LinkedinLoginPage extends LinkedinBasePage {
    private String landingPageTitle = "LinkedIn: Log In or Sign Up";
    private String mainURL = "https://www.linkedin.com/";

    @FindBy(xpath = "//input[@id='login-email']") //аннотация заменяет initElements
    private WebElement userEmailField;

    @FindBy(xpath = "//input[@id='login-password']")
    private WebElement userPasswordField;

    @FindBy(xpath = "//input[@id='login-submit']")
    private WebElement signInButton;

    public LinkedinLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); //Можем вычитать из другого класса тогда вместо this ставим LinkedinHomePage.class
    }

    public <T> T login(String userEmail, String userPassword) {
        userEmailField.sendKeys(userEmail);
        userPasswordField.sendKeys(userPassword);
        signInButton.click();
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (getCurrentUrl().contains("/feed")) {
            return (T) new LinkedinHomePage(driver, wait);
        }
        if (getCurrentUrl().contains("/login-submit")) {
            return (T) new LinkedinLoginSubmitPage(driver);
        }
        else {
            return (T) this; // или Т()this;
                            // или(T)PageFactory.initElements(driver, LinkedinLoginPage.class); - эта реализация вернет new LinkedinLoginPage() с проинициализированными полями веб елементов
                           // даную конструкцию следует использовать, если в конструкции возвращаемой пейджи мы не прописываем PageFactory.initElements(driver, this)
        }
    }

    public boolean isPageLoaded(){
        return getCurrentUrl().equals(mainURL)
                && getCurrentTitle().equals(landingPageTitle)
                && signInButton.isDisplayed();
    }
}
