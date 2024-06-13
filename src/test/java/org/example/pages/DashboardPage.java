package org.example.pages;

import org.example.WebUi;
import org.openqa.selenium.By;
import org.testng.Assert;

public class DashboardPage extends CommonPage {

    public DashboardPage() {
    }

    private By labelTotalTasksNotFinished = By.xpath("((//div[@class='top_stats_wrapper'])[4]//span)[2]");

    public void verifyRedirectToDashboardPage() {
        WebUi.waitForPageLoaded();
        boolean checkMenuDashboard = WebUi.isDisplayed(menuDashboard);
        WebUi.logConsole("Check Menu Dashboard: " + checkMenuDashboard);
        Assert.assertTrue(checkMenuDashboard, "The menu Dashboard page not display.");
    }

    public void openPage(String menuName) {
        WebUi.waitForPageLoaded();
        WebUi.clickElement(By.xpath("//span[normalize-space()='" + menuName + "']"));
    }

    public void verifyTotalOfTasksNotFinished(String totalValue) {
        WebUi.waitForPageLoaded();
        String total = WebUi.getElementText(labelTotalTasksNotFinished);
        WebUi.logConsole("Total Actual: " + total);
        Assert.assertEquals(total, totalValue, "The total of Tasks Not Finished not match.");
    }
}
