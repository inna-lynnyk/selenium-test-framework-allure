package com.masteringselenium.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.masteringselenium.DriverFactory;

import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AllureListener implements ITestListener {

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	//Text attachment for Allure
	@Attachment(value = "Page screenshot", type = "image/png")
	public byte[] saveScreenshotPNG(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	@Attachment(value = "{0}", type = "text/html")
	public static String attachHtml(String html) {
		return html;
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
		if(driver != null) {
			log.warn("Screenshot captured for test case: " + testClass + getTestMethodName(iTestResult));
			saveScreenshotPNG(driver);
		}
		//Save log
		saveTextLog(getTestMethodName(iTestResult) + "failed and screenshot taken!");
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
