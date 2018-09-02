import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LinkedinLoginTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String mainURL;

    @BeforeMethod
    public void beforeMethod(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        mainURL = "https://www.linkedin.com/";
        driver.get(mainURL);
    }

    @AfterMethod
    public void afterMethod(){
        driver.quit();
    }

    @DataProvider
    public Object[][] validDataProvider() {
        return new Object[][]{
                { "limp_slim@ukr.net", "COPYC2t" },
                { "LIMP_slim@ukr.net", "COPYC2t" },
                { "  limp_slim@ukr.net  ", "COPYC2t" }
        };
    }

    @DataProvider
    public Object[][] wrongEmailAndPasswordDataProvider() {
        return new Object[][]{
                { "Txx@xt.net", "Wrong1234", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Hmm, we don't recognize that email. Please try again."},
                { "COPYC2t", "limp_slim@ukr.net", "There were one or more errors in your submission. Please correct the marked fields below.",
                "Please enter a valid email address."}
        };
    }

    @DataProvider
    public Object[][] correctEmailAndWrongPasswordDataProvider() {
        return new Object[][]{
                { "limp_slim@ukr.net", "Wrong12345", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Hmm, that's not the right password. Please try again or request a new one."},
                { "limp_slim@ukr.net", "123", "There were one or more errors in your submission. Please correct the marked fields below.",
                "The password you provided must have at least 6 characters."},
                { "limp_slim@ukr.net",
                        "Maintaining a well-tested codebase is mission-critical, but figuring out where your tests are lacking can be painful. You're already running your tests on a continuous integration server, let it do the heavy lifting. Coveralls works with your CI to sift through coverage data to find gaps you didn't know you hadtuyewqtruwieuiri734y687368574yrfudsfgdjhfdisaufhyirwe47y857346t87eryfgdsadhfuisaugferu7y8t7e",
                        "There were one or more errors in your submission. Please correct the marked fields below.",
                        "The password you provided must have at most 400 characters."}
        };
    }

    @DataProvider
    public Object[][] wrongEmailAndCorrectPasswordDataProvider() {
        return new Object[][]{
                { "TTx@xtx.net", "COPYC2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Hmm, we don't recognize that email. Please try again."},
                { "12 ^#%$^&%^&^%&*&*(*&* ^5876098765", "COPYC2t",
                        "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Be sure to include \"+\" and your country code."},
                { "limp_slimukr.net", "COPYC2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Please enter a valid email address."},
                { "Maintaining a well-tested codebase is mission-critical, but figuring out where your tests are lacking can be painful. You're already running your tests on a continuous integration server, let it do the heavy lifting. Coveralls works with your CI to sift through coverage data to find gaps you didn't know you had",
                        "COPYC2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "The text you provided is too long (the maximum length is 128 characters, your text contains 312 characters)."},
                { "r", "COPYC2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "The text you provided is too short (the minimum length is 3 characters, your text contains 1 character)."}
        };
    }

    @DataProvider
    public Object[][] emptyLoginAndOrPasswordDataProvider(){
        return new Object[][]{
                { "", ""},
                { "", "COPYC2t"},
                { "limp_slim@ukr.net", ""}
        };
    }

    @DataProvider
    public Object[][] injectionInsteadLogin (){
        return new Object[][]{
                { "<script>alert(123)</script>", "COPYC2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Please enter a valid email address."},
                { "<form action=\"http://google.com\"><input type=»submit»></form>", "COPYC2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Please enter a valid email address."},
                { "SELECT * FROM blog WHERE code LIKE 'a%'", "COPYC2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Please enter a valid email address."},
        };
    }



    @Test(dataProvider = "validDataProvider")
    public void successfulLoginTest(String userEmail, String userPassword){
        //Navigate to 'Linkedin.com'
        //Verify that login page is loaded
        //Enter user e-mail
        //Enter user password
        //Click 'Sign in' button
        //Verify Home page is loaded

        LinkedinLoginPage linkedinLoginPage = new LinkedinLoginPage(driver);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "The Login page is not loaded");
        linkedinLoginPage.login(userEmail, userPassword);

        LinkedinHomePage linkedinHomePage = new LinkedinHomePage(driver);
        Assert.assertTrue(linkedinHomePage.isPageLoaded(), "The Home page is not loaded");
    }

    @Test(dataProvider = "wrongEmailAndPasswordDataProvider")
    public void negativeWrongEmailAndPasswordLoginTest(String userEmail, String userPassword, String alertMessage, String alertMessageEmail){
        LinkedinLoginPage linkedinLoginPage = new LinkedinLoginPage(driver);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginPage.login(userEmail, userPassword);
        LinkedinLoginSubmitPage linkedinLoginSubmitPage = new LinkedinLoginSubmitPage(driver);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageDisplayed(alertMessage), "Alert message text is wrong or is not displayed");
        Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageEmailDisplayed(alertMessageEmail), "Alert message Email is wrong or is not displayed");
    }

    @Test(dataProvider = "correctEmailAndWrongPasswordDataProvider")
    public void negativeCorrectLoginAndWrongPasswordLoginTest(String userEmail, String userPassword, String alertMessage, String alertMessagePassword){
        LinkedinLoginPage linkedinLoginPage = new LinkedinLoginPage(driver);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginPage.login(userEmail, userPassword);
        LinkedinLoginSubmitPage linkedinLoginSubmitPage = new LinkedinLoginSubmitPage(driver);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageDisplayed(alertMessage), "Alert message text is wrong or is not displayed");
        Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessagePasswordDisplayed(alertMessagePassword), "Alert message Password is wrong or is not displayed");
    }

    @Test (dataProvider = "wrongEmailAndCorrectPasswordDataProvider")
    public void negativeIncorrectLoginAndCorrectPasswordLoginTest(String userEmail, String userPassword, String alertMessage, String alertMessageEmail){
        LinkedinLoginPage linkedinLoginPage = new LinkedinLoginPage(driver);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginPage.login(userEmail, userPassword);
        LinkedinLoginSubmitPage linkedinLoginSubmitPage = new LinkedinLoginSubmitPage(driver);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageDisplayed(alertMessage), "Alert message text is wrong or is not displayed");
        Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageEmailDisplayed(alertMessageEmail), "Alert message Email is wrong or is not displayed");
    }

    @Test(dataProvider = "emptyLoginAndOrPasswordDataProvider")
    public void negativeEmptyLoginAndOrPasswordLoginTest(String userEmail, String userPassword){
        LinkedinLoginPage linkedinLoginPage = new LinkedinLoginPage(driver);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Incorrect page is displayed");
    }

    @Test(dataProvider = "injectionInsteadLogin")
    public void negativeJS_HTML_SQLInjectionsInsteadLoginLoginTest(String userEmail, String userPassword, String alertMessage, String alertMessageEmail){
        LinkedinLoginPage linkedinLoginPage = new LinkedinLoginPage(driver);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginPage.login(userEmail, userPassword);
        LinkedinLoginSubmitPage linkedinLoginSubmitPage = new LinkedinLoginSubmitPage(driver);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageDisplayed(alertMessage), "Alert message text is wrong or is not displayed");
        Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageEmailDisplayed(alertMessageEmail), "Alert message Email is wrong or is not displayed");
    }
}
