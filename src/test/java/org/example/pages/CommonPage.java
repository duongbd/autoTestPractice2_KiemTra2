package org.example.pages;

import org.example.WebUi;
import org.openqa.selenium.By;

public class CommonPage {

    public CommonPage() {
    }

    public By inputSearch = By.xpath("//input[@id='search_input']");
    public By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");
    public By menuCustomer = By.xpath("//span[normalize-space()='Customers']");
    public By menuProject = By.xpath("//span[normalize-space()='Projects']");
    public By dropdownProfile = By.xpath("//a[contains(@class,'dropdown-toggle profile')]");
    public By itemLogout = By.xpath("//a[contains(@class,'dropdown-toggle profile')]/following-sibling::ul//a[normalize-space()='Logout']");


    public void openProjectPage() {
        WebUi.clickElement(menuProject);
        WebUi.waitForPageLoaded();
    }

    public LoginPage logout() {
        WebUi.clickElement(dropdownProfile);
        WebUi.sleep(1);
        WebUi.clickElement(itemLogout);
        WebUi.waitForPageLoaded();

        return new LoginPage();
    }

    public void searchCommon(String text){
        WebUi.setText(inputSearch, text);
    }

    //Khởi tạo từng class riêng để gọi dùng cho nhanh
    LoginPage loginPage;

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    public CategoryPage openCategoryPage() {
        return CategoryPage.openCategoryPage();
    }

}
