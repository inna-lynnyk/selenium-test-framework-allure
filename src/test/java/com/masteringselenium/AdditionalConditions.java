package com.masteringselenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Class with additional/new ExpectedConditions for explicit waits.
 *
 * Example of usage:
 * 1. Within a test or class
 *    WebDriverWait wait = new WebDriverWait(getDriver(), 15, 100);
 *    wait.until(AdditionalConditions.jQueryAJAXCallsHaveCompleted());
 *
 * 2. Within a method
 *    new WebDriverWait(getDriver(), 15, 100)
 *        .ignoring(NoSuchElementException.class) //if applicable
 *        .ignoring(StaleElementException.class) //if multiple exceptions should be ignored
 *        .until(AdditionalConditions.jQueryAJAXCallsHaveCompleted());
 *
 */
public class AdditionalConditions {

	/**
	 * Find out whether jQuery has any outstanding active AJAX requests
	 */
	public static ExpectedCondition<Boolean> jQueryAJAXCallsHaveCompleted() {
		return new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) ((JavascriptExecutor) driver)
						.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};
	}

	/**
	 * A chain of conditions to ensure that AngularJS is available
	 * and it has had time to bootstrap and generate its services.
	 * It hooks into the internal 'pendingRequests' array and
	 * counts the number of AJAX requests that still need to complete.
	 */
	public static ExpectedCondition<Boolean> angularHasFinishedProcessing() {
		return new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				return Boolean.valueOf(((JavascriptExecutor) driver)
						.executeScript("return (window.angular !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)").toString());
			}
		};
	}

}
