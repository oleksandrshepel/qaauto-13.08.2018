package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import page.*;
import util.ServicesFunctions;

import java.io.File;
import  java.lang.*;

/**
 * Parent class for all tests. Here the driver instance is being created.
 * The class contains before and after methods and declares all pageObjects.
 */
public class LinkedinBaseTest {
    protected WebDriver driver;
    protected static Logger LOG;
    protected boolean isTestPass = false;
    protected String pathToScreenShot;
    protected LinkedinLoginPage linkedinLoginPage;
    protected LinkedinHomePage linkedinHomePage;
    protected LinkedinLoginSubmitPage linkedinLoginSubmitPage;
    protected LinkedinSearchPage linkedinSearchPage;
    protected LinkedinPasswordResetPage linkedinPasswordResetPage;
    protected LinkedinRequestPasswordResetSubmitPage linkedinRequestPasswordResetSubmitPage;
    protected LinkedinRetypePasswordPage linkedinRetypePasswordPage;
    protected LinkedinPasswordResetSubmitPage linkedinPasswordResetSubmitPage;
    protected LinkedinLogoutPage linkedinLogoutPage;

    /**
     * Before method to create webdriver instance and get to the landing page
     * Defines a webdriver instance for a particular browser
     */
    @Parameters({"browserName","mainURL"})
    @BeforeMethod
    public void setUp (@Optional("firefox") String browserName,
                      @Optional("https://www.linkedin.com/") String mainURL) throws Exception {
        //Аннотация @Optional("chrome") используется для того, чтобы указать дефолтное значение параметров.
        //Это даст возможность запускать тесты из Intellij, а анотация @Parameters позволит сделать запуск из xml файла (пр.кл.мыши по xml файлу -> run)

        //WebDriverManager позволяет без предустановки драйвера работать с драйверами под разные браузеры.
        // https://github.com/bonigarcia/webdrivermanager
        switch(browserName.toLowerCase()){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            case "internetexplorer":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new Exception("Can not work with a type of browser "+browserName);
        }
        File file = new File(""); //Мы создали обьект файлы без пути для того чтобы потом спросить нас о пути
        //Этот метод создает путь к папкам которые мы создали папку с именем , пекедж с именем, имя файла и его разширение
        pathToScreenShot = file.getAbsolutePath() + "\\target\\screenshot\\"
                + this.getClass().getPackage().getName() + "\\"
                + this.getClass().getSimpleName() + "\\";
        driver.manage().window().maximize();
        driver.get(mainURL);
        linkedinLoginPage = new LinkedinLoginPage(driver);
    }

    /**
     * After method to quit webdriver
     * alwaysRun = true - should be added otherwise if an exception is thrown in BeforeMethod then AfterMethod will not work
     */
    @AfterMethod(alwaysRun = true)//добавили параметр (alwaysRun = true) иначе при выбросе exception в BeforeMethod наш AfterMethod не отработает
    public void tearDown(){
        if (!(driver == null)) { //Если дравера нету то ничего закрывать
            if (!isTestPass) {
                //Только в случаи false если тест упадет при закрытии драйвера снимать скринШот
                ServicesFunctions.screenShot(driver, pathToScreenShot+".jpg");
            }
            driver.quit();
        }
    }

    protected void setIsTestPass(boolean value){
        isTestPass = value;
    }

}