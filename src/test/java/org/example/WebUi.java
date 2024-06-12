package org.example;

import lombok.extern.log4j.Log4j2;
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
    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
}
