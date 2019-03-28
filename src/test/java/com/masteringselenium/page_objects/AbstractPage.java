package com.masteringselenium.page_objects;

import com.masteringselenium.utils.AdditionalConditions;
import com.masteringselenium.config.FrameworkProperties;
import com.masteringselenium.utils.CustomWait;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public abstract class AbstractPage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected String url;
	protected final int CUSTOM_WAIT_TIMEOUT = FrameworkProperties.getInstance().getCustomTimeout();

	public AbstractPage(WebDriver driver) {
		this.driver = driver;

		CustomWait.setWait(this.driver, CUSTOM_WAIT_TIMEOUT);
		this.wait = CustomWait.getWait();
	}

	protected abstract AbstractPage verifyPageOpened();

	public AbstractPage open() {
		log.info("Openning page at: {}", url);
		driver.get(url);
		wait.until(AdditionalConditions.javaScriptPageLoadingCompleted());
		return this;
	}

	//wrapper for driver.findElement() to be used in descendants
	protected WebElement findElement(By selector) {
		return driver.findElement(selector);
	}
}
