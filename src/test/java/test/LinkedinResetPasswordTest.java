package test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class LinkedinResetPasswordTest extends LinkedinBaseTest{

    @DataProvider
    public Object[][] changePassword() {
        return new Object[][]{
                {"qaauto13082018@gmail.com", "COPYC@2t7", "COPYC@2t8",
                "There were one or more errors in your submission. Please correct the marked fields below.",
                "Hmm, that's not the right password. Please try again or request a new one."}
        };
    }

    /**
     * Reset user's password
     *
     * Preconditions:
     * - Open new browser
     * - Navigate to 'Linkedin.com'
     *
     * Scenario:
     *- Click Forgot-password link
     *- Verify Linkedin Password Reset Page is loaded
     *- Type email
     *- Click Find account button
     *- Verify Linkedin Rquest Password Reset Submit Page is loaded
     *- go to your email
     *- find email from security-noreply@linkedin.com
     *- open the letter
     *- click link inside the letter
     *- verify Linkedin Retype Password Page is loaded
     *- type new password
     *- retype new password
     *- check Require All devices checkbox
     *- click button Submit
     *- Verify Linkedin Password Reset Submit Page is loaded
     *- click button Go Home
     *- Verify Home page is loaded
     *- Log out
     *- Verify no longer user can log in with old password
     *- login with new password
     *- Verify Home page is loaded
     *- Verify "your password was successfully reset" email has been sent
     * Close browser
     */
    @Ignore
    @Test(dataProvider = "changePassword")
    public void resetPasswordTest(String userEmail, String password, String newPassword,
                                  String alertMessage, String alertMessagePassword){
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "The Login page is not loaded");
        linkedinPasswordResetPage = linkedinLoginPage.clickForgotPasswordLink();
        Assert.assertTrue(linkedinPasswordResetPage.isPageLoaded(),"Password Reset Page is not loaded");
        linkedinPasswordResetPage.enterUserEmail(userEmail);
        linkedinRequestPasswordResetSubmitPage = linkedinPasswordResetPage.clickFindAccount();
        Assert.assertTrue(linkedinRequestPasswordResetSubmitPage.isPageLoaded(),"Request Password Reset Submit Page is not loaded");
        linkedinRetypePasswordPage = linkedinRequestPasswordResetSubmitPage.navigateToLinkFromEmail();
        Assert.assertTrue(linkedinRetypePasswordPage.isPageLoaded(), "Retype Password Page is not loaded");
        linkedinRetypePasswordPage.enterNewPassword(newPassword);
        linkedinRetypePasswordPage.enterRetypeNewPassword(newPassword);
        linkedinRetypePasswordPage.checkRequireAllDevices();
        linkedinPasswordResetSubmitPage = linkedinRetypePasswordPage.clickSubmitButton();
        Assert.assertTrue(linkedinPasswordResetSubmitPage.isPageLoaded(),"Password Reset Submit Page is not loaded");
        linkedinHomePage = linkedinPasswordResetSubmitPage.clickGoHomepage();
        Assert.assertTrue(linkedinHomePage.isPageLoaded(), "Home page is not loaded");
        linkedinHomePage.clickProfileNavButton();
        linkedinLoginPage = linkedinHomePage.selectProfileNavDropdownItem("Sign out");
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "Login Page is not loaded");
        linkedinLoginSubmitPage = linkedinLoginPage.login(userEmail, password);
        Assert.assertTrue(linkedinLoginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded");
        Assert.assertEquals(linkedinLoginSubmitPage.getHeaderContent(), alertMessage, "Alert message text is wrong or is not displayed");
        Assert.assertEquals(linkedinLoginSubmitPage.getUserPasswordAlertText(),alertMessagePassword, "Alert message Password is wrong or is not displayed");
        linkedinHomePage = linkedinLoginPage.login(userEmail, newPassword);
        Assert.assertTrue(linkedinHomePage.isPageLoaded(), "Home page is not loaded");

    }
}
