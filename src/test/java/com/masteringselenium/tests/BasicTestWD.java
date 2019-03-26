package com.masteringselenium.tests;

import com.masteringselenium.DriverFactory;
import com.masteringselenium.page_objects.IndexPage;
import com.masteringselenium.page_objects.SignInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicTestWD extends DriverFactory {

	private static final String baseURL = "http://automationpractice.com";

	private void searchOnIndexPage(String query) {

		WebDriver driver = DriverFactory.getDriver();

		driver.get(baseURL +"/index.php");

		WebElement searchField = driver.findElement(IndexPage.searchField);
		searchField.clear();
		searchField.sendKeys(query);
		searchField.submit();

		System.out.println("Page title is: " + driver.getTitle());

		new WebDriverWait(driver, 10)
				.until((ExpectedCondition<Boolean>) driverObject ->
						driverObject.getTitle().toLowerCase().startsWith("Search".toLowerCase()));
	}

	@Test
	public void indexPageSearchExample() {
		searchOnIndexPage("Blouse");
	}

	@Test
	public void loginExample() {
		SignInPage signInPage = new SignInPage();

		getDriver().get(baseURL + "/index.php?controller=authentication&back=my-account");
		signInPage.enterEmail("test@test.com")
				.enterPassword("12345");

		assertThat(signInPage.alertMessageIsNotDisplayed()).isTrue();

		signInPage.clickLoginButton();

		assertThat(signInPage.getAlertMessage(0).getText()).contains("Authentication failed.");
	}

	@Test
	public void testThatAboutPageHasButtonsInTheHeader() {
		getDriver().get(baseURL);

		indexPage = new IndexPage();
		assertThat(indexPage.headingButtonsAreDisplayed()).isTrue();
		assertThat(indexPage.menuButtonsAreDisplayed()).isTrue();

		aboutUsPage = indexPage.footer.goToAboutUsPage();
		assertThat(aboutUsPage.headingButtonsAreDisplayedOnAboutUsPage()).isTrue();
	}

	@Test
	public void testActions() throws Exception {
		getDriver().get(baseURL);

		//Actions does not chain waits
		Actions advancedActions = new Actions(getDriver());
		WebDriverWait wait = new WebDriverWait(getDriver(), 5, 100);

		WebElement womenMenu = getDriver().findElement(By.cssSelector("#block_top_menu a[title='Women']"));
		WebElement blousesSubMenu = getDriver().findElement(By.cssSelector("a[title='Blouses']"));

		//hover
		advancedActions.moveToElement(womenMenu)
				.perform();

		wait.until(ExpectedConditions.visibilityOf(blousesSubMenu));

		advancedActions.moveToElement(blousesSubMenu)
				.click()
				.perform();

		new WebDriverWait(getDriver(), 10)
				.until((ExpectedCondition<Boolean>) driverObject ->
						driverObject.getTitle().toLowerCase().startsWith("Blouses".toLowerCase()));

		assertThat(getDriver().getTitle()).contains("Blouses");

		/*//test for drag and drop
		getDriver().get("https://html5demos.com/drag/");

		WebElement boxTwo = getDriver().findElement(By.id("two"));
		WebElement bin = getDriver().findElement(By.id("bin"));

		//drag and drop (1px right, 1px down from the left corner of the element)
		advancedActions.moveToElement(boxTwo, 1, 1)
				.clickAndHold()
				.moveToElement(bin)
				.release()
				.perform();*/
	}

}
