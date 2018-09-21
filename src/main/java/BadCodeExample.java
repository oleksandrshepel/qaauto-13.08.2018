import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class BadCodeExample {
   /* public static void main(String[] args){
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String mainURL = "https://www.google.com.ua/";
        String searchTag = "selenium";
        By searchFieldLocator = By.id("lst-ib");
        By searchResultLocator = By.xpath("//div[@class='g']");

        driver.get(mainURL);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(searchFieldLocator));
        element.click();
        element.clear();
        element.sendKeys(searchTag + Keys.ENTER);

        List<WebElement> searchResults = wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(searchResultLocator)));
        System.out.println("Search results count: "+searchResults.size());
        int resultAmountPattern = 10;
        if(searchResults.size() == resultAmountPattern)
            System.out.println("Success! Search results count is correct "+searchResults.size());
                 else System.out.println("Failure! Search results count is incorrect "+searchResults.size());

        for(WebElement searchResult: searchResults) {
            System.out.println("Result: " + searchResult.getText());
            if(searchResult.getText().toLowerCase().contains(searchTag))
                System.out.println("Success! Search tag has been found"+"\n");
            else System.out.println("Failure! Search tag hasn't been found"+"\n");

        }

        driver.close();
        driver.quit();
    }*/
}
