package com.masteringselenium.homework;

import org.openqa.selenium.By;
import org.testng.annotations.*;
import static org.testng.Assert.assertTrue;

public class Demo extends BaseTest{
    private static final By KEY_FIELD_SELECTOR = By.className("fileField");
    private static final By PASSWORD_FIELD_SELECTOR = By.className("passwordField");
    private static final By ENTER_BUTTON = By.className("actionDefault");
    private static final By EXPANDABLE_TRIGGER = By.className("userInfo");
    private static final By LOGOUT_BUTTON = By.className("actionLogout");
    private static final String KEY_PATH = "C:\\Users\\user\\Desktop\\dbocorp\\src\\test\\resources\\keys\\33010822_test_client.dat";
    private static final String WRONG_KEY_PATH = "C:\\Users\\user\\Desktop\\dbocorp\\src\\test\\resources\\keys\\README.txt";
    private static final String PASSWORD = "Aval#2019";


    @Test (priority = 1, description = "Success login")
    public void successLoginWithKeyAndPassword () throws InterruptedException {

        login();

        boolean checkLogin = driver.getTitle().contains("Головна");
        assertTrue(checkLogin);

        logout();
    }


    @Test (priority = 2, description = "Login with wrong password", dataProvider = "InvalidPassword")
    public void errorMessageOnLoginAttemptWithWrongPassword (String Password) {

        driver.findElement(KEY_FIELD_SELECTOR).sendKeys(KEY_PATH);
        driver.findElement(PASSWORD_FIELD_SELECTOR).sendKeys(Password);
        driver.findElement(ENTER_BUTTON).click();


        boolean checkErrorMessage = driver.findElement(By.className("feedback")).isDisplayed();
        assertTrue(checkErrorMessage);
        //driver.navigate().refresh();

    }

    @Test (priority = 3, description = "Login with wrong key")
    public void errorMessageOnLoginWithWrongKey () {

        driver.findElement(KEY_FIELD_SELECTOR).sendKeys(WRONG_KEY_PATH);
        driver.findElement(PASSWORD_FIELD_SELECTOR).sendKeys(PASSWORD);
        driver.findElement(ENTER_BUTTON).click();

        boolean checkErrorMessage = driver.findElement(By.className("feedback")).isDisplayed();
        assertTrue(checkErrorMessage);
    }

    @Test (priority = 4, description = "Login without password")
    public void errorMessageOnLoginWithoutPassword () {

        driver.findElement(KEY_FIELD_SELECTOR).sendKeys(KEY_PATH);
        driver.findElement(PASSWORD_FIELD_SELECTOR).sendKeys("");
        driver.findElement(ENTER_BUTTON).click();

        boolean checkErrorMessage = driver.findElement(By.cssSelector(".feedbackPanel > .feedbackPanelERROR")).isDisplayed();
        assertTrue(checkErrorMessage);
    }

    @Test (priority = 4, description = "Login without credentials")
    public void errorMessageOnLoginWithoutCredentials () {

        driver.findElement(ENTER_BUTTON).click();

        boolean checkErrorMessage = driver.findElement(By.cssSelector(".feedbackPanel > .feedbackPanelERROR")).isDisplayed();
        assertTrue(checkErrorMessage);

    }



    private void login() throws InterruptedException {
        driver.findElement(KEY_FIELD_SELECTOR).sendKeys(KEY_PATH);
        driver.findElement(PASSWORD_FIELD_SELECTOR).sendKeys(PASSWORD);
        driver.findElement(ENTER_BUTTON).click();
        Thread.sleep(10000);
    }

    private void logout() {
        driver.findElement(EXPANDABLE_TRIGGER).click();
        driver.findElement(LOGOUT_BUTTON).click();
    }


    @DataProvider
    private Object[][] InvalidPassword() {
        return new Object[][] {
                {"222"},
                {" "},
                {"##00#($)47347"},
                {"Aval#2018"}
        };
    }

}
