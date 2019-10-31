package com.masteringselenium.tests;

import com.masteringselenium.components.pages.LoginPage;
import com.masteringselenium.driver.DriverFactory;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.BLOCKER;

@Feature("Login")
public class LoginPageTestsWD extends DriverFactory {

    @Test(description = "Opening Login page")
    @Severity(BLOCKER)
    @Description("- Page description is displayed at the top,\n"
            + "- Verbatim 'Show balance for specific date' is displayed before Datepicker,\n"
            + "- Datepicker input and button are displayed,\n"
            + "- Show and Clear buttons are displayed and disabled,\n"
            + "- Table header and body are visible,\n"
            + "- Table header and body cells are not empty.")
    @Story("LOG-5")
    public void verifyLoginPageMainElementsPresent() {
        LoginPage loginPage = new LoginPage(getDriver(), baseURL);
        loginPage.open();
        loginPage.verifyVisible()
                .verifyContainsText("Учет запчастей");

        loginPage.getLoginFormSection()
                .verifyVisible()
                .verifyExactText("Авторизация")
                .enterEmail("Student")
                .enterPassword("909090")
                .getSubmitButton().verifyVisible()
                .verifyExactText("Вход")
                .verifyEnabled()
                .verifyDisabled()
                .click();
    }
}
