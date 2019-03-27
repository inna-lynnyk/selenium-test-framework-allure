package com.masteringselenium.page_objects;

import com.masteringselenium.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SignInPage {

	/*//Consider using live templates
	//https://www.jetbrains.com/help/idea/using-live-templates.html
	@FindBy(id = "email")
	private WebElement emailAddressField;

	@FindBy(id = "passwd")
	private WebElement passwordField;

	@FindBy(id = "SubmitLogin")
	private WebElement signInButton;

	@FindBy(css = ".alert.alert-danger > ol li")
	public List<WebElement> alertMessage;

	public SignInPage() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	public SignInPage enterEmail(String email) {
		emailAddressField.clear();
		emailAddressField.sendKeys(email);

		return this;
	}

	public SignInPage enterPassword(String password) {
		passwordField.clear();
		passwordField.sendKeys(password);

		return this;
	}

	public void clickLoginButton() {
		signInButton.click();
	}

	public boolean alertMessageIsNotDisplayed() {
		return alertMessage.size() == 0;
	}

	public WebElement getAlertMessage(int lineNumber) {
		if(alertMessage.size() == 0) {
			throw new NullPointerException();
		}

		return alertMessage.get(lineNumber);
	}*/
}
