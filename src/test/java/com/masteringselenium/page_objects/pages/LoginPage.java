package com.masteringselenium.page_objects.pages;

import com.masteringselenium.page_objects.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginPage extends AbstractPage {

	//Consider using live templates (pfb)
	private final By LOGIN_FORM = By.cssSelector(".login-box-body");
	private final By EMAIL_FIELD = By.name("_username");
	private final By PASSWORD_FIELD = By.id("password");
	private final By SUBMIT_BUTTON = By.cssSelector(".btn.btn-primary.btn-block");

	/**
	 * Might be used to return instance of this page
	 * (i.e. when fluently proceed through different
	 * pages during test scenario execution)
	 *
	 * @param driver - WebDriver instance
	 */
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Creates the page instance and sets URL
	 * to let open() method to open the page
	 *
	 * @param driver - WebDriver instance
	 * @param url    - URL to be opened
	 */
	public LoginPage(WebDriver driver, String url) {
		super(driver);
		super.url = url;
	}

	@Override
	public LoginPage open() {
		super.open();
		return this;
	}

	@Override
	public LoginPage verifyPageOpened() {
		boolean isLoginFormDisplayed = findElement(LOGIN_FORM).isDisplayed();

		assertThat(isLoginFormDisplayed).isTrue();

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

	public IndexPage getIndexPage() {
		return new IndexPage(driver);
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
