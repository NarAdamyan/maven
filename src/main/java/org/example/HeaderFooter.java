package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeaderFooter extends BasePage {
    private By navBar = By.cssSelector("div[class=\"container\"]");

    public WebElement getNavbarTab(Tabs tab) {
        WebElement navigBar = driver.findElement(navBar);
        wait.until(ExpectedConditions.visibilityOf(navigBar));
        return navigBar.findElement(By.xpath(String.format("//div[contains(text(), '%s')]", tab)));
    }


    private final String categoryXPath = "//div[contains(@class,'navbar-collapse')]//div[text()='%s']";
    private final By headerRegisterLoc = By.xpath("//a[@href='/register']/div");

    public void selectRegisterFromCandidate() {
        // Locate and click "Candidate"
        WebElement candidate = driver.findElement(navBar)
                .findElement(By.xpath(".//div[contains(text(), 'Candidate')]"));
        candidate.click();

        // Wait for "Register" to be visible after clicking "Candidate"
        WebElement register = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Candidate')]//following::div[contains(text(), 'Register')][1]")
        ));

        // Click "Register"
        register.click();
//        register.sendKeys(Keys.ESCAPE);

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

public void selectCandidateRegister() {
    selectDropdownOption("Candidate", "Register");
}

public HeaderFooter() {
}

public void selectFooterCategoryViewAllCompanies() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//a//div[text()='View all companies'])[2]")));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
    wait.until(ExpectedConditions.elementToBeClickable(el));
    try {
        el.click();
    } catch (ElementClickInterceptedException e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }
}
}