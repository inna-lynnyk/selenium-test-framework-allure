package com.masteringselenium.page_objects;

import com.masteringselenium.config.FrameworkProperties;
import com.masteringselenium.utils.AdditionalConditions;
import com.masteringselenium.utils.CustomWait;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public abstract class AbstractPage {
	protected WebDriver driver;
	protected String url;
	private WebDriverWait wait;

	public AbstractPage(WebDriver driver) {
		this.driver = driver;

		CustomWait.setWait(this.driver, FrameworkProperties.getInstance().getCustomTimeout());
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
	 * Waits for the unique page element to get present on the page
	 */
	private void verifyPageOpened() {
		wait.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
				.pollingEvery(Duration.ofMillis(300))
				.until(ExpectedConditions.presenceOfElementLocated(getUniqueElement()));
	}

	/**
	 * Wait for the page finish loading
	 */
	private void verifyPageLoadingCompleted() {
		wait.until(AdditionalConditions.javaScriptPageLoadingCompleted());
	}

	public AbstractPage open() {
		log.info("Openning a page at: {}", url);

		driver.get(url);
		verifyPageLoadingCompleted();
		verifyPageOpened();

		log.info("The page has been loaded and opened");
		return this;
	}

	//wrapper for driver.findElement() to be used in descendants
	protected WebElement findElement(By selector) {
		return driver.findElement(selector);
	}
}
