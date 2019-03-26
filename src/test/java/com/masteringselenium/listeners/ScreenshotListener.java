package com.masteringselenium.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class ScreenshotListener extends TestListenerAdapter {

	private static final Logger LOG = LogManager.getLogger(ScreenshotListener.class);

	/**
	 * Creates a file
	 *
	 * @param screenshot
	 * @return <code>true<code/> if file has been created
	 * @throws IOException
	 */
	private boolean createFile(File screenshot) throws IOException {
		boolean fileCreated = false;

		if(screenshot.exists()) {
			fileCreated = true;
		} else {
			File parentDirectory = new File(screenshot.getParent());

			if(parentDirectory.exists() || parentDirectory.mkdirs()) {
				fileCreated = screenshot.createNewFile();
			}
		}
		return fileCreated;
	}

	/**
	 * Writes screenshot to a file
	 *
	 * @param driver
	 * @param screenshot
	 * @throws IOException
	 */
	private void writeScreenshotToFile(WebDriver driver, File screenshot) throws IOException {
		FileOutputStream screenshotStream = new FileOutputStream(screenshot);
		screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
		screenshotStream.close();
	}

	/**
	 * Listener that captures a screenshot on test failure
	 * @param failingTest
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

			if(createFile(screenshot)) {
				try {
					writeScreenshotToFile(driver, screenshot);
				} catch(ClassCastException weNeedToAugmentOurDriverObject) {
					writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
				}
				LOG.info("Screenshot saved to " + screenshotAbsolutePath);
			} else {
				LOG.error("Unable to create " + screenshotAbsolutePath);
			}
		} catch(Exception ex) {
			LOG.error("Unable to capture screenshot...");
			ex.printStackTrace();
		}
	}
}
