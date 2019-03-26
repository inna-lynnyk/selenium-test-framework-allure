package com.masteringselenium.page_objects;

import com.masteringselenium.DriverFactory;
import com.masteringselenium.page_objects.common_elements.PageFooter;
import com.masteringselenium.page_objects.common_elements.PageHeader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class IndexPage {
	public static By searchField = By.id("search_query_top");

	@FindBy(css = "#header nav div")
	private List<WebElement> headingButton;

	@FindBy(css = "#block_top_menu > ul > li")
	private List<WebElement> menuButton;

	//use page objects via index page object in tests
	public PageHeader header = new PageHeader();
	public PageFooter footer = new PageFooter();

	public IndexPage() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	public boolean headingButtonsAreDisplayed() {
		return headingButton.size() == 2;
	}

	public boolean menuButtonsAreDisplayed() {
		return menuButton.size() == 3;
	}
}
