import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LinkedinHomePage extends LinkedinBasePage {

    @FindBy (xpath = "//li[@id='profile-nav-item']")
    private WebElement profileNavItem;

    @FindBy(xpath = "//*[@id='extended-nav-search']/descendant :: input")
    private WebElement navSearchField;

    @FindBy(xpath = "//*[@class='search-results__list list-style-none']")
    private WebElement searchResultContainer;

    @FindBy(xpath = "//*[@class='search-results-container']/descendant:: div[@class = 'search-results-page core-rail']/div/div/ul/li")
    private List<WebElement> searchResults;

    public LinkedinHomePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageLoaded() {
        return getCurrentUrl().equals("https://www.linkedin.com/feed/")
                && getCurrentTitle().contains("LinkedIn")
                && profileNavItem.isDisplayed();
    }

    public boolean isNavSearchFieldDisplayed() {
        return navSearchField.isDisplayed();
    }

    public void makeSearchRequest(String requestData) {
        navSearchField.click();
        navSearchField.sendKeys(requestData + Keys.ENTER);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isSearchResultContainerDisplayed() {
            return searchResultContainer.isDisplayed();
    }

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

    private boolean compareContainerText(WebElement container, String searchTag){
        return container.getText().toLowerCase().contains(searchTag.toLowerCase());

    }


}
