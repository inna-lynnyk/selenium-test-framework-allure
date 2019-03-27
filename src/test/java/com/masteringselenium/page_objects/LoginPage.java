package com.masteringselenium.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {
	private String url;

	//Consider using live templates
	private final By EMAIL_FIELD = By.name("_username");
	private final By PASSWORD_FIELD = By.id("password");
	private final By SUBMIT_BUTTON = By.cssSelector(".btn.btn-primary.btn-block");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public LoginPage(WebDriver driver, String url) {
		super(driver);
		this.url = url;
	}

	@Override
	public LoginPage open() {
		driver.get(url);
		return this;
	}

	public LoginPage enterEmail(String email) {
		findElement(EMAIL_FIELD).sendKeys(email);
		return this;
	}

	public LoginPage enterPassword(String password) {
		findElement(PASSWORD_FIELD).sendKeys(password);
		return this;
	}

	public LoginPage clickSubmitButton() {
		findElement(SUBMIT_BUTTON).click();
		return this;
	}

	/*public SignInPage enterEmail(String email) {
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

//	@FindBy(id = "email")
//	private WebElement emailAddressField;

//	public SignInPage() {
//		PageFactory.initElements(DriverFactory.getDriver(), this);
//	}
}
