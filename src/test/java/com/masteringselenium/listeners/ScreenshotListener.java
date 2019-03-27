package com.masteringselenium.listeners;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.masteringselenium.DriverFactory.getDriver;

@Slf4j
public class ScreenshotListener extends TestListenerAdapter {
	/**
	 * Creates a file
	 *
	 * @param screenshot - screenshot file
	 * @return <code>true<code/> if file has been created
	 * @throws IOException - exception if cant create file or directory
	 */
	private boolean createFile(File screenshot) throws IOException {
		boolean fileCreated = false;

		if (screenshot.exists()) {
			fileCreated = true;
		} else {
			File parentDirectory = new File(screenshot.getParent());

			if (parentDirectory.exists() || parentDirectory.mkdirs()) {
				fileCreated = screenshot.createNewFile();
			}
		}
		return fileCreated;
	}

	/**
	 * Writes screenshot to a file
	 *
	 * @param driver     - WebDriver driver
	 * @param screenshot - screenshot file
	 * @throws IOException - if no screenshot file obtained
	 */
	private void writeScreenshotToFile(WebDriver driver, File screenshot) throws IOException {
		FileOutputStream screenshotStream = new FileOutputStream(screenshot);
		screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
		screenshotStream.close();
	}

	/**
	 * Listener that captures a screenshot on test failure
	 *
	 * @param failingTest - ITestResult failingTest
	 */
	@Override
	public void onTestFailure(ITestResult failingTest) {
		try {
			WebDriver driver = getDriver();
			String screenshotDirectory = System.getProperty("screenshotDirectory");
			String screenshotAbsolutePath = screenshotDirectory + File.separator
					+ System.currentTimeMillis() + "_" + failingTest.getName()
					+ ".png";
			File screenshot = new File(screenshotAbsolutePath);

			if (createFile(screenshot)) {
				try {
					writeScreenshotToFile(driver, screenshot);
				} catch (ClassCastException weNeedToAugmentOurDriverObject) {
					writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
				}
				log.info("Screenshot saved to " + screenshotAbsolutePath);
			} else {
				log.error("Unable to create " + screenshotAbsolutePath);
			}
		} catch (Exception ex) {
			log.error("Unable to capture screenshot...");
			ex.printStackTrace();
		}
	}
}
