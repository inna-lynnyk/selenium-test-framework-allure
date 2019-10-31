package com.masteringselenium.tests;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

@Slf4j
public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void setUp() {
        log.info("Starting setup");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\QaLight\\Desktop\\selenium-test-framework\\src\\test\\resources\\drivers\\chromedriver.exe"); //drivers/chromedriver.exe
        log.info("WebDriver property was set");
        initializeChromeDriver();
        // initializes driver
        //driver = DriverFactory.getDriver();

        //Puts an Implicit wait, Will wait for 10 seconds before throwing exception
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Maximize the browser
        driver.manage().window().maximize();
        log.info("Setup was finished");
    }

    private void initializeChromeDriver() {
        driver = new ChromeDriver();
        log.info("Driver has been initialized");
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
        System.out.println("Driver has been quite");
    }

    protected void openIndexPage() {
        driver.get("http://v3.test.itpmgroup.com");
    }
}
