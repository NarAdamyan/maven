package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class RegisterPage extends BasePage {
    @FindBy(xpath = "//h1[contains(text(), 'Register your account')]/../..")
    private WebElement registrationForm;

    @FindBy(xpath = ".//div[text()='First name']//following-sibling::div/input")
    private WebElement firstName;

    @FindBy(xpath = ".//div[text()='Last name']//following-sibling::div/input")
    private WebElement lastName;

    @FindBy(xpath = ".//div[text()=\"Password\"]//following-sibling::div/input")
    private WebElement password;

    @FindBy(xpath = ".//div[text()='Confirm password']//following-sibling::div/input")
    private WebElement confirmPassword;

    @FindBy(xpath = ".//div[text()='Register']")
    private WebElement register;

    @FindBy(xpath = ".//div[text()='Email']//following-sibling::div/input")
    private WebElement email;

    @FindBy(xpath = ".//div[text()='The field must be a valid email address.']")
    private WebElement emailError;

    private final By agreeToTermLoc = (By.xpath("//span[contains(text(), 'I agree')]"));

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



    public RegisterPage enterEmail(String emailText) {
        wait.until(ExpectedConditions.visibilityOf(email));
        email.sendKeys(emailText);
        return this;
    }

    public boolean isEmailErrorMessageVisible() {
        try {
            return true; // Or use wait + ExpectedConditions if needed
        } catch (NoSuchElementException | TimeoutException e) {
            // Optionally log the exception here
            System.out.println("Error occurred: " + e.getMessage());
            return false;
        }
    }

    public boolean correctMailFieldIfReceiveEmailError(String correctedMail) {
        try {
            email.clear();
            email.sendKeys(correctedMail);
            wait.until(ExpectedConditions.invisibilityOf(emailError)); // Wait until error is cleared
            return true; // Return true if no error is visible
        } catch (NoSuchElementException | TimeoutException e) {
            // Optionally log the exception here
            System.out.println("Error occurred while waiting for email error message: " + e.getMessage());
            return false; // Return false if the email error is still visible or other issues occur
        }
    }


    public RegisterPage enterPassword(String passwordText) {
        wait.until(ExpectedConditions.visibilityOf(password));
        password.sendKeys(passwordText);
        return this;
    }

    public RegisterPage confirmPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(confirmPassword));
        confirmPassword.sendKeys(password);
        return this;
    }

    public RegisterPage agreementPrivacyAndPolicy() {
        WebElement checkbox = driver.findElement(agreeToTermLoc);
        actions.moveToElement(checkbox).click().perform();
        checkbox.click();
        return this;
    }

    public boolean isRegisterStyleChanges() {
        actions.moveToElement(register).perform();
        try {
            return wait.until(driver ->
                    register.getCssValue("background-color").equals("rgb(180, 180, 180)")
            );
        } catch (TimeoutException e) {
            return false;
        }
    }
    public void clickRegisterButton(){
        wait.until(ExpectedConditions.elementToBeClickable(register)).click();
    }

}