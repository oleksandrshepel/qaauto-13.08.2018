package page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class LinkedinSearchPage extends LinkedinBasePage {
    private static Logger LOG = Logger.getLogger(LinkedinSearchPage.class);
    @FindBy(xpath = "//*[@class='search-results__list list-style-none ']")
    private WebElement searchResultContainer;

    @FindBy(xpath = "//*[@class='search-results-container']/descendant:: div[@class = 'search-results-page core-rail']/div/div/ul/li")
    private List<WebElement> searchResults;

    @FindBy(xpath = "//li[@class='search-result search-result__occluded-item ember-view']")
    private List<WebElement> relevantSearchResults;

    /**
     * Constructor for Linkedin Search PageObject
     * @param driver - driver instance from tests.
     */
    public LinkedinSearchPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        //assert Добавляем после PageFactory, иначе не проинициализируется вебєлемент
        assertWebElementIsVisible(searchResultContainer,10);
    }

    /**
     * Defines whether PageObject loaded by checking current url, title and webelement visibility
     * @return - boolean
     */
    public boolean isPageLoaded() {
        return getCurrentUrl().contains("https://www.linkedin.com/search/results")
                && getCurrentTitle().contains("| Search | LinkedIn")
                && searchResultContainer.isDisplayed();
    }

    /**
     * Defines number of searchresults
     * @return - size of an array of search results
     */
    public int getSearchResultsNumber() {
        return relevantSearchResults.size();
    }

    /**
     * Make list of strings with search results
     * @return - list of search results
     */
    public List<String> getSearchResultsList(){
        List<String> searchResultList = new ArrayList<String>();
        relevantSearchResults.forEach(result -> {
            scrollToElement(result);
            searchResultList.add(result.getText());
        });
        LOG.info("Got search results");
        return searchResultList;
    }
}
