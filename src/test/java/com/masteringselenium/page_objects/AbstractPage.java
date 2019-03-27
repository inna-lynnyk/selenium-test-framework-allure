package com.masteringselenium.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class AbstractPage {
	protected WebDriver driver;
	protected String url;

	public AbstractPage(WebDriver driver) {
		this.driver = driver;
	}

	protected abstract AbstractPage open();

	protected abstract AbstractPage verifyPageOpened();

	protected WebElement findElement(By selector) {
		return driver.findElement(selector);
	}
}
