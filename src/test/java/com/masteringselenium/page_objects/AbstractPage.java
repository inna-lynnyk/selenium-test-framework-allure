package com.masteringselenium.page_objects;

import com.masteringselenium.config.FrameworkProperties;
import com.masteringselenium.utils.AdditionalConditions;
import com.masteringselenium.utils.CustomWait;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Objects;

@Slf4j
public abstract class AbstractPage {
	protected WebDriver driver;
	protected String url;
	private FluentWait<WebDriver> wait;

	public AbstractPage(WebDriver driver) {
		this.driver = driver;

		CustomWait.setFluentWait(this.driver);
		this.wait = CustomWait.getWait();
	}

	/**
	 * Should be overriden by descendants actual PageObjects
	 * and return locator of the unique element on the page
	 *
	 * @return By locator - locator of the unique element
	 */
	protected abstract By getUniqueElement();

	/**
	 * Checks whether getUniqueElement() returns non null
	 * Throws @NullPointerException if unique element is not defined in descendant class
	 */
	private void checkUniqueElementNonNull() {
		By uniqueElement = getUniqueElement();

		if (Objects.nonNull(uniqueElement)) {
			log.info("The unique {} element '{}' obtained", getClass().getSimpleName(), uniqueElement);
		} else {
			throw new NullPointerException("The unique element for " + getClass().getSimpleName() + "has not been defined in descendant class");
		}
	}

	/**
	 * Verifies whether the unique page element is present on the page
	 * if true, the page is considered to be opened
	 */
	private void verifyPageOpened() {
		checkUniqueElementNonNull();

		wait.withTimeout(Duration.ofSeconds(FrameworkProperties.getInstance().getCustomTimeout()))
				.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
				.pollingEvery(Duration.ofMillis(300))
				.withMessage("The unique element: {" + getUniqueElement() + "} not found")
				.until(ExpectedConditions.presenceOfElementLocated(getUniqueElement()));

		log.info("The unique {} element '{}' has been found", getClass().getSimpleName(), getUniqueElement());
		log.info("The {} has been opened", getClass().getSimpleName());
	}

	/**
	 * Verifies whether the page finished loading
	 */
	private void verifyPageLoadingCompleted() {
		wait.until(AdditionalConditions.javaScriptPageLoadingCompleted());
		log.info("The {} loading completed", getClass().getSimpleName());
	}

	/**
	 * Checks whether URL to be opened is non null
	 * Throws @NullPointerException if URL is not defined
	 *
	 * @param URL - String URL to be opened
	 */
	private void checkURLnonNull(String URL) {
		if (Objects.nonNull(url)) {
			log.info("The URL for {} obtained: {}", getClass().getSimpleName(), URL);
		} else {
			throw new NullPointerException("The URL for " + getClass().getSimpleName() + " has not been defined");
		}
	}

	/**
	 * Opens the page by URL assigned
	 * The URL is being checked for NonNull
	 *
	 * @param URL - String full URL to open the page
	 */
	private void openPageByURL(String URL) {
		checkURLnonNull(URL);

		log.info("Opening {} at: {}", getClass().getSimpleName(), URL);
		driver.get(URL);
	}

	/**
	 * Opens the page with verifications of url, page loading completeness and unique element's presence
	 *
	 * @return this - the object of the (descendant) page being opened
	 */
	public AbstractPage open() {
		log.info("Opening {}", getClass().getSimpleName());
		openPageByURL(url);
		verifyPageLoadingCompleted();
		verifyPageOpened();
		return this;
	}

	//wrapper for driver.findElement() to be used in descendants
	protected WebElement findElement(By selector) {
		return driver.findElement(selector);
	}
}
