package com.masteringselenium.page_objects;

import com.masteringselenium.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AboutUsPage {

	@FindBy(css = "#header nav div")
	private List<WebElement> headingButton;

	@FindBy(css = "#block_top_menu > ul > li")
	private List<WebElement> menuButton;

	public AboutUsPage() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	public boolean headingButtonsAreDisplayedOnAboutUsPage() {
		return headingButton.size() == 2;
	}
}
