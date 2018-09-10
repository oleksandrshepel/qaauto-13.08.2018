package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class LinkedinSearchPage extends LinkedinBasePage {
    @FindBy(xpath = "//*[@class='search-results__list list-style-none']")
    private WebElement searchResultContainer;

    @FindBy(xpath = "//*[@class='search-results-container']/descendant:: div[@class = 'search-results-page core-rail']/div/div/ul/li")
    private List<WebElement> searchResults;

    @FindBy(xpath = "//li[contains(@class,'search-result search-result__occluded-item')]")
    //аналогичный xpath "//li[@class[contains(.,'search-result search-result__occluded-item')]]"
    private List<WebElement> relevantSearchResults;

    public LinkedinSearchPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/search/results/index/?keywords=hr&origin=GLOBAL_SEARCH_HEADER")
                && getCurrentTitle().contains("| Поиск | LinkedIn")
                && searchResultContainer.isDisplayed();
    }

    /* Эти два метода весьма громоздкие, лучше подобрать локатор, который будет отсекать блок с рекламой - relevantSearchResults
    public int calculateSearchResults() {
        int result = 0;
        for(int i = 0; i < searchResults.size(); i++){
            WebElement container = searchResults.get(i);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", container);
            String conteinerContent = container.getAttribute("innerHTML");
            if(conteinerContent.contains("class=\"results-list display-flex pb3 ember-view\"")){
                continue;
            }else  result++;
        }
        return result;
    }

    public int calculateRelevantSearchResults(String compareText) {
        int result = 0;
        for(int i = 0; i < searchResults.size(); i++){
            WebElement container = searchResults.get(i);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", container);
            String conteinerContent = container.getAttribute("innerHTML");
            if(conteinerContent.contains("class=\"results-list display-flex pb3 ember-view\"")){
                continue;
            }else{
                if(compareContainerText(container, compareText)) result++;
            }
        }
        return result;
    }
    */
    public int getSearchResultsNumber() {
        return relevantSearchResults.size();
    }

    public List<String> getSearchResultsList(){
        List<String> searchResultList = new ArrayList<String>();
        for(WebElement result: relevantSearchResults){
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", result);
            searchResultList.add(result.getText());
        }
        return searchResultList;
    }

    public int getRelevantSearchResults(String searchTag) {
        int result = 0;
        for(WebElement searchResult: relevantSearchResults){
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", searchResult);
            if(compareContainerText(searchResult, searchTag)) result++;
        }
        return result;
    }

    private boolean compareContainerText(WebElement container, String searchTag){
        return container.getText().toLowerCase().contains(searchTag.toLowerCase());

    }


}
