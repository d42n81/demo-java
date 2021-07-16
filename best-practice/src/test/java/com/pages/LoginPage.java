package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public By usernameFieldLocator = By.id("user-name");
    public By passwordFieldLocator = By.id("password");
    public By submitButtonLocator = By.id("login-button");

    @Override
    public String getPagePart() {
        return "";
    }

    public void login(String userName) {
        //Create an instance of a Selenium explicit wait
        // so that we can dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //wait for the user name field to be visible and store that element into a variable
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameFieldLocator));

        WebElement userNameField = driver.findElement(usernameFieldLocator);
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        WebElement submitButton = driver.findElement(submitButtonLocator);

        userNameField.sendKeys(userName);
        passwordField.sendKeys("secret_sauce");
        submitButton.click();
    }

    public Integer getPageLoadTime() {
        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");
        Map<String, Object> perfMetrics = (Map<String, Object>) js.executeScript("sauce:log", metrics);
        Integer loadTime = Integer.parseInt(perfMetrics.get("load").toString());
        return loadTime;
    }

}
