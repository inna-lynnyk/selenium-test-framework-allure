package com.masteringselenium.components.elements.buttons;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Slf4j
public class SubmitButton extends AbstractButton {
    private static final By SUBMIT_BUTTON_SELECTOR = By.cssSelector("button[type=\"submit\"]");

    public SubmitButton(WebDriver driver, By parentSelector) {
        super(driver, parentSelector);
    }

    @Override
    protected By getConcreteButton() {
        return SUBMIT_BUTTON_SELECTOR;
    }

    @Override
    public SubmitButton verifyDisabled() {
        throw new UnsupportedOperationException("This button does not support this action");
    }
}
