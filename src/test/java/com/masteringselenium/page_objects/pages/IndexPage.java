package com.masteringselenium.page_objects.pages;

import com.masteringselenium.page_objects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IndexPage extends AbstractPage {

	//the Page Objects variables lets use co-related page objects in tests
	// via index page object as soon as it is created
	// i.e. indexPage.header.
//	public PageHeader header = new PageHeader();
//	public PageFooter footer = new PageFooter();

	//Locators
	private final By CONTENT_HEADER = By.cssSelector(".content-header h1");

	/**
	 * Might be used to return instance of this page
	 * (i.e. when fluently proceed through different
	 * pages during test scenario execution)
	 *
	 * @param driver - WebDriver instance
	 */
	public IndexPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Creates the page instance and sets URL
	 * to let open() method to open the page
	 *
	 * @param driver - WebDriver instance
	 * @param url    - URL to be opened
	 */
	public IndexPage(WebDriver driver, String url) {
		super(driver);
		super.url = url;
	}

	@Override
	protected By getUniqueElement() {
		return CONTENT_HEADER;
	}

	@Override
	public IndexPage open() {
		super.open();
		return this;
	}

	/*@FindBy(css = "#header nav div")
	private List<WebElement> headingButton;

	@FindBy(css = "#block_top_menu > ul > li")
	private List<WebElement> menuButton;



	public IndexPage() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	public boolean headingButtonsAreDisplayed() {
		return headingButton.size() == 2;
	}

	public boolean menuButtonsAreDisplayed() {
		return menuButton.size() == 3;
	}*/
}
