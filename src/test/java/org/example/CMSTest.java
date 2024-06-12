package org.example;


import org.example.common.BaseTest;
import org.example.drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Calendar;

public class CMSTest extends BaseTest {
//    @Test
    public void loginCMS() {
        DriverManager.getDriver().get("https://cms.anhtester.com/login");
        DriverManager.getDriver().findElement(By.xpath("//input[@id='email']")).sendKeys("admin@example.com");
        DriverManager.getDriver().findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
        DriverManager.getDriver().findElement(By.xpath("//button[normalize-space()='Login']")).click();
    }

    @Test
    public void addNewCateGory() throws InterruptedException {
        loginCMS();
        //add new category
        DriverManager.getDriver().get("https://cms.anhtester.com/admin/categories/create");
        WebUi.waitForPageLoaded(DriverManager.getDriver());
        if (DriverManager.getDriver() instanceof FirefoxDriver) {
            WebUi.sleep(3);
        }
        //insert name
        String categoryName = "nbd-test-" + Calendar.getInstance().getTimeInMillis();
        DriverManager.getDriver().findElement(By.id("name")).sendKeys(categoryName);
        //choose parent category
        WebElement element = DriverManager.getDriver().findElement(By.cssSelector("form > div:nth-of-type(2) select"));
        if (DriverManager.getDriver() instanceof FirefoxDriver) {
            element.sendKeys("Sport shoes");
        } else {
            new Select(element).selectByVisibleText("Sport shoes");
        }
        //insert ordering number
        DriverManager.getDriver().findElement(By.id("order_level")).sendKeys("5");
        //choose type category
        if (DriverManager.getDriver() instanceof FirefoxDriver) {
            WebUi.sleep(3);
        }
        new Select( DriverManager.getDriver().findElement(By.cssSelector("form > div:nth-of-type(4) select"))).selectByVisibleText("Physical");
        //choose banner
        WebUi.sleep(3);
        DriverManager.getDriver().findElement(By.cssSelector("form > div:nth-of-type(5) div.form-control")).click();
        WebUi.sleep(5);
        DriverManager.getDriver().findElement(By.cssSelector("div.aiz-file-box-wrap:nth-child(1)")).click();
        DriverManager.getDriver().findElement(By.cssSelector("div.modal-footer > button")).click();
        WebUi.sleep(3);

        //choose icon
        DriverManager.getDriver().findElement(By.cssSelector("form > div:nth-of-type(6) div.form-control")).click();
        WebUi.sleep(5);

        DriverManager.getDriver().findElement(By.cssSelector("div.aiz-file-box-wrap:nth-child(5)")).click();
        DriverManager.getDriver().findElement(By.cssSelector("div.modal-footer > button")).click();
        WebUi.sleep(3);
        //insert meta title
        DriverManager.getDriver().findElement(By.cssSelector("form > div:nth-of-type(7) input")).sendKeys(Calendar.getInstance().toString());
        //insert meta description
        DriverManager.getDriver().findElement(By.cssSelector("form > div:nth-of-type(8) textarea")).sendKeys(Calendar.getInstance().toString());
        //choose Filtering attribute
        if (DriverManager.getDriver() instanceof FirefoxDriver) {
            DriverManager.getDriver().findElement(By.cssSelector("form > div:nth-of-type(9) select")).sendKeys("Size");
        } else {
            new Select(DriverManager.getDriver().findElement(By.cssSelector("form > div:nth-of-type(9) select"))).selectByVisibleText("Size");
        }
        //save category
        DriverManager.getDriver().findElement(By.cssSelector("form  button:last-child.btn-primary")).click();
        WebUi.waitForPageLoaded(DriverManager.getDriver());
        Assert.assertTrue( DriverManager.getDriver().getCurrentUrl().contains("/admin/categories"), "Can not navigate to Category Details page.");

        // verify result
        DriverManager.getDriver().findElement(By.id("search")).sendKeys(categoryName);
        DriverManager.getDriver().findElement(By.id("search")).sendKeys(Keys.RETURN);
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        DriverManager.getDriver().findElements(By.cssSelector("td.footable-first-visible")).forEach(webElement -> {
            if (webElement.getText().equals(categoryName)) {
                Assert.assertTrue(true);
            }
        });
    }
}
