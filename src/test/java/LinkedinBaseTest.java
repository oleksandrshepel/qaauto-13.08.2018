import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.util.concurrent.TimeUnit;

public class LinkedinBaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected LinkedinLoginPage linkedinLoginPage;
    protected LinkedinHomePage linkedinHomePage;
    protected LinkedinLoginSubmitPage linkedinLoginSubmitPage;
    protected LinkedinSearchPage linkedinSearchPage;

    private String mainURL;


    @BeforeMethod
    public void beforeMethod(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        mainURL = "https://www.linkedin.com/";
        driver.get(mainURL);
        linkedinLoginPage = new LinkedinLoginPage(driver);
    }

    @AfterMethod
    public void afterMethod(){
        driver.quit();
    }

}
