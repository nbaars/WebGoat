package org.owasp.webgoat.features;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class LoginActions {

    private WebDriver driver;

    public LoginActions(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void loginAs(String user, String password) throws Exception {
        enterUsername(user);
        enterPassword(password);
        clickLogin();
    }

    public void enterUsername(String user) throws Exception {
        driver.findElement(By.name("username")).sendKeys(user);
    }

    public void enterPassword(String password) throws Exception {
        driver.findElement(By.name("password")).sendKeys(password);
    }

    public void clickLogin() throws Exception {
        driver.findElement(By.xpath("//*[contains(text(), 'Sign in')]")).click();
    }

    public void checkLoginErrorMessage(String errorMessage) throws Exception {
        long end = System.currentTimeMillis() + 1000;
        while (System.currentTimeMillis() < end) {
            assertTrue(driver.getPageSource().contains(errorMessage));
        }
    }
}
