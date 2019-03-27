package com.masteringselenium.utils;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomWait {

	@Getter
	private static WebDriverWait wait;

	public static void setWait(WebDriver driver, int seconds) {
		CustomWait.wait = new WebDriverWait(driver, seconds);
	}
}
