package com.masteringselenium;

import com.masteringselenium.config.DriverType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.net.MalformedURLException;
import java.net.URL;

import static com.masteringselenium.config.DriverType.CHROME;
import static com.masteringselenium.config.DriverType.FIREFOX;

/**
 * Class that instantiates an actual driver
 */
public class WebDriverThread {

	private WebDriver webDriver;
	private DriverType selectedDriverType;

	private final DriverType defaultDriverType = FIREFOX;
	private final String browser = System.getProperty("browser");
	private final String operatingSystem = System.getProperty("os.name").toUpperCase();
	private final String systemArchitecture = System.getProperty("os.arch");
	private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");

	private static final Logger LOG = LogManager.getLogger(WebDriverThread.class);

	/**
	 * Sets up and returns a valid WebDriver instance
	 *
	 * @return webDriver - WebDriver object with predefined options
	 */
	public WebDriver getDriver() {
		if (webDriver == null) {
			selectedDriverType = determineEffectiveDriverType();
			MutableCapabilities capabilities = selectedDriverType.getDesiredCapabilities();
			instantiateWebDriver(capabilities);
		}
		return webDriver;
	}

	public void quitDriver() {
		if (webDriver != null) {
			webDriver.quit();
			webDriver = null;
		}
	}

	/**
	 * Reads and holds the type of browser specified, otherwise,
	 * returns default driverType
	 *
	 * @return driverType
	 */
	private DriverType determineEffectiveDriverType() {
		DriverType driverType = defaultDriverType;

		try {
			driverType = DriverType.valueOf(browser.toUpperCase());
		} catch (IllegalArgumentException ignored) {
			LOG.warn("Unknown driver specified, defaulting to '" + driverType + "'...");
		} catch (NullPointerException ignored) {
			LOG.warn("No driver specified, defaulting to '" + driverType + "'...");
		}
		return driverType;
	}

	/**
	 * Instantiates a WebDriver object with desired options
	 *
	 * If 'remoteDriver' property is set to 'true' in pom
	 * the method will instantiate a remote WebDriver object
	 * (platform and browser version are optional parameters)
	 *
	 * @param capabilities passed parameters for a browser
	 * @throws MalformedURLException if no or invalid selenoid URL provided
	 */
	private void instantiateWebDriver(MutableCapabilities capabilities) {
		LOG.info("Current Browser Selection: " + selectedDriverType);
		LOG.info("Current Operating System: " + operatingSystem);
		LOG.info("Current Architecture: " + systemArchitecture);
		LOG.info("Remote execution: " + useRemoteWebDriver);

		//use pom property <remoteDriver> to instantiate local or remote WebDriver object
		if (useRemoteWebDriver) {
			try {
				URL selenoidURL = new URL(System.getProperty("selenoidURL"));
				String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
				String desiredPlatform = System.getProperty("desiredPlatform");
				DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

				if (desiredPlatform != null && !desiredPlatform.isEmpty()) {
					capabilities.setCapability("platform", Platform.valueOf(desiredPlatform.toUpperCase()));
				}

				if (desiredBrowserVersion != null && !desiredBrowserVersion.isEmpty()) {
					desiredCapabilities.setVersion(desiredBrowserVersion);
					capabilities.merge(desiredCapabilities);
				}
				webDriver = new RemoteWebDriver(selenoidURL, capabilities);

			} catch (MalformedURLException e) {
				LOG.error("Selenoid URL either absent or corrupted");
				e.printStackTrace();
			}
		} else {
			webDriver = selectedDriverType.getWebDriverObject(capabilities);
		}
	}
}
