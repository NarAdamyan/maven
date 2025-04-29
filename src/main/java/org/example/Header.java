package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header extends BasePage {
    private By navBar = By.cssSelector("div[class=\"container\"]");

    public void getNavbarTab(String tab) {
        WebElement navigBar = driver.findElement(navBar);
        wait.until(ExpectedConditions.visibilityOf(navigBar));
        navigBar.findElement(By.xpath(String.format("//div[contains(text(), '%s')]", tab))).click();
    }


    private final String categoryXPath = "//div[contains(@class,'navbar-collapse')]//div[text()='%s']";
    private final By headerRegisterLoc = By.xpath("//a[@href='/register']/div");

    public void selectRegisterFromCandidate() {
        // Locate and click "Candidate"
        WebElement candidate = driver.findElement(navBar)
                .findElement(By.xpath(".//div[contains(text(), 'Candidate')]"));
        candidate.click();

        WebElement register = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Candidate')]//following::div[contains(text(), 'Register')][1]")
        ));

        register.click();
    }

    public void selectDropdownOption(String dropdownText, String optionText) {
        By dropdownLocator = By.xpath(String.format("//div[contains(text(), '%s')]", dropdownText));
        WebElement dropdownElement = driver.findElement(dropdownLocator);
        actions.moveToElement(dropdownElement).click().perform();

        By optionLocator = By.xpath(String.format("//div[@class='css-146c3p1' and contains(text()='%s')]", optionText));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement optionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(optionLocator));
        optionElement.click();
    }
}