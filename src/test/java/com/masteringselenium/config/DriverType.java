package com.masteringselenium.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.Arrays;
import java.util.HashMap;

public enum DriverType implements DriverSetup {

	FIREFOX {
		@Override
		public MutableCapabilities getDesiredCapabilities() {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setCapability("marionette", true);
			return firefoxOptions;
		}

		@Override
		public WebDriver getWebDriverObject(MutableCapabilities capabilities) {
			WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver((FirefoxOptions) capabilities);
		}
	},

	CHROME {
		@Override
		public MutableCapabilities getDesiredCapabilities() {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setCapability("chrome.switches",
					Arrays.asList("--no-default-browser-check",
							"--disable-extensions",
							"--disable-notifications",
							"--disable-translate",
							"--disable-infobars",
							"--disable-web-security",
							"--disable-save-password-bubble"));

			HashMap<String, String> chromePreferences = new HashMap<>();
			chromePreferences.put("profile.password_manager_enabled", "false");

			chromeOptions.setCapability("chrome.prefs", chromePreferences);

			return chromeOptions;
		}

		@Override
		public WebDriver getWebDriverObject(MutableCapabilities capabilities) {
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver((ChromeOptions) capabilities);
		}
	},

	IE {
		@Override
		public MutableCapabilities getDesiredCapabilities() {
			MutableCapabilities capabilities = new InternetExplorerOptions();
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			capabilities.setCapability("requireWindowFocus", true);

			return capabilities;
		}

		@Override
		public WebDriver getWebDriverObject(MutableCapabilities capabilities) {
			WebDriverManager.iedriver().setup();
			return new InternetExplorerDriver((InternetExplorerOptions) capabilities);
		}
	},

	SAFARI {
		@Override
		public MutableCapabilities getDesiredCapabilities() {
			MutableCapabilities capabilities = new SafariOptions();
			capabilities.setCapability("safari.cleanSession", true);

			return capabilities;
		}

		@Override
		public WebDriver getWebDriverObject(MutableCapabilities capabilities) {
			return new SafariDriver((SafariOptions) capabilities);
		}
	},

	OPERA {
		@Override
		public MutableCapabilities getDesiredCapabilities() {
			return new OperaOptions();
		}

		@Override
		public WebDriver getWebDriverObject(MutableCapabilities capabilities) {
			WebDriverManager.operadriver().setup();
			return new OperaDriver((OperaOptions) capabilities);
		}
	}
}
