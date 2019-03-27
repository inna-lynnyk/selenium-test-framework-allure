package com.masteringselenium.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class FrameworkProperties {

	private static final String CONFIG_FILE = "config.properties";

	private static FrameworkProperties instance;
	private final Properties properties;

	private FrameworkProperties() {
		properties = new Properties();

		setDefaultProperties();

		getPropertiesFromConfigFile();

		properties.putAll(System.getProperties());
	}

	public static FrameworkProperties getInstance() {
		FrameworkProperties localInstance = instance;
		if (localInstance == null) {
			synchronized (FrameworkProperties.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new FrameworkProperties();
				}
			}
		}
		return localInstance;
	}

	/**
	 * Gets properties from config.properties file
	 */
	private void getPropertiesFromConfigFile() {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE);

		if (inputStream != null)
			try {
				properties.load(inputStream);
				log.info("Properties from " + CONFIG_FILE + " are obtained");
			} catch (IOException | IllegalArgumentException e) {
				String errorMessage = "Can't get properties from " + CONFIG_FILE;
				log.error(errorMessage, e);
				throw new IllegalStateException(errorMessage, e);
			}
		else {
			log.warn("No " + CONFIG_FILE + " file found! Default properties are used");
		}
	}

	/**
	 * Sets default properties to be used in framework
	 * (if the .properties file is not accessible
	 * or necessary parameters are not passed)
	 */
	private void setDefaultProperties() {
		properties.setProperty("test.protocol", "http://");
		properties.setProperty("test.connection.host", "v3.test.itpmgroup.com");
		properties.setProperty("test.user.id", "Student");
		properties.setProperty("test.user.password", "909090");
		properties.setProperty("test.driver.custom.timeout", "10000");
	}

	/**
	 * Getters for properties
	 */
	public String getProtocol() {
		return properties.getProperty("test.protocol");
	}

	public String getHost() {
		return properties.getProperty("test.connection.host");
	}

	public String getUserId() {
		return properties.getProperty("test.user.id");
	}

	public String getUserPassword() {
		return properties.getProperty("test.user.password");
	}

	public String getBaseURL() {
		return getProtocol() + getHost();
	}

	public int getCustomTimeout() {
		return Integer.valueOf(properties.getProperty("test.driver.custom.timeout"));
	}
}
