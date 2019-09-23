package util;

import exceptions.WaitingTimeoutValidationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public class MyFluentWait extends FluentWait<WebDriver> {
    String waitDescription;
    WebDriver driver;

    public MyFluentWait(WebDriver driver, String waitDescription) {
        super(driver);
        this.waitDescription = waitDescription;
        this.driver = driver;
    }

    @Override
    protected RuntimeException timeoutException(String message, Throwable lastException) {
        String screenshotUrl = ServicesFunctions.takeScreenshot(driver);
        return new WaitingTimeoutValidationException(lastException, waitDescription + "&lt;a href="+screenshotUrl+" &gt;screen&lt;/a&gt; ", screenshotUrl);
    }
}
