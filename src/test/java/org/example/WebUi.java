package org.example;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.example.drivers.DriverManager;
import org.example.helpers.PropertiesHelper;
import org.example.reports.AllureManager;
import org.example.reports.ExtentTestManager;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

@Log4j2
public class WebUi {

    private static int TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("EXPLICIT_WAIT_TIMEOUT"));
    private static int PAGE_LOAD_TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT"));
    private static double STEP_TIME = Double.parseDouble(PropertiesHelper.getValue("STEP_TIME"));
    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCurrentUrl() {
        waitForPageLoaded();
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        LogUtils.info("Get Current URL: " + currentUrl);
        ExtentTestManager.logMessage(Status.PASS, "Get Current URL: " + DriverManager.getDriver().getCurrentUrl());
        return currentUrl;
    }
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(30), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

    //Wait for Javascript to load
    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
        @Override
        public Boolean apply(WebDriver driver) {
            return js.executeScript("return document.readyState").toString().equals("complete");
        }
    };

    //Check JS is Ready
    boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

    //Wait Javascript until it is Ready!
        if (!jsReady) {
        LogUtils.info("Javascript is NOT Ready.");
        //Wait for Javascript to load
        try {
            wait.until(jsLoad);
        } catch (Throwable error) {
            error.printStackTrace();
            Assert.fail("FAILED. Timeout waiting for page load.");
        }
    }
}

    public static void waitForElementVisible(WebDriver driver, By by, int second) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(second), Duration.ofMillis(500));

        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void waitForElementPresent(WebDriver driver, By by, int second) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(second));

        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitForElementClickable(WebDriver driver, By by, int second) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(second));

        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static Boolean checkElementExist(WebDriver driver, By by) {
        List<WebElement> listElement = driver.findElements(by);

        if (!listElement.isEmpty()) {
            log.info("Element: {} existing.", by);
            return true;
        } else {
            log.info("Element: {} NOT exist.", by);
            return false;
        }
    }

    public static Boolean checkElementExist(WebDriver driver, String xpath) {
        List<WebElement> listElement = driver.findElements(By.xpath(xpath));

        if (!listElement.isEmpty()) {
            log.info("Element: {} existing.", xpath);
            return true;
        } else {
            log.info("Element: {} NOT exist.", xpath);
            return false;
        }
    }

    /**
     * Wait for Page loaded
     * Chờ đợi trang tải xong (Javascript tải xong)
     */
    public static void waitForPageLoaded(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver1 -> js.executeScript("return document.readyState").toString().equals("complete");

        //Check JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            System.out.println("Javascript is NOT Ready.");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                log.error(error.getMessage(), error);
                Assert.fail("FAILED. Timeout waiting for page load.");
            }
        }
    }

    public static Boolean isDisplayed(By by) {
        waitForPageLoaded();
        boolean checkDisplay = getWebElement(by).isDisplayed();
        LogUtils.info("Check element display " + by + " \n=======> " + checkDisplay);
        return checkDisplay;
    }

    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }

    @Step("Get text of element {0}")
    public static String getElementText(By by) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        String text = DriverManager.getDriver().findElement(by).getText();
        LogUtils.info("Get text of element " + by);
        LogUtils.info("==> Text: " + getWebElement(by).getText());
        AllureManager.saveTextLog("==> Text: " + getWebElement(by).getText());
        ExtentTestManager.logMessage(Status.PASS, "Get text of element " + by);
        ExtentTestManager.logMessage(Status.INFO, "==> Text: " + getWebElement(by).getText());
        return text;
    }

    public static void waitForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
            LogUtils.info("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    @Step("Click element {0}")
    public static void clickElement(By by) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        DriverManager.getDriver().findElement(by).click();
        LogUtils.info("Click element " + by);
        ExtentTestManager.logMessage(Status.PASS, "Click on element " + by);
    }

    @Step("Set text {1} on element {0}")
    public static void setText(By by, String value) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        DriverManager.getDriver().findElement(by).sendKeys(value);
        LogUtils.info("Set text: " + value + " on element " + by);
        ExtentTestManager.logMessage(Status.PASS, "Set text " + value + " on element " + by);
    }

    public static void logConsole(Object message) {
        LogUtils.info(message);
    }

    @Step("Open Url: {0}")
    public static void openURL(String url) {
        DriverManager.getDriver().get(url);
        sleep(STEP_TIME);
        LogUtils.info("Open Url: " + url);
        ExtentTestManager.logMessage(Status.PASS, "Open URL: " + url);
    }
}
