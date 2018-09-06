import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class LinkedinSearchTest extends LinkedinBaseTest{


    @DataProvider
    public Object[][] searchHomePage() {
        return new Object[][]{
                {"limp_slim@ukr.net", "COPYC2t", "hr"}
        };
    }
    /*
    - Open login page
    - Verify login page is loaded
    - Login with valid credentials
    - Verify home page is loaded
    - Search for 'hr' Search-term
    - Verify Search page is loaded
    - Verify 10 results are displayed on the search page
    - Verify each result item contains search-term
     */

    @Test(dataProvider = "searchHomePage")
    public void basicSearchTest(String userEmail, String userPassword, String requestData){
        Assert.assertTrue(linkedinLoginPage.isPageLoaded(), "The Login page is not loaded");
        linkedinHomePage = linkedinLoginPage.login(userEmail, userPassword);
        Assert.assertTrue(linkedinHomePage.isPageLoaded(), "The Home page is not loaded");
        Assert.assertTrue(linkedinHomePage.isNavSearchFieldDisplayed(), "The NavSearchField is not displayed");
        linkedinSearchPage = linkedinHomePage.makeSearchRequest(requestData);
        Assert.assertTrue(linkedinSearchPage.isPageLoaded(), "The Search page is not loaded");
        Assert.assertEquals(linkedinSearchPage.getSearchResultsNumber(), 10, "Number of search results is incorrect");
        //Assert.assertEquals(linkedinSearchPage.getRelevantSearchResults(requestData), linkedinSearchPage.getSearchResultsNumber(), "There are some irrelevant results");
        List<String> searchResultsList = linkedinSearchPage.getSearchResultsList();
        for(String result: searchResultsList){
            Assert.assertTrue(result.toLowerCase().contains(requestData),
                    "Search term "+requestData+" is not found in: "+result);
        }
    }


}
