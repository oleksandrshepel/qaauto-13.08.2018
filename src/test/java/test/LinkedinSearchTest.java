package test;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class LinkedinSearchTest extends LinkedinBaseTest{

    public LinkedinSearchTest(){
        LOG = Logger.getLogger(LinkedinLoginTest.class);
    }

    @DataProvider
    public Object[][] searchHomePage() {
        return new Object[][]{
                {"limp_slim@ukr.net", "COPYC@2t", "hr"}
        };
    }
    /**
     * Preconditions:
     * - Open new browser
     * - Navigate to 'Linkedin.com'
     *
     * Scenario:
     * - Open login page
     * - Verify login page is loaded
     * - Login with valid credentials
     * - Verify home page is loaded
     * - Search for 'hr' Search-term
     * - Verify Search page is loaded
     * - Verify 10 results are displayed on the search page
     * - Verify each result item contains search-term
     * Close browser
     */

    @Test(dataProvider = "searchHomePage")
    public void basicSearchTest(String userEmail, String userPassword, String requestData){
        String testName = new Object(){}.getClass().getEnclosingMethod().getName();
        pathToScreenShot += testName;
        LOG.info("Test scenario: "+testName);
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "The Login page is not loaded");
        linkedinHomePage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinHomePage.isPageLoaded(), "The Home page is not loaded");
        Assert.assertTrue(linkedinHomePage.isNavSearchFieldDisplayed(), "The NavSearchField is not displayed");
        linkedinSearchPage = linkedinHomePage.makeSearchRequest(requestData);
        Assert.assertTrue(linkedinSearchPage.isPageLoaded(), "The Search page is not loaded");
        Assert.assertEquals(linkedinSearchPage.getSearchResultsNumber(), 4, "Number of search results is incorrect");
        linkedinSearchPage.getSearchResultsList().forEach(result ->
                Assert.assertTrue(result.toLowerCase().contains(requestData),
                        "Search term "+requestData+" is not found in: "+result) );
        LOG.info("Test scenario is completed");
        setIsTestPass(true);
    }


}
