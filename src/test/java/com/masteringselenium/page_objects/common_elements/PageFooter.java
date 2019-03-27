package com.masteringselenium.page_objects.common_elements;

import com.masteringselenium.DriverFactory;
import com.masteringselenium.page_objects.pages.AboutUsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageFooter {

	@FindBy(css = "a[title='About us']")
	private WebElement aboutUsLink;

	public PageFooter() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	public AboutUsPage goToAboutUsPage() {
		aboutUsLink.click();
		return new AboutUsPage();
	}
}
