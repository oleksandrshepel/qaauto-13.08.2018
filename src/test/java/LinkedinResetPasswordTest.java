import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static java.lang.Thread.sleep;

public class LinkedinResetPasswordTest extends LinkedinBaseTest{

    /*
    - Click Forgot-password link
    - Verify Linkedin Password Reset Page is loaded
    - Type email
    - Click Find account button
    - Verify Linkedin Rquest Password Reset Submit Page is loaded
    - go to your email
    - find email from security-noreply@linkedin.com
    - open the letter
    - click link inside the letter
    - verify Linkedin Retype Password Page is loaded
    - type new password
    - retype new password
    - check Require All devices checkbox
    - click button Submit
    - Verify Linkedin Password Reset Submit Page is loaded
    - click button Go Home
    - Verify Home page is loaded
    - Log out
    - Verify no longer user can log in with old password
    - login with new password
    - Verify Home page is loaded
    - Verify "your password was successfully reset" email has been sent
     */
    @DataProvider
    public Object[][] changePassword() {
        return new Object[][]{
                {"qaauto@ukr.net", "COPYC@2t6", "COPYC@2t7",
                "There were one or more errors in your submission. Please correct the marked fields below.",
                "Hmm, that's not the right password. Please try again or request a new one."}
        };
    }

    @Test(dataProvider = "changePassword")
    public void resetPasswordTest(String userEmail, String password, String newPassword,
                                  String alertMessage, String alertMessagePassword){
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "The Login page is not loaded");
        linkedinPasswordResetPage = linkedinLoginPage.clickForgotPasswordLink();
        Assert.assertTrue(linkedinPasswordResetPage.isPageLoaded(),"Password Reset Page is not loaded");
        linkedinPasswordResetPage.enterUserEmail(userEmail);
        linkedinRequestPasswordResetSubmitPage = linkedinPasswordResetPage.clickFindAccount();
        Assert.assertTrue(linkedinRequestPasswordResetSubmitPage.isPageLoaded(),"Request Password Reset Submit Page is not loaded");
        //try {
        //    sleep(120000);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        //**************************************************************************************************************
        driver.get("https://www.ukr.net/");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.name("Login")).sendKeys(userEmail);
        driver.findElement(By.name("Password")).sendKeys("COPYC@2t");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        //wait.until(ExpectedConditions.elementToBeClickable(
        //       driver.findElement(By.xpath("//section [@id='login-block']/descendant:: a[contains(@href,'https://mail.ukr.net')]")))).click();
        driver.get("https://mail.ukr.net/desktop#msglist/f0/p0");
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> letters = driver.findElements(By.xpath("//a[contains(text(),\"here's the link to reset your password\")]"));
        if(letters.size()>=1)
           for(WebElement letter: letters)
               if(letter.getText().contains("here's the link to reset your password")){
                   letter.click();
                   break;
               }
        String link = wait.until(ExpectedConditions.elementToBeClickable(
                driver.findElement(By.xpath("//*[@id=\"readmsg\"]/div[2]/section/div[2]/div[1]/span/span[2]/table/tbody/tr/td/center/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[4]/td/p/a"))))
                .getAttribute("href");
        driver.get(link);
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //**************************************************************************************************************
        linkedinRetypePasswordPage = new LinkedinRetypePasswordPage(driver);
        Assert.assertTrue(linkedinRetypePasswordPage.isPageLoaded(), "Retype Password Page is not loaded");
        linkedinRetypePasswordPage.enterNewPassword(newPassword);
        linkedinRetypePasswordPage.enterRetypeNewPassword(newPassword);
        linkedinRetypePasswordPage.checkRequireAllDevices();
        linkedinPasswordResetSubmitPage = linkedinRetypePasswordPage.clickSubmitButton();
        Assert.assertTrue(linkedinPasswordResetSubmitPage.isPageLoaded(),"Password Reset Submit Page is not loaded");
        linkedinHomePage = linkedinPasswordResetSubmitPage.clickGoHomepage();
        Assert.assertTrue(linkedinHomePage.isPageLoaded(), "Home page is not loaded");
        linkedinHomePage.clickProfileNavButton();
        linkedinLogoutPage = linkedinHomePage.selectProfileNavDropdownItem("Sign out");
        Assert.assertTrue(linkedinLogoutPage.isPageLoaded(), "Logout Page is not loaded");
        //далее тест не идёт, поскольку форма для xpath недоступна
        //linkedinLogoutPage.login(userEmail, password);
        //Assert.assertEquals(linkedinLogoutPage.getAlertMessageText(), alertMessage, "Alert message text is wrong or is not displayed");
        //Assert.assertEquals(linkedinLogoutPage.getUserPasswordAlertText(),alertMessagePassword, "Alert message Password is wrong or is not displayed");
        //linkedinHomePage = linkedinLogoutPage.login(userEmail, newPassword);
        //Assert.assertTrue(linkedinHomePage.isPageLoaded(), "Home page is not loaded");

    }
}
