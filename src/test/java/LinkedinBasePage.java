import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LinkedinBasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public String getCurrentTitle(){
        return driver.getTitle();
    }
}
