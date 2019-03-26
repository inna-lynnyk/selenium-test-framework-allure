package com.masteringselenium.page_objects.common_elements;

import com.masteringselenium.DriverFactory;
import com.masteringselenium.page_objects.SignInPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageHeader {

	@FindBy(css = "#contact-link > a")
	private WebElement contactUsLink;

	@FindBy(css = ".login")
	private WebElement signInLink;

	public PageHeader() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	public void goToContactUsPage() {
		contactUsLink.click();
		//return 'Contact us' page object
	}

	public SignInPage goToSignInPage() {
		signInLink.click();
		return new SignInPage();
	}
}
