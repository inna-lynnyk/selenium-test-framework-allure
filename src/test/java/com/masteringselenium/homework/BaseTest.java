package com.masteringselenium.homework;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


@Slf4j
public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void setUp () {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\QaLight\\Desktop\\selenium-test-framework\\src\\test\\resources\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("Driver On");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void openPage() {
        driver.get("https://dbocorp.aval.ua");
        String dbocorp = driver.getCurrentUrl();
        System.out.println("Page " + dbocorp + " was opened");
    }

    @AfterMethod
    public void refreshPage() {

        System.out.println("AfterMethod");
    }

    @AfterSuite
    public void tearDown () {
        driver.quit();
        System.out.println("Driver off");
    }
}
