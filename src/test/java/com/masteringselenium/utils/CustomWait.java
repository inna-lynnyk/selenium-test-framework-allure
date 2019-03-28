package com.masteringselenium.utils;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

public class CustomWait {

	@Getter
	private static FluentWait<WebDriver> wait;

	public static void setFluentWait(WebDriver driver) {
		CustomWait.wait = new FluentWait<>(driver);
	}
}
