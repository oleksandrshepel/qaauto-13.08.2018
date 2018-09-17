package test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LinkedinLoginTest extends LinkedinBaseTest{

    /**
     * Test data
     *
     * @return - an array of test data
     */
    @DataProvider
    public Object[][] validDataProvider() {
        return new Object[][]{
                { "limp_slim@ukr.net", "COPYC@2t" },
                { "LIMP_slim@ukr.net", "COPYC@2t" },
                { "  limp_slim@ukr.net  ", "COPYC@2t" }
        };
    }

    /**
     * Test data
     *
     * @return - an array of test data
     */
    @DataProvider
    public Object[][] wrongEmailAndPasswordDataProvider() {
        return new Object[][]{
                { "Txx@xt.net", "Wrong1234", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Hmm, we don't recognize that email. Please try again."},
                { "COPYC@2t", "limp_slim@ukr.net", "There were one or more errors in your submission. Please correct the marked fields below.",
                "Please enter a valid email address."}
        };
    }

    /**
     * Test data
     *
     * @return - an array of test data
     */
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
                { "TTx@xtx.net", "COPYC@2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Hmm, we don't recognize that email. Please try again."},
                { "12 ^#%$^&%^&^%&*&*(*&* ^5876098765", "COPYC2t",
                        "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Be sure to include \"+\" and your country code."},
                { "limp_slimukr.net", "COPYC@2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Please enter a valid email address."},
                { "Maintaining a well-tested codebase is mission-critical, but figuring out where your tests are lacking can be painful. You're already running your tests on a continuous integration server, let it do the heavy lifting. Coveralls works with your CI to sift through coverage data to find gaps you didn't know you had",
                        "COPYC@2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "The text you provided is too long (the maximum length is 128 characters, your text contains 312 characters)."},
                { "r", "COPYC@2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "The text you provided is too short (the minimum length is 3 characters, your text contains 1 character)."}
        };
    }

    /**
     * Test data
     *
     * @return - an array of test data
     */
    @DataProvider
    public Object[][] emptyLoginAndOrPasswordDataProvider(){
        return new Object[][]{
                { "", ""},
                { "", "COPYC@2t"},
                { "limp_slim@ukr.net", ""}
        };
    }

    /**
     * Test data
     *
     * @return - an array of test data
     */
    @DataProvider
    public Object[][] injectionInsteadLogin (){
        return new Object[][]{
                { "<script>alert(123)</script>", "COPYC@2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Please enter a valid email address."},
                { "<form action=\"http://google.com\"><input type=\"submit\"></form>", "COPYC2t", "There were one or more errors in your submission. Please correct the marked fields below.",
                        "Please enter a valid email address."},
                { "SELECT * FROM blog WHERE code LIKE 'a%'", "COPYC@2t", "There were one or more errors in your submission. Please correct the marked fields below.",
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
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "The Login page is not loaded");
        linkedinHomePage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinHomePage.isPageLoaded(), "The Home page is not loaded");
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
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginSubmitPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginSubmitPage.getAlertMessageText(), alertMessage, "Alert message text is wrong or is not displayed");
        Assert.assertEquals(linkedinLoginSubmitPage.getUserEmailAlertText(),alertMessageEmail, "Alert message Email is wrong or is not displayed");
        //Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageDisplayed(alertMessage), "Alert message text is wrong or is not displayed");
        //Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageEmailDisplayed(alertMessageEmail), "Alert message Email is wrong or is not displayed");
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
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginSubmitPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginSubmitPage.getAlertMessageText(), alertMessage, "Alert message text is wrong or is not displayed");
        Assert.assertEquals(linkedinLoginSubmitPage.getUserPasswordAlertText(),alertMessagePassword, "Alert message Password is wrong or is not displayed");

        //Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageDisplayed(alertMessage), "Alert message text is wrong or is not displayed");
        //Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessagePasswordDisplayed(alertMessagePassword), "Alert message Password is wrong or is not displayed");
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
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginSubmitPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginSubmitPage.getAlertMessageText(), alertMessage, "Alert message text is wrong or is not displayed");
        Assert.assertEquals(linkedinLoginSubmitPage.getUserEmailAlertText(),alertMessageEmail, "Alert message Email is wrong or is not displayed");
        //Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageDisplayed(alertMessage), "Alert message text is wrong or is not displayed");
        //Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageEmailDisplayed(alertMessageEmail), "Alert message Email is wrong or is not displayed");
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
    public void negativeEmptyLoginAndOrPasswordLoginTest(String userEmail, String userPassword){
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Incorrect page is displayed");
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
    public void negativeJS_HTML_SQLInjectionsInsteadLoginLoginTest(String userEmail, String userPassword, String alertMessage, String alertMessageEmail){
        //page.LinkedinLoginPage linkedinLoginPage = new page.LinkedinLoginPage(driver);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login page is not loaded");
        linkedinLoginSubmitPage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginSubmitPage.getAlertMessageText(), alertMessage, "Alert message text is wrong or is not displayed");
        Assert.assertEquals(linkedinLoginSubmitPage.getUserEmailAlertText(),alertMessageEmail, "Alert message Email is wrong or is not displayed");
        //Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageDisplayed(alertMessage), "Alert message text is wrong or is not displayed");
        //Assert.assertTrue(linkedinLoginSubmitPage.isAlertMessageEmailDisplayed(alertMessageEmail), "Alert message Email is wrong or is not displayed");
    }
}
