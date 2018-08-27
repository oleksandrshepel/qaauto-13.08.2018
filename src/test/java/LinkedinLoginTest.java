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
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "limp_slim@ukr.net";
        String custPassword = "COPYC2t";

        driver.get(mainURL);
        Assert.assertEquals(driver.getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        List<WebElement> navList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//nav[@id='extended-nav']/*/ul[1]/li")));
        List<String> navListPattern = new ArrayList<String>() {};
        navListPattern.add("Главная");
        navListPattern.add("Сеть");
        navListPattern.add("Вакансии");
        navListPattern.add("Сообщения");
        navListPattern.add("Уведомления");
        navListPattern.add("Профиль");

        List<String> navListText = new ArrayList<String>() {};
        for (WebElement navListItem : navList)
            navListText.add(navListItem.getText());

        Collections.sort(navListText);
        Collections.sort(navListPattern);

        Assert.assertEquals(navListText, navListPattern,
                "Prifile webElements doesn't match");

        WebElement profileNavItem = driver.findElement(By.xpath("//li[@id='profile-nav-item']"));
        Assert.assertTrue(profileNavItem.isDisplayed(),
                "profileNavItem button is not displayed on Home page");

        driver.close();
    }

    @Test
    public void negativeWrongEmailAndPasswordLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "xxx@x.net";
        String custPassword = "Wrong123";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement alertMessage = driver.findElement(By.xpath("//div[@id='global-alert-queue']"));
        Assert.assertEquals(alertMessage.getText(),
                "There were one or more errors in your submission. Please correct the marked fields below.",
                "Alert message doesn't match");

        driver.close();

    }

    @Test
    public void negativeCorrectLoginAndWrongPasswordLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "limp_slim@ukr.net";
        String custPassword = "Wrong123";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        WebElement alertMessage = driver.findElement(By.xpath("//div[@id='global-alert-queue']"));
        Assert.assertEquals(alertMessage.getText(),
                "There were one or more errors in your submission. Please correct the marked fields below.",
                "Alert message doesn't match");
        WebElement passwordLoginError = driver.findElement(By.xpath("//span[@id='session_password-login-error']"));
        Assert.assertEquals(passwordLoginError.getText(), "Hmm, that's not the right password. Please try again or request a new one.",
                "Alert message doesn't match");

        driver.close();

    }

    @Test
    public void negativeIncorrectLoginAndCorrectPasswordLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "limpslim@ukr.net";
        String custPassword = "COPYC2t";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement alertMessage = driver.findElement(By.xpath("//div[@id='global-alert-queue']"));
        Assert.assertEquals(alertMessage.getText(),
                "There were one or more errors in your submission. Please correct the marked fields below.",
                "Alert message doesn't match");
        WebElement loginLoginError = driver.findElement(By.xpath("//span[@id='session_key-login-error']"));
        Assert.assertEquals(loginLoginError.getText(), "Hmm, we don't recognize that email. Please try again.",
                "Alert message doesn't match");

        driver.close();

    }

    @Test
    public void negativeInterchangeLoginAndPasswordLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "COPYC2t";
        String custPassword = "limp_slim@ukr.net";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement alertMessage = driver.findElement(By.xpath("//div[@id='global-alert-queue']"));
        Assert.assertEquals(alertMessage.getText(),
                "There were one or more errors in your submission. Please correct the marked fields below.",
                "Alert message doesn't match");
        WebElement keyLoginError = driver.findElement(By.xpath("//span[@id='session_key-login-error']"));
        Assert.assertEquals(keyLoginError.getText(),
                "Please enter a valid email address.",
                "Alert message doesn't match");

        driver.close();

    }

    @Test
    public void negativeEmptyLoginAndPasswordLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "";
        String custPassword = "";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        driver.close();

    }

    @Test
    public void negativeEmptyLoginAndCorrectPasswordLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "";
        String custPassword = "COPYC2t";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        driver.close();

    }

    @Test
    public void negativeCorrectLoginAndEmptyPasswordLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "limp_slim@ukr.net";
        String custPassword = "";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        driver.close();

    }

    @Test
    public void negativeJSInjectionInsteadLoginLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "<script>alert(123)</script>";
        String custPassword = "COPYC2t";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement alertMessage = driver.findElement(By.xpath("//div[@id='global-alert-queue']"));
        Assert.assertEquals(alertMessage.getText(),
                "There were one or more errors in your submission. Please correct the marked fields below.",
                "Alert message doesn't match");
        WebElement keyLoginError = driver.findElement(By.xpath("//span[@id='session_key-login-error']"));
        Assert.assertEquals(keyLoginError.getText(),
                "Please enter a valid email address.",
                "Alert message doesn't match");

        driver.close();
    }

    @Test
    public void negativeHTMLInjectionInsteadLoginLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "<form action=\"https://google.com/\"><input type=\"submit\"></form>";
        String custPassword = "COPYC2t";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement alertMessage = driver.findElement(By.xpath("//div[@id='global-alert-queue']"));
        Assert.assertEquals(alertMessage.getText(),
                "There were one or more errors in your submission. Please correct the marked fields below.",
                "Alert message doesn't match");
        WebElement keyLoginError = driver.findElement(By.xpath("//span[@id='session_key-login-error']"));
        Assert.assertEquals(keyLoginError.getText(),
                "Please enter a valid email address.",
                "Alert message doesn't match");

        driver.close();
    }

    @Test
    public void negativeLoginWithSpaceAndCorrectPasswordLoginTest(){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.linkedin.com/";
        String landingPageTitle = "LinkedIn: Log In or Sign Up";
        String custLogin = "  limp_slim@ukr.net";
        String custPassword = "COPYC2t";


        driver.get(mainURL);
        Assert.assertEquals(driver. getCurrentUrl(), mainURL, "Login page URL doesn't not match");
        Assert.assertEquals(driver.getTitle(), landingPageTitle,
                "Login page Title doesn't not match");

        WebElement loginField = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-email")));
        loginField.click();
        loginField.sendKeys(custLogin);

        WebElement loginPasswordField = driver.findElement(By.id("login-password"));
        loginPasswordField.click();
        loginPasswordField.sendKeys(custPassword);

        WebElement signInButton = driver.findElement(By.id("login-submit"));
        Assert.assertTrue(signInButton.isDisplayed(),
                "SignIn button is not displayed on Login Page");
        signInButton.click();

        WebElement profileNavItem = driver.findElement(By.xpath("//li[@id='profile-nav-item']"));
        Assert.assertTrue(profileNavItem.isDisplayed(),
                "profileNavItem button is not displayed on Home page");

        driver.close();
    }

}
