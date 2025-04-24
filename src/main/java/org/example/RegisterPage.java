package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class RegisterPage extends BasePage {

    @FindBy(xpath = "//div[text()='First name']//following-sibling::div/input")
    private WebElement firstName;
    @FindBy(xpath = "//div[text()='Last name']//following-sibling::div/input")
    private WebElement lastName;
    @FindBy(xpath = "//div[text()=\"Password\"]//following-sibling::div/input")
    private WebElement password;
    @FindBy(xpath = "//div[text()='Confirm password']//following-sibling::div/input")
    private WebElement confirmPassword;
    @FindBy(xpath = "(//div[text()='Register'])[3]")
    private WebElement register;
    @FindBy(xpath = "//div[text()='Email']//following-sibling::div/input")
    private WebElement email;
    @FindBy(xpath = "//div[text()='The field must be a valid email address.']")
    private WebElement emailError;
    private final By agreeToTermLoc = RelativeLocator.with(By.tagName("div")).toLeftOf(By.xpath("//span[contains(text(), 'I agree')]"));

    public RegisterPage fillFirstName(String name) {
        wait.until(ExpectedConditions.visibilityOf(firstName));
        firstName.sendKeys(name);
        return this;
    }
    public RegisterPage fillLastName(String lastname) {
        wait.until(ExpectedConditions.visibilityOf(lastName));
        lastName.sendKeys(lastname);
        return this;
    }
    public void enterEmail(String emailText) {
        wait.until(ExpectedConditions.visibilityOf(email));
        email.sendKeys(emailText);
    }
    public boolean isEmailErrorMessageVisible() {
        try {
            return emailError.isDisplayed(); // Or use wait + ExpectedConditions if needed
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    public void correctMailFieldIfReceiveEmailError(String correctedMail) {
        if (isEmailErrorMessageVisible()) {
            email.clear();
            email.sendKeys(correctedMail);
            wait.until(ExpectedConditions.invisibilityOf(emailError));
        }

    }

    public void enterPassword(String passwordText) {
        wait.until(ExpectedConditions.visibilityOf(password));
        password.sendKeys(passwordText);
    }
    public void confirmPassword(String password){
        wait.until(ExpectedConditions.visibilityOf(confirmPassword));
        confirmPassword.sendKeys(password);
    }
    public void agreementPrivacyAndPolicy() {
        WebElement checkbox = driver.findElement(agreeToTermLoc);
        actions.moveToElement(checkbox).click().perform();
    }
    public boolean isRegisterStyleChanges() {
        actions.moveToElement(register).click().perform();
        try {
            return wait.until(driver ->
                    register.getCssValue("background-color").equals("rgb(180, 180, 180)")
            );
        } catch (TimeoutException e) {
            return false;
        }
    }
}
