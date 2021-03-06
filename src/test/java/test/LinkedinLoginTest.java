package test;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LinkedinLoginTest extends LinkedinBaseTest{

    public LinkedinLoginTest(){
        LOG = Logger.getLogger(LinkedinLoginTest.class);
    }

    /**
     * Test data
     * @return - an array of test data
     */
    @DataProvider //можно давать имя (name = "Authentication"), тогда в тесте запись будет выглядеть @Test(dataProvider = "Authentication")
    public Object[][] validDataProvider() {
        return new Object[][]{
                { "limp_slim@ukr.net", "COPYC@2t" },
                { "LIMP_slim@ukr.net", "COPYC@2t" },
                { "  limp_slim@ukr.net  ", "COPYC@2t" }
        };
    }

    /**
     * Test data
     * @return - an array of test data
     */
    @DataProvider
    public Object[][] wrongEmailAndPasswordDataProvider() {
        return new Object[][]{
                { "Txx@xt.net", "Wrong1234", "Welcome Back", "Hmm, we don't recognize that email. Please try again."},
                { "COPYC@2t", "limp_slim@ukr.net", "Welcome Back", "Hmm, we don't recognize that email. Please try again."}
        };
    }

    /**
     * Test data
     * @return - an array of test data
     */
    @DataProvider
    public Object[][] correctEmailAndWrongPasswordDataProvider() {
        return new Object[][]{
                { "limp_slim@ukr.net", "Wrong12345", "Welcome Back",
                        "Hmm, that's not the right password. Please try again or request a new one."}
        };
    }

    /**
     * Test data
     * @return - an array of test data
     */
    @DataProvider
    public Object[][] invalidPasswordRangeDataProvider() {
        return new Object[][]{
                { "limp_slim@ukr.net", "123", "The password you provided must have at least 6 characters"},
                { "limp_slim@ukr.net",
                        "Maintaining a well-tested codebase is mission-critical, but figuring out where your tests are lacking can be painful. You're already running your tests on a continuous integration server, let it do the heavy lifting. Coveralls works with your CI to sift through coverage data to find gaps you didn't know you hadtuyewqtruwieuiri734y687368574yrfudsfgdjhfdisaufhyirwe47y857346t87eryfgdsadhfuisaugferu7y8t7e",
                        "The password you provided must have at most 400 characters"}
        };
    }

    @DataProvider
    public Object[][] wrongEmailAndCorrectPasswordDataProvider() {
        return new Object[][]{
                { "TTx@xtx.net", "COPYC@2t", "Welcome Back",
                        "Hmm, we don't recognize that email. Please try again."},
                { "12 ^#%$^&%^&^%&*&*(*&* ^5876098765", "COPYC2t",
                        "Welcome Back",
                        "Be sure to include \"+\" and your country code."},
                { "limp_slimukr.net", "COPYC@2t", "Welcome Back",
                        "Please enter a valid email address."}
        };
    }

    @DataProvider
    public Object[][] invalidEmailRangeDataProvider() {
        return new Object[][]{
                { "Maintaining a well-tested codebase is mission-critical, but figuring out where your tests are lacking can be painful. You're already running your tests on a continuous integration server, let it do the heavy lifting. Coveralls works with your CI to sift through coverage data to find gaps you didn't know you had",
                        "COPYC@2t", "Email or phone number must be between 3 to 128 characters"},
                { "r", "COPYC@2t", "Email or phone number must be between 3 to 128 characters"}
        };
    }

    /**
     * Test data
     * @return - an array of test data
     */
    @DataProvider
    public Object[][] emptyLoginAndOrPasswordDataProvider(){
        return new Object[][]{
                { "", "", "Please enter an email address or phone number"},
                { "", "COPYC@2t", "Please enter an email address or phone number"},
                { "limp_slim@ukr.net", "", "Please enter a password"}
        };
    }

    /**
     * Test data
     * @return - an array of test data
     */
    @DataProvider
    public Object[][] injectionInsteadLogin (){
        return new Object[][]{
                { "<script>alert(123)</script>", "COPYC@2t", "Welcome Back", "Please enter a valid email address."},
                { "<form action=\"http://google.com\"><input type=\"submit\"></form>", "COPYC2t", "Welcome Back",
                        "Please enter a valid email address."},
                { "SELECT * FROM blog WHERE code LIKE 'a%'", "COPYC@2t", "Welcome Back",
                        "Please enter a valid email address."},
        };
    }


    /**
     * Verify successful user Login
     *
     * Preconditions:
     *  - Open new browser
     *  - Navigate to 'Linkedin.com'
     *
     * Scenario:
     *  - Verify that login page is loaded
     *  - Enter user e-mail
     *  - Enter user password
     *  - Click 'Sign in' button
     *  - Verify Home page is loaded
     *  - close browser
     */
    @Test(dataProvider = "validDataProvider")
    public void successfulLoginTest(String userEmail, String userPassword){
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        pathToScreenShot += testName;
        LOG.info("Test scenario: "+testName);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "The Login page is not loaded");
        linkedinHomePage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinHomePage.isPageLoaded(), "The Home page is not loaded");
        LOG.info("Test scenario is completed");
        setIsTestPass(true);
    }

    /**
     * Verify impossibility of being logged in  using incorrect credentials
     *
     * Preconditions:
     * - Open new browser
     * - Navigate to 'Linkedin.com'
     *
     * Scenario:
     * - Verify that login page is loaded
     * - Enter user invalid e-mail
     * - Enter user invalid password
     * - Click 'Sign in' button
     * - Verify Login submit page is loaded
     * - Verify alert messages
     * - Close browser
     *
     */
    @Test(dataProvider = "wrongEmailAndPasswordDataProvider")
    public void negativeWrongEmailAndPasswordLoginTest(String userEmail, String userPassword, String alertMessage, String alertMessageEmail){
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        pathToScreenShot += testName;
        LOG.info("Test scenario: "+testName);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginSubmitPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginSubmitPage.getHeaderContent(), alertMessage, "Alert message text is wrong or is not displayed");
        Assert.assertEquals(linkedinLoginSubmitPage.getUserEmailAlertText(),alertMessageEmail, "Alert message Email is wrong or is not displayed");
        LOG.info("Test scenario is completed");

        setIsTestPass(true);
    }

    /**
     * Verify impossibility of being logged in using incorrect password
     *
     * Preconditions:
     * - Open new browser
     * - Navigate to 'Linkedin.com'
     *
     * Scenario:
     * - Verify that login page is loaded
     * - Enter user valid e-mail
     * - Enter user invalid password
     * - Click 'Sign in' button
     * - Verify Login submit page is loaded
     * - Verify alert messages
     * - Close browser
     *
     */
    @Test(dataProvider = "correctEmailAndWrongPasswordDataProvider")
    public void negativeCorrectLoginAndWrongPasswordLoginTest(String userEmail, String userPassword, String alertMessage, String alertMessagePassword){
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        pathToScreenShot += testName;
        LOG.info("Test scenario: "+testName);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginSubmitPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginSubmitPage.getHeaderContent(), alertMessage, "Alert message text is wrong or is not displayed");
        Assert.assertEquals(linkedinLoginSubmitPage.getUserPasswordAlertText(),alertMessagePassword, "Alert message Password is wrong or is not displayed");
        LOG.info("Test scenario is completed");
        setIsTestPass(true);
    }

    /**
     * Verify impossibility of being logged in using incorrect password
     *
     * Preconditions:
     * - Open new browser
     * - Navigate to 'Linkedin.com'
     *
     * Scenario:
     * - Verify that login page is loaded
     * - Enter user valid e-mail
     * - Enter user invalid password
     * - Click 'Sign in' button
     * - Verify Login submit page is loaded
     * - Verify alert messages
     * - Close browser
     *
     */
    @Test(dataProvider = "invalidPasswordRangeDataProvider")
    public void negativeInvalidPasswordRangeLoginTest(String userEmail, String userPassword, String alertMessagePassword){
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        pathToScreenShot += testName;
        LOG.info("Test scenario: "+testName);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertEquals(linkedinLoginPage.getUserLoginPasswordAlertText(),alertMessagePassword, "Alert message Password is wrong or is not displayed");
        LOG.info("Test scenario is completed");
        setIsTestPass(true);
    }

    /**
     * Verify impossibility of being logged in using incorrect login
     *
     * Preconditions:
     * - Open new browser
     * - Navigate to 'Linkedin.com'
     *
     * Scenario:
     * - Verify that login page is loaded
     * - Enter user invalid e-mail
     * - Enter user valid password
     * - Click 'Sign in' button
     * - Verify Login submit page is loaded
     * - Verify alert messages
     * - Close browser
     *
     */
    @Test (dataProvider = "wrongEmailAndCorrectPasswordDataProvider")
    public void negativeIncorrectLoginAndCorrectPasswordLoginTest(String userEmail, String userPassword, String alertMessage, String alertMessageEmail){
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        pathToScreenShot += testName;
        LOG.info("Test scenario: "+testName);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginSubmitPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginSubmitPage.getHeaderContent(), alertMessage, "Alert message text is wrong or is not displayed");
        Assert.assertEquals(linkedinLoginSubmitPage.getUserEmailAlertText(),alertMessageEmail, "Alert message Email is wrong or is not displayed");
        LOG.info("Test scenario is completed");
        setIsTestPass(true);
    }

    /**
     * Verify impossibility of being logged in using incorrect login
     *
     * Preconditions:
     * - Open new browser
     * - Navigate to 'Linkedin.com'
     *
     * Scenario:
     * - Verify that login page is loaded
     * - Enter user invalid e-mail
     * - Enter user valid password
     * - Click 'Sign in' button
     * - Verify Login page is loaded
     * - Verify alert messages
     * - Close browser
     *
     */
    @Test (dataProvider = "invalidEmailRangeDataProvider")
    public void negativeInvalidLoginRangeTest(String userEmail, String userPassword, String alertMessage){
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        pathToScreenShot += testName;
        LOG.info("Test scenario: "+testName);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginPage.getUserLoginPasswordAlertText(),alertMessage, "Alert message Email is wrong or is not displayed");
        LOG.info("Test scenario is completed");
        setIsTestPass(true);
    }

    /**
     * Verify impossibility of being logged in using empty credentials
     *
     * Preconditions:
     * - Open new browser
     * - Navigate to 'Linkedin.com'
     *
     * Scenario:
     * - Verify that login page is loaded
     * - Click 'Sign in' button having login and/or password fields empty
     * - Verify current page hasn't changed
     * - Close browser
     *
     */
    @Test(dataProvider = "emptyLoginAndOrPasswordDataProvider")
    public void negativeEmptyLoginAndOrPasswordLoginTest(String userEmail, String userPassword, String alertMessage){
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        pathToScreenShot += testName;
        LOG.info("Test scenario: "+testName);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Incorrect page is displayed");
        Assert.assertEquals(linkedinLoginPage.getUserLoginPasswordAlertText(),alertMessage, "Alert message Email is wrong or is not displayed");
        LOG.info("Test scenario is completed");
        setIsTestPass(true);
    }

    /**
     * Verify JS/SQL/HTML scripts are properly handled
     *
     * Preconditions:
     * - Open new browser
     * - Navigate to 'Linkedin.com'
     *
     * Scenario:
     * - Verify that login page is loaded
     * - Enter JS/SQL/HTML script in users's email field
     * - Enter user valid password
     * - Click 'Sign in' button
     * - Verify Login submit page is loaded
     * - Verify alert messages
     * - Close browser
     *
     */
    @Test(dataProvider = "injectionInsteadLogin")
    public void negativeJS_HTML_SQLInjectionsInsteadLoginLoginTest(String userEmail, String userPassword, String headerContent, String alertMessageEmail){
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        pathToScreenShot += testName;
        LOG.info("Test scenario: "+testName);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginSubmitPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginSubmitPage.getHeaderContent(), headerContent, "A Header content text is wrong or is not displayed");
        Assert.assertEquals(linkedinLoginSubmitPage.getUserEmailAlertText(),alertMessageEmail, "Alert message Email is wrong or is not displayed");
        LOG.info("Test scenario is completed");
        setIsTestPass(true);
    }
}
