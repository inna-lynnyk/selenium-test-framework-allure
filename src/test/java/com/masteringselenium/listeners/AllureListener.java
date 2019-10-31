package com.masteringselenium.listeners;

import static org.openqa.selenium.logging.LogType.BROWSER;

import java.util.stream.Collectors;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.masteringselenium.driver.DriverFactory;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AllureListener extends TestListenerAdapter {

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	//Text attachment for Allure
	@Attachment(value = "Screenshot", type = "image/png")
	private byte[] saveScreenshotPNG(WebDriver driver) {
		log.info("Saving screenshot");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Attachment(value = "HTML", type = "text/html")
	private static String attachHtml(WebDriver driver) {
		log.info("Attaching HTML logs");
		return driver.getPageSource();
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		log.info("On start method " + iTestContext.getName());
		iTestContext.setAttribute("WebDriver", DriverFactory.getDriver());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		log.info("On finish method " + iTestContext.getName());
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		log.info(getTestMethodName(iTestResult) + "start");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		log.info(getTestMethodName(iTestResult) + " succeed");
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		log.warn("Test failed: " + getTestMethodName(iTestResult) + " failed");
		Object testClass = iTestResult.getInstance();
		WebDriver driver = DriverFactory.getDriver();
		if (driver != null) {
			log.warn("Screenshot captured for test case: " + testClass + getTestMethodName(iTestResult));
			saveScreenshotPNG(driver);
			//Save log
			attachHtml(driver);
		}
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		log.warn(getTestMethodName(iTestResult) + " skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		log.warn(getTestMethodName(iTestResult) + " skipped");
	}
}
