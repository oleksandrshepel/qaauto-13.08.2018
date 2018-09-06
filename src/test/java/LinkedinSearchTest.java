import org.testng.Assert;
import org.testng.annotations.*;

public class LinkedinSearchTest extends LinkedinBaseTest{


    @DataProvider
    public Object[][] searchHomePage() {
        return new Object[][]{
                {"limp_slim@ukr.net", "COPYC2t", "hr"}
        };
    }


    @Test(dataProvider = "searchHomePage")
    public void searchTestHomePage(String userEmail, String userPassword, String requestData){
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "The Login page is not loaded");
        linkedinHomePage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinHomePage.isPageLoaded(), "The Home page is not loaded");
        Assert.assertTrue(linkedinHomePage.isNavSearchFieldDisplayed(), "The NavSearchField is not displayed");
        linkedinHomePage.makeSearchRequest(requestData);
        Assert.assertTrue(linkedinHomePage.isSearchResultContainerDisplayed(), "SearchForm is not displayed");
        Assert.assertEquals(linkedinHomePage.calculateSearchResults(), 10, "Number of search results is incorrect");
        Assert.assertEquals(linkedinHomePage.calculateRelevantSearchResults(requestData), linkedinHomePage.calculateSearchResults(), "There are some irrelevant results");

    }


}
