package com.masteringselenium.tests;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.testng.Assert.assertTrue;

//import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class InitialDemo extends BaseTest {
    private static final By USER_NAME_INPUT_SELECTOR = By.name("_username");

    @AfterMethod
    public void refreshPage() {
        log.info("In @AfterMethod");
    }

    @Test(priority = 1, description = "User cannot login if invalid username passed")
    public void userCannotLoginWhenInvalidUsername() {
        log.warn("No test");
    }

    @Test
    public void userCanLogin() {
        log.error("Error");
        log.info("After error");
        // declaration and instantiation of objects/variables
        //System.setProperty("webdriver.firefox.marionette", "\\src\\test\\resources\\drivers\\geckodriver.exe");
        //driver = new ChromeDriver();
        //comment the above 2 lines and uncomment below 2 lines to use Chrome

        openIndexPage();
        // Enter user name
        driver.findElement(USER_NAME_INPUT_SELECTOR).sendKeys("Student");

        // Enter password

        driver.findElement(By.id("password")).sendKeys("909090");

        // Enter value 10 in the first number of the percent Calculator
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-block")).click();

        // Verify name on newly opened page
        String result = driver.findElement(By.className("content-header")).getText();

        // Print a Log In message to the screen
        System.out.println(" The Result is " + result);

        // Do assertion (validation)
        boolean actual = result.contains("Главная");
        assertTrue(actual);

        driver.findElement(By.cssSelector(".user-image")).click();
        driver.findElement(By.className("pull-right")).click();

        String loginButtonText = driver.findElement(By.cssSelector(".btn.btn-primary.btn-block")).getText();
        System.out.println(" The Result is " + loginButtonText);

        boolean isLogOut = loginButtonText.contains("Вход");
        assertTrue(isLogOut);
    }

    @Test(priority = 2, dataProvider = "invalidUserNames")
    public void wrongLogin(String userName) {
        By usernameSelector = By.name("_username");
        driver.findElement(usernameSelector).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys("909090");
        driver.findElement(By.cssSelector(".btn.btn-primary.btn-block")).click();

        // Do assertion (validation)
    }

    //loggers, selectors, selenium interactions, waits

    @Test
    public void inputInteraction() {
        openIndexPage();
        By usernameSelector = By.name("_username");
        driver.findElement(usernameSelector).clear(); //clear field
        driver.findElement(usernameSelector).sendKeys("QWERTY"); //type in the field

        driver.findElement(usernameSelector).clear(); //clear field
        driver.findElement(By.id("password")).sendKeys("909090"); //type in the field

        String typedText = driver.findElement(usernameSelector).getAttribute("value");
        System.out.println(typedText);
    }

    @Test
    public void buttonsElementsInteraction() {
        openIndexPage();
        By submitButtonLocator = By.cssSelector(".btn.btn-primary.btn-block");

        driver.findElement(submitButtonLocator).isEnabled();
        driver.findElement(submitButtonLocator).click();
    }

    @Test
    void radioButtonsInteraction() {
        driver.get("http://test.rubywatir.com/radios.php");

        // Click on Radio Button
        By radioButtonSelector = By.id("radioId");
        driver.findElement(radioButtonSelector).click();
        System.out.println("The Output of the IsSelected " +
                driver.findElement(radioButtonSelector).isSelected());
        System.out.println("The Output of the IsEnabled " +
                driver.findElement(radioButtonSelector).isEnabled());
        System.out.println("The Output of the IsDisplayed " +
                driver.findElement(radioButtonSelector).isDisplayed());
        sleep();
    }

    @Test
    void checkBoxesInteraction() {
        driver.get("http://test.rubywatir.com/checkboxes.php");

        By checkBoxSelector = By.cssSelector("input[value=\"football\"]");
        driver.findElement(checkBoxSelector).click();
        System.out.println("The Output of the IsSelected " +
                driver.findElement(checkBoxSelector).isSelected());
        System.out.println("The Output of the IsEnabled " +
                driver.findElement(checkBoxSelector).isEnabled());
        System.out.println("The Output of the IsDisplayed " +
                driver.findElement(checkBoxSelector).isDisplayed());
    }

    @Test
    void dropdownInteraction() {
        driver.get("http://www.globalsqa.com/demo-site/select-dropdown-menu/");

        By dropDownSelector = By.cssSelector("div[rel-title=\"Select Country\"] select");
        WebElement dropdownElement = driver.findElement(dropDownSelector);
        Select dropdown = new Select(dropdownElement);

        dropdown.selectByVisibleText("Ukraine");
        sleep(3000);

        //you can also use
        dropdown.selectByIndex(1); // to select second element as
        sleep(3000);
        //index starts with 0.
        //OR
        dropdown.selectByValue("ALB");
        sleep(3000);

        System.out.println("The Output of the IsSelected " +
                driver.findElement(dropDownSelector).isSelected());
        System.out.println("The Output of the IsEnabled " +
                driver.findElement(dropDownSelector).isEnabled());
        System.out.println("The Output of the IsDisplayed " +
                driver.findElement(dropDownSelector).isDisplayed());
    }

    @Test
    public void dragAndDrop() {
        driver.navigate().to("http://www.keenthemes.com/preview/metronic/templates/admin/ui_tree.html");
        driver.manage().window().maximize();

        WebElement dragDropSection = driver.findElement(By.className("portlet-title"));
        WebElement from = driver.findElement(By.id("j3_7_anchor"));
        WebElement to = driver.findElement(By.id("j3_1_anchor"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", dragDropSection);
        sleep();

        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(from).moveToElement(to).release(to).build();
        dragAndDrop.perform();

        sleep();
    }

    @Test
    public void getAllLinks() {
        driver.get("http://www.globalsqa.com/demo-site/select-dropdown-menu/");

        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Number of Links in the Page is " + links.size());

        for (int i = 1; i < links.size(); i++) {
            System.out.println("Name of Link# " + i + links.get(i).getText());
        }
    }

    @Test
    public void multiSelect() {
        driver.navigate().to("http://demos.devexpress.com/aspxeditorsdemos/ListEditors/MultiSelect.aspx");

        //chose multiple selection mode
        driver.findElement(By.id("ControlOptionsTopHolder_lbSelectionMode")).click();
        driver.findElement(By.id("ControlOptionsTopHolder_lbSelectionMode_DDD_L_LBI1T0")).click();
        sleep();

        // Perform Multiple Select
        Actions builder = new Actions(driver);
        WebElement select = driver.findElement(By.id("ContentHolder_lbFeatures_LBT")); //finds table with options
        List<WebElement> options = select.findElements(By.tagName("td"));

        System.out.println(options.size());
        Action multipleSelect = builder
                .keyDown(Keys.CONTROL)
                .click(options.get(2))
                .click(options.get(4))
                .click(options.get(6))
                .build();

        multipleSelect.perform();
        sleep();
    }

    @Test
    public void mouseActions() {
        //        void click(WebElement onElement)
        //        void contextClick(WebElement onElement)
        //        void doubleClick(WebElement onElement)
        //        void mouseDown(WebElement onElement)
        //        void mouseUp(WebElement onElement)
        //        void mouseMove(WebElement toElement)
        //        void mouseMove(WebElement toElement, long xOffset, long yOffset)
    }

    @Test
    public void keyboardActions() {
        //        void sendKeys(java.lang.CharSequence keysToSend)
        //        void pressKey(java.lang.CharSequence keyToPress)
        //        void releaseKey(java.lang.CharSequence keyToRelease)
    }

    @Test
    public void waiters() {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); //implicit wait (global setting), throws NoSuchElementException
        WebDriverWait explicitWait = new WebDriverWait(driver, 20); //excplicit wait that fails by condition
        openIndexPage();
        By usernameSelector = By.name("_username");
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(usernameSelector));

        Duration timeout = Duration.ofSeconds(20);
        Duration pollingTimeout = Duration.ofMillis(500);
        // Waiting 30 seconds for an element to be present on the page, checking
        // for its presence once every 5 seconds.
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(pollingTimeout)
                .ignoring(NoSuchElementException.class);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    WebElement webElement = webDriver.findElement(usernameSelector);
                    return webElement.isDisplayed();
                } catch (final StaleElementReferenceException e) {
                    return false;
                }
            }
        });
    }

    @Test
    public void loggers() {
		/*<dependencies>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.25</version>
    </dependency>
  </dependencies>*/
		/*<dependency>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-simple</artifactId>
                    <version>${slf4j-simple.version}</version>
                </dependency>*/
		/*<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-slf4j-impl</artifactId>
			<version>${log4j.version}</version>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-core</artifactId>
			<version>${log4j.version}</version>
			<scope>test</scope>
		</dependency>*/
		/*<?xml version="1.0" encoding="UTF-8"?>

		//log4j.xml
<!DOCTYPE log4j:configuration PUBLIC "-//log4j/log4j Configuration//EN" "log4j.dtd">

<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/">

    <appender name="Appender1" class="org.apache.log4j.ConsoleAppender">
       <layout class="org.apache.log4j.PatternLayout">
          <param name="ConversionPattern" value="%-7p %d [%t] %c %x - %m%n"/>
       </layout>
    </appender>

    <appender name="Appender2" class="org.apache.log4j.FileAppender">
       <param name="File" value="applog.txt" />
       <layout class="org.apache.log4j.PatternLayout">
          <param name="ConversionPattern" value="%-7p %d [%t] %c %x - %m%n"/>
       </layout>
    </appender>

    <root>
        <priority value="DEBUG"/>
        <appender-ref ref="Appender1" />
        <appender-ref ref="Appender2" />
    </root>

</log4j:configuration>*/

		/*<?xml version="1.0" encoding="UTF-8"?>
<configuration status="warn">
	<Properties>
		<Property name="sq.logging.pattern">[%d{dd/MM/yy HH:mm:ss.SSS}] %-5p [%.8t] [%X{sq-correlation-id}] [%c{1}] %m%n</Property>
	</Properties>
	<Appenders>
		<Console name="console" target="SYSTEM_OUT">
			<PatternLayout pattern="${sys:sq.logging.pattern}"/>
		</Console>
		<File name="docker_logs" fileName="target/docker-compose.log">
			<PatternLayout pattern="%d{dd/MM/yy HH:mm:ss.SSS}] %X{service}\t%m%n" />
		</File>
	</Appenders>
	<Loggers>
		<Logger name="com." level="info" additivity="false">
			<AppenderRef ref="console"/>
		</Logger>
		<Logger name="com.github." level="info" additivity="false">
			<AppenderRef ref="console"/>
		</Logger>
		<Logger name="org.glassfish.jersey.filter.LoggingFilter" level="info" additivity="false">
			<AppenderRef ref="console"/>
		</Logger>
		<Logger name="util.Slf4jLoggerListener" level="info" additivity="false">
			<AppenderRef ref="docker_logs" />
		</Logger>
		<Logger name="util.VerboseTestListener" level="info" additivity="false">
			<AppenderRef ref="console" />
		</Logger>
		<Root level="info">
			<AppenderRef ref="console"/>
		</Root>
	</Loggers>
</configuration>*/
    }

    @DataProvider
    private Object[][] invalidUserNames() {
        return new Object[][]{
                {"qwerlkjh"},
                {" "},
                {"#"},
                {"#$%JKADFJ"}
        };
    }

    private void sleep() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}

