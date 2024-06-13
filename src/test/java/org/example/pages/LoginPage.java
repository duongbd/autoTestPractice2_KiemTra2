package org.example.pages;

import org.example.WebUi;
import org.example.drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class LoginPage extends CommonPage {

    private String url = "https://cms.anhtester.com/login";

    public LoginPage() {
    }

    private By headerPage = By.xpath("//h1[normalize-space()='Login']");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonLogin = By.xpath("//button[normalize-space()='Login']");
    private By errorMessage = By.xpath("//div[contains(@class,'alert alert-danger')]");

    public DashboardPage login(String email, String password) {
        WebUi.openURL(url);
        WebUi.waitForPageLoaded();
        WebUi.setText(inputEmail, email);
        WebUi.setText(inputPassword, password);
        WebUi.clickElement(buttonLogin);

        return new DashboardPage();
    }


    public void verifyLoginSuccess() {
        Assert.assertFalse(WebUi.getCurrentUrl().contains("authentication"), "FAIL. Vẫn đang ở trang Login");
    }

    public void verifyLoginFail(String errorMassageContent) {
        Assert.assertTrue(WebUi.getCurrentUrl().contains("authentication"), "FAIL. Không còn ở trang Login");
        Assert.assertTrue(WebUi.isDisplayed(errorMessage), "Error message NOT displays");
        Assert.assertEquals(WebUi.getElementText(errorMessage), errorMassageContent, "Content of error massage NOT match.");
    }

    public void verifyRedirectToLoginPage() {
        boolean checkHeader = WebUi.isDisplayed(headerPage);
        String textHeader = WebUi.getElementText(headerPage);

        Assert.assertTrue(checkHeader, "The header of Login page not display.");
        Assert.assertEquals(textHeader, "Login", "The header content of Login page not match.");
    }

}
