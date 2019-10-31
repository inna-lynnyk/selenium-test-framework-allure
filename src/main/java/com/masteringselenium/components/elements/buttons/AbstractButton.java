package com.masteringselenium.components.elements.buttons;

import com.masteringselenium.components.Clickable;
import com.masteringselenium.components.Readable;
import com.masteringselenium.components.Visible;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public abstract class AbstractButton implements Clickable, Visible, Readable {
    private WebElement parentElement;

    AbstractButton(WebDriver driver, By parentSelector) {
        this.parentElement = driver
                .findElement(parentSelector)
                .findElement(getConcreteButton());
    }

    protected abstract By getConcreteButton();

    @Step("Click button")
    @Override
    public AbstractButton click() {
        parentElement.click();
        return this;
    }

    @Step("Verifying button is enabled")
    @Override
    public AbstractButton verifyEnabled() {
        assertThat(parentElement.isEnabled()).isTrue();
        return this;
    }

    @Override
    public AbstractButton verifyDisabled() {
        assertThat(parentElement.isEnabled()).isFalse();
        return this;
    }

    @Step("Verifying button is visible")
    @Override
    public AbstractButton verifyVisible() {
        parentElement.isDisplayed();
        return this;
    }

    @Step("Verify button contains text: {expectedText}")
    @Override
    public AbstractButton verifyContainsText(String expectedText) {
        String actualText = parentElement.getText();
        assertThat(actualText).containsIgnoringCase(expectedText);
        return this;
    }

    @Step("Verify button's text is: {expectedText}")
    @Override
    public AbstractButton verifyExactText(String expectedText) {
        String actualText = parentElement.getText();
        assertThat(actualText).isEqualToIgnoringCase(expectedText);
        return this;
    }
}
