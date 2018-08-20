import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinkedinLoginTest {

    @Test
    public void successfulLoginTest(){
        //Navigate to 'Linkedin.com'
        //Verify that login page is loaded
        //Enter user e-mail
        //Enter user password
        //Click 'Sign in' button
        //Verify Home page is loaded

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String custLogin = "limp_slim@ukr.net";
        String custPassword = "COPYC2t";
        By loginFieldLocator = By.id("login-email");
        By loginPasswordLocator = By.id("login-password");
        By buttonSignInLocator = By.id("login-submit");
        By navMainList = By.xpath("//nav[@id='extended-nav']/*/ul[1]/li");


        driver.get(mainURL);
        Assert.assertEquals(driver.getCurrentUrl(), mainURL, "Login page URL doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(loginFieldLocator));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(loginPasswordLocator);
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement buttonSignIn = driver.findElement(buttonSignInLocator);
        buttonSignIn.click();

        List<WebElement> navList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(navMainList));
        List<String> navListPattern = new ArrayList<String>() {};
        navListPattern.add("Главная");
        navListPattern.add("Сеть");
        navListPattern.add("Вакансии");
        navListPattern.add("Сообщения");
        navListPattern.add("Уведомления");
        navListPattern.add("Профиль");

        List<String> navListText = new ArrayList<String>() {};
        for(int i=0; i<navList.size(); i++)
            navListText.add(navList.get(i).getText());

        Collections.sort(navListText);
        Collections.sort(navListPattern);

        Assert.assertEquals(navListText, navListPattern);

        driver.close();



    }
}
