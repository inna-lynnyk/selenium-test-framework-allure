package com.masteringselenium.components.pages;

import com.masteringselenium.components.Readable;
import com.masteringselenium.components.Visible;
import com.masteringselenium.utils.AdditionalConditions;
import com.masteringselenium.utils.CustomWait;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class AbstractPage implements Visible, Readable {
    protected WebDriver driver;
    protected FluentWait<WebDriver> fluentWait;
    private String page;
    protected String url;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        this.page = getClass().getSimpleName();
        this.fluentWait = CustomWait.getFluentWait(this.driver);
        //this.fluentWait = CustomWait.getFluentWait(this.driver, 20, 500);
    }

    /**
     * Should be overriden by descendants of actual PageObjects
     * and return locator of the unique element on the page
     *
     * @return By locator - locator of the unique element
     */
    protected abstract By getUniqueElement();

    @Override
    public AbstractPage verifyVisible() {
        driver.findElement(getUniqueElement()).isDisplayed();
        return this;
    }

    /**
     * Opens the page with verifications of url, page loading completeness and unique element's presence
     *
     * @return this - the object of the (descendant) page being opened
     */
    public AbstractPage open() {
        log.info("Opening {}", page);
        openPageByURL(url);
        verifyPageOpened();
        verifyPageLoadingCompleted();
        return this;
    }

    /**
     * Opens the page by URL assigned
     * The URL is being checked for NonNull
     *
     * @param URL - String full URL to open the page
     */
    private void openPageByURL(String URL) {
        checkURLnonNull(URL);

        log.info("Opening {} at: {}", page, URL);
        driver.get(URL);
    }

    /**
     * Checks whether URL to be opened is non null
     * Throws @NullPointerException if URL is not defined
     *
     * @param url - String URL to be opened
     */
    private void checkURLnonNull(String url) {
        if (Objects.nonNull(url)) {
            log.info("The URL for {} obtained: {}", page, url);
        } else {
            throw new NullPointerException("The URL for " + page + " has not been defined");
        }
    }

    /**
     * Verifies whether the unique page element is present on the page
     * if true, the page is considered to be opened
     */
    private void verifyPageOpened() {
        //checkUniqueElementNonNull();
        fluentWait.withMessage("Searching for unique element: {" + getUniqueElement() + "}")
                .until(ExpectedConditions.presenceOfElementLocated(getUniqueElement()));

        log.info("The unique {} element '{}' has been found", page, getUniqueElement());
        log.info("The {} has been opened", page);
    }

    /**
     * Verifies whether the page finished loading
     */
    private void verifyPageLoadingCompleted() {
        fluentWait.until(AdditionalConditions.javaScriptPageLoadingCompleted());
        log.info("The {} loading completed", page);
    }

    /*
     *//**
     * Should be overriden by descendants actual PageObjects
     * and return locator of the unique element on the page
     *
     * @return By locator - locator of the unique element
     *//*
    protected abstract By getUniqueElement();

    *

    //wrapper for driver.findElement() to be used in descendants
    protected WebElement findElement(By selector) {
        return driver.findElement(selector);
    }

    *//**
     * Checks whether getUniqueElement() returns non null
     * Throws @NullPointerException if unique element is not defined in descendant class
     *//*
    private void checkUniqueElementNonNull() {
        By uniqueElement = getUniqueElement();

        if (Objects.nonNull(uniqueElement)) {
            log.info("The unique {} element '{}' obtained", page, uniqueElement);
        } else {
            throw new NullPointerException("The unique element for " + page + " has not been defined");
        }
    }

    *//**
     * Verifies whether the unique page element is present on the page
     * if true, the page is considered to be opened
     *//*
    private void verifyPageOpened() {
        checkUniqueElementNonNull();

        wait.withTimeout(Duration.ofSeconds(FrameworkProperties.getInstance().getCustomTimeout()))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
                .withMessage("The unique element: {" + getUniqueElement() + "} not found")
                .until(ExpectedConditions.presenceOfElementLocated(getUniqueElement()));

        log.info("The unique {} element '{}' has been found", page, getUniqueElement());
        log.info("The {} has been opened", page);
    }*/
}
