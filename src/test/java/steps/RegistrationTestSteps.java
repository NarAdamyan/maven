package steps;

import io.qameta.allure.Step;
import org.example.DropDown;
import org.example.HeaderFooter;
import org.example.RegisterPage;
import org.example.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationTestSteps {

    private final WebDriver driver = WebDriverManager.getInstance().getDriver();
    private final RegisterPage registerPage = new RegisterPage(); // Pass driver instance
    private final DropDown dropdown = new DropDown();       // Pass driver instance
    private final HeaderFooter headerFooter = new HeaderFooter(); // Pass driver instance


    @Step("Get driver")
    public WebDriver getDriver() {
        WebDriver driver = WebDriverManager.getInstance().getDriver();
        driver.get("https://staff.am/en");
        return driver;
    }

    @Step("Select Register from Candidate")
    public void selectRegisterFromCandidate() {
        headerFooter.selectRegisterFromCandidate();
    }

    @Step("Fill your name")
    public void fillTheName(String name) {
        registerPage.fillFirstName(name);
    }

    @Step("Fill the lastName")
    public void fillTheLastName(String lastname) {
        registerPage.fillLastName(lastname);
    }

    @Step("Enter emailText")
    public void enterEmail(String mailText) {
        registerPage.enterEmail(mailText);
    }

    @Step("Check if email error message is visible")
    public boolean getErrorMsg() {
        return registerPage.isEmailErrorMessageVisible();
    }

    @Step("Correct invalid email if needed")
    public void correctEmailIfInvalid(String correctedMail) {
        registerPage.correctMailFieldIfReceiveEmailError(correctedMail);
    }

    @Step("Enter passwordText")
    public void enterPassword(String passwordText) {
        registerPage.enterPassword(passwordText);
    }

    @Step("Confirm Password")
    public void confirmPassword(String text) {
        registerPage.confirmPassword(text);
    }

    @Step("Agreement Privacy and Policy")
    public void agreemetnPrivacyAndPolicy() {
        registerPage.agreementPrivacyAndPolicy();
    }

    public void fillBirthday(String year, String month, String day) {
        System.out.println("Selecting Year: " + year);
        dropdown.selectOption("Year", year);
        System.out.println("Year selected.");

        System.out.println("Selecting Month: " + month);
        dropdown.selectOption("Month", month);
        System.out.println("Month selected.");

        System.out.println("Selecting Day: " + day);
        dropdown.selectOption("Day", day);
        System.out.println("Day selected.");
    }
//
//    @Step("Set first name: {firstName}")
//    public void setFirstName(String firstName) {
//        registerPage.setFirstName(firstName);
//    }
//
//    @Step("Set last name: {lastName}")
//    public void setLastName(String lastName) {
//        registerPage.setLastName(lastName);
//    }
//
//    @Step("Fill password fields")
//    public void fillPasswords() {
//        registerPage.setPassword();
//        registerPage.confirmPassword();
//    }
//
//    @Step("Agree to Terms and Conditions")
//    public void agreeToTerms() {
//        registerPage.agreeToTerms();
//    }
//
//    @Step("Enter invalid email and verify error message is displayed'{expectedErrorText}'")
//    public boolean enterEmailAndCheckInvalidMessage(String email, String expectedErrorText) {
////        AllureAttachments.attachScreenshot(driver,"Shows expected error");
//        return registerPage.enterEmailAndCheckInvalidMessage(email, expectedErrorText);
//    }
//
//    @Step("Enter valid email and verify no error message is displayed")
//    public boolean enterValidEmailAndVerifyNoError(String email) {
//        return registerPage.enterValidEmailAndVerifyNoError(email);
//    }
//
//    @Step("Submit registration")
//    public void submitRegistration() {
//        registerPage.submitRegister();
//    }
//
//    @Step("Get expected style value for the Register button")
//    public String getExpectedRegisterButtonStyle() {
//        return registerPage.getRegisterButtonStyle();
//    }
//
//    @Step("Get actual style value for the Register button")
//    public String getActualRegisterButtonStyle() {
//        return registerPage.getRegisterButtonStyle();
//    }
}