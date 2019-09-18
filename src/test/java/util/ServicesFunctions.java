package util;

import exceptions.WaitingTimeoutValidationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ServicesFunctions {
    private static int TIME_OUT_SECONDS = Parameters.timeoutWaitDialogs;
    private static String PATH_TO_SCREENSHOT = Parameters.screenShotDir;
    private static final String JS_JQUERY_DEFINED = "return typeof jQuery !== 'undefined' && jQuery != null;";
    private static final String JS_PRIMEFACES_DEFINED = "return typeof PrimeFaces !== 'undefined' && PrimeFaces != null;";
    private static final String JS_JQUERY_ACTIVE = "return jQuery.active != 0;";
    private static final String JS_PRIMEFACES_QUEUE_NOT_EMPTY = "return !PrimeFaces.ajax.Queue.isEmpty();";
    private static final String GUI_WAIT_PANEL_ID = "uiStateWaitBlockPanelId";
    private static Logger LOG = Logger.getLogger(ServicesFunctions.class);

    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        waitForJQueryAndPrimeFaces(driver);
    }

    /**
     * Метод который влючает ожидание выполнения JQUERY/PRIMEFACES/JAVASCRIPT
     *
     * @param driver
     */
    public static void waitForJQueryAndPrimeFaces(WebDriver driver) {
        if (TIME_OUT_SECONDS != Parameters.timeoutWaitDialogs){
            TIME_OUT_SECONDS = Parameters.timeoutWaitDialogs;
            System.out.println("TIME_OUT_SECONDS: " + TIME_OUT_SECONDS);
        }
        waitForJQueryAndPrimeFaces(driver, TIME_OUT_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * Метод который влючает ожидание выполнения JQUERY/PRIMEFACES/JAVASCRIPT
     *
     * @param driver
     */
    public static void waitForJQueryAndPrimeFaces(WebDriver driver, int duration, TimeUnit timeUnit) {
        try {
            new MyFluentWait(driver, "ожидание выполнения JQUERY/PRIMEFACES/JAVASCRIPT")
                   .withTimeout(duration, timeUnit)
                    .pollingEvery(300, TimeUnit.MILLISECONDS)
                    .until(new Function<WebDriver, Boolean>() {
                        @Override
                        public Boolean apply(WebDriver input) {
                            boolean ajax = false;
                            boolean jQueryDefined = executeBooleanJavascript(input, JS_JQUERY_DEFINED);
                            boolean primeFacesDefined = executeBooleanJavascript(input, JS_PRIMEFACES_DEFINED);

                            if (jQueryDefined) {
                                // jQuery is still active
                                ajax |= executeBooleanJavascript(input, JS_JQUERY_ACTIVE);
                            }
                            if (primeFacesDefined) {
                                // PrimeFaces queue isn't empty
                                ajax |= executeBooleanJavascript(input, JS_PRIMEFACES_QUEUE_NOT_EMPTY);
                            }

                            // continue if all ajax request are processed
                            return !ajax;
                        }
                    });
        } catch (Exception e) {
            if (e instanceof TimeoutException) {
                handleTimeoutException(driver, WaitingTimeoutValidationException.JS_JQUERY_PRIMEFACES_WAIT_FAILED,
                        (TimeoutException) e);
            }
        }

        try {
            new MyFluentWait(driver, "ожидание пропадания панели uiStateWait")
                    /* new FluentWait<WebDriver>(driver) */.withTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                    .pollingEvery(300, TimeUnit.MILLISECONDS)
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.id(GUI_WAIT_PANEL_ID)));
        } catch (Exception e) {
            if (e instanceof TimeoutException) {
                handleTimeoutException(driver, WaitingTimeoutValidationException.GUI_BLOCK_PANEL_HIDE_WAIT_FAILED,
                        (TimeoutException) e);
            }
        }
    }

    /**
     * Taking screenshot into .//target// + pathToScreenShot
     * @param driver
     */
    public static void screenShot(WebDriver driver, String pathToScreenShot){ //Метод который делает скрин
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); //снимает скрин
        try {
            FileUtils.copyFile(scrFile, new File(pathToScreenShot));
            LOG.info("ScreenShot: " + pathToScreenShot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String takeScreenshot(WebDriver driver) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = PATH_TO_SCREENSHOT + "/screenshot_" + RandomStringUtils.randomAlphanumeric(6) + ".png";
        try {
            FileUtils.copyFile(scrFile, new File(fileName));
        } catch (IOException e1) {
            LOG.warn("Не могу записать Screenshot");
        }
        return Parameters.screenUrlPrefix + fileName;
    }

    public static void handleTimeoutException(WebDriver driver, String message, TimeoutException e) {
        String screenshotUrl = takeScreenshot(driver);
        System.out.println(e);
        throw new WaitingTimeoutValidationException(e,
                message + "&lt;a href=" + screenshotUrl + " &gt;screen&lt;/a&gt; ", screenshotUrl);
    }

    public static void ThreadSleep(long milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean executeBooleanJavascript(WebDriver input, String javascript) {
        return (Boolean) ((JavascriptExecutor) input).executeScript(javascript);
    }

}
