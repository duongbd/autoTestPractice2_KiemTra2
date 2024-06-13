package org.example.testcase;

import org.example.common.BaseTest;
import org.example.pages.LoginPage;
import org.testng.annotations.Test;

public class CategoryTest extends BaseTest {

    private final LoginPage loginPage = new LoginPage();


    @Test
    public void testAddNewCateGory() {
        loginPage.login("admin@example.com", "123456")
                .openCategoryPage()
                .verifyAddNewCateGory();
    }
}
