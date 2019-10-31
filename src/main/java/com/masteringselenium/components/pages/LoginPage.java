package com.masteringselenium.components.pages;

import com.masteringselenium.components.sections.LoginFormSection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage extends AbstractPage {

    //Consider using live templates (pfb)
    private static final By TITLE_SELECTOR = By.cssSelector(".login-logo");

    /**
     * Creates the page instance and sets URL
     * to let open() method to open the page
     *
     * @param driver - WebDriver instance
     * @param url    - URL to be opened
     */
    public LoginPage(WebDriver driver, String url) {
        super(driver);
        super.url = url;
    }

    public LoginFormSection getLoginFormSection() {
        return new LoginFormSection(driver);
    }

    @Override
    protected By getUniqueElement() {
        return TITLE_SELECTOR;
    }

    @Override
    public LoginPage verifyContainsText(String expectedText) {
        String actualResult = driver.findElement(TITLE_SELECTOR).getText();
        assertThat(actualResult).contains(expectedText);
        return this;
    }

    @Override
    public LoginPage verifyExactText(String expectedText) {
        String actualResult = driver.findElement(TITLE_SELECTOR).getText();
        assertThat(actualResult).isEqualToIgnoringCase(expectedText);
        return this;
    }
}
