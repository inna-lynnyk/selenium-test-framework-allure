package com.masteringselenium.utils;

import com.masteringselenium.domain.FrameworkProperties;
import lombok.experimental.UtilityClass;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

@UtilityClass
public class CustomWait {

    public static FluentWait<WebDriver> getFluentWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(FrameworkProperties.getInstance().getCustomTimeout()))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(StaleElementReferenceException.class, NoSuchElementException.class);
    }

    public static FluentWait<WebDriver> getFluentWait(WebDriver driver, int timeInSeconds, int pollingInMillis) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(FrameworkProperties.getInstance().getCustomTimeout()))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(StaleElementReferenceException.class, NoSuchElementException.class);
    }
}
