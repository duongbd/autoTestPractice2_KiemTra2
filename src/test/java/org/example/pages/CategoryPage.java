package org.example.pages;

import org.example.WebUi;
import org.example.drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.Calendar;

public class CategoryPage {
    private static final String url = "https://cms.anhtester.com/admin/categories/create";


    private static final By insertName = By.id("name");
    private static final By chooseParentCategory = By.cssSelector("form > div:nth-of-type(2) select");
    private static final By insertOrderingNumber = By.id("order_level");
    private static final By chooseTypeCategory = By.cssSelector("form > div:nth-of-type(4) select");
    private static final By chooseBannerStep1 = By.cssSelector("form > div:nth-of-type(5) div.form-control");
    private static final By chooseBannerStep2 = By.cssSelector("div.aiz-file-box-wrap:nth-child(1)");
    private static final By chooseBannerStep3 = By.cssSelector("div.modal-footer > button");
    private static final By chooseIconStep1 = By.cssSelector("form > div:nth-of-type(6) div.form-control");
    private static final By chooseIconStep2 = By.cssSelector("div.aiz-file-box-wrap:nth-child(5)");
    private static final By chooseIconStep3 = By.cssSelector("div.modal-footer > button");
    private static final By insertMetaTitle = By.cssSelector("form > div:nth-of-type(7) input");
    private static final By insertMetaDescription = By.cssSelector("form > div:nth-of-type(8) textarea");
    private static final By chooseFilteringAttribute = By.cssSelector("form > div:nth-of-type(9) select");
    private static final By saveCCategory = By.cssSelector("form  button:last-child.btn-primary");
    private static final By chooseSearch = By.id("search");
    private static final By findResult = By.cssSelector("td.footable-first-visible");

    public static CategoryPage openCategoryPage(){
        //add new category
        DriverManager.getDriver().get(url);
        WebUi.waitForPageLoaded(DriverManager.getDriver());
        if (DriverManager.getDriver() instanceof FirefoxDriver) {
            WebUi.sleep(3);
        }
        return new CategoryPage();
    }

    public CategoryPage verifyAddNewCateGory() {
        //insert name
        String categoryName = "nbd-test-" + Calendar.getInstance().getTimeInMillis();
        DriverManager.getDriver().findElement(insertName).sendKeys(categoryName);
        //choose parent category
        WebElement element = DriverManager.getDriver().findElement(chooseParentCategory);
        if (DriverManager.getDriver() instanceof FirefoxDriver) {
            element.sendKeys("Sport shoes");
        } else {
            new Select(element).selectByVisibleText("Sport shoes");
        }
        //insert ordering number
        DriverManager.getDriver().findElement(insertOrderingNumber).sendKeys("5");
        //choose type category
        if (DriverManager.getDriver() instanceof FirefoxDriver) {
            WebUi.sleep(3);
        }
        new Select( DriverManager.getDriver().findElement(chooseTypeCategory)).selectByVisibleText("Physical");
        //choose banner
        WebUi.sleep(3);
        DriverManager.getDriver().findElement(chooseBannerStep1).click();
        WebUi.sleep(5);
        DriverManager.getDriver().findElement(chooseBannerStep2).click();
        DriverManager.getDriver().findElement(chooseBannerStep3).click();
        WebUi.sleep(3);

        //choose icon
        DriverManager.getDriver().findElement(chooseIconStep1).click();
        WebUi.sleep(5);

        DriverManager.getDriver().findElement(chooseIconStep2).click();
        DriverManager.getDriver().findElement(chooseIconStep3).click();
        WebUi.sleep(3);
        //insert meta title
        DriverManager.getDriver().findElement(insertMetaTitle).sendKeys(Calendar.getInstance().toString());
        //insert meta description
        DriverManager.getDriver().findElement(insertMetaDescription).sendKeys(Calendar.getInstance().toString());
        //choose Filtering attribute
        if (DriverManager.getDriver() instanceof FirefoxDriver) {
            DriverManager.getDriver().findElement(chooseFilteringAttribute).sendKeys("Size");
        } else {
            new Select(DriverManager.getDriver().findElement(chooseFilteringAttribute)).selectByVisibleText("Size");
        }
        //save category
        DriverManager.getDriver().findElement(saveCCategory).click();
        WebUi.waitForPageLoaded(DriverManager.getDriver());
        Assert.assertTrue( DriverManager.getDriver().getCurrentUrl().contains("/admin/categories"), "Can not navigate to Category Details page.");

        // verify result
        DriverManager.getDriver().findElement(chooseSearch).sendKeys(categoryName);
        DriverManager.getDriver().findElement(chooseSearch).sendKeys(Keys.RETURN);
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        DriverManager.getDriver().findElements(findResult).forEach(webElement -> {
            if (webElement.getText().equals(categoryName)) {
                Assert.assertTrue(true);
            }
        });
        return this;
    }
}
