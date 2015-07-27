package org.owasp.webgoat.features;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

@RunWith(Cucumber.class)
public class LoginFeature {

    private String appUrl = "http://localhost:8080/WebGoat/";
    private WebDriver driver;
    private LoginActions login;

    @Given("^I want to use the browser (.*)$")
    public void chooseBrowser(Browsers browser) throws Exception {
        this.driver = browser.getDriver();
        driver.get(appUrl);
        login = new LoginActions(driver);
    }

    @Given("^I navigate to the login page$")
    public void I_navigate_to_the_login_page() throws Throwable {
        driver.get(appUrl + "login.mvc");
    }

    @Then("^I see \"([^\"]*)\" as input$")
    public void I_see_as_label(String expected) throws Throwable {
        driver.getPageSource().contains(expected);
    }

    @When("^I enter \"([^\"]*)\" as username$")
    public void I_enter_as_username(String username) throws Throwable {
        login.enterUsername(username);
    }

    @When("^I enter \"([^\"]*)\" as password")
    public void I_enter_as_password(String password) throws Throwable {
        login.enterPassword(password);
    }

    @And("^I login$")
    public void I_press_the_button() throws Throwable {
        login.clickLogin();
    }

    @Then("^I see the following error message \"([^\"]*)\"$")
    public void I_see_the_following_error_message(String errorMessage) throws Throwable {
        login.checkLoginErrorMessage(errorMessage);
    }

    @Then("^the url contains \"([^\"]*)\"$")
    public void the_url_contains(String arg1) throws Throwable {
        assertTrue(driver.getCurrentUrl().contains(arg1));
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }

    @When("^I login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void I_login_with_username_and_password(String user, String password) throws Throwable {
        login.loginAs(user, password);
    }
}
