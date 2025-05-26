package org.example;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

public class DropDown extends BasePage {
    private static final int TIMEOUT_SECONDS = 10;
    private static final int MAX_ATTEMPTS = 100; // Use a higher number for scrolling

    private final String baseXPath;
    private final String dropdownTextXpath = "%s//following::span/div[normalize-space()='%s']";
    private final String optionXpath = "//div[normalize-space()='%s']";

    public DropDown() {
        // More general and stable XPath for dropdowns
        this.baseXPath = "//div[contains(@class, 'ant-select-selector')]";
    }

    private By dropdownLocator(String dropdownLabel) {
        return By.xpath(String.format(dropdownTextXpath, baseXPath, dropdownLabel));
    }

    private By optionLocator(String optionText) {
        return By.xpath(String.format(optionXpath, optionText));
    }

    @Step("Select option '{optionText}' from dropdown '{dropdownName}'")
    public void selectOption(String dropdownName, String optionText) {
        // Wait for the dropdown to be clickable and open it
        WebElement dropdown = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(dropdownLocator(dropdownName)));

        actions.moveToElement(dropdown).click().perform();

        // Now scroll and select the option
        boolean optionFound = scrollAndFindOption(optionText);
        if (!optionFound) {
            throw new RuntimeException(String.format(
                    "Option '%s' not found in dropdown '%s' after %d attempts.",
                    optionText, dropdownName, MAX_ATTEMPTS
            ));
        }
    }

    private boolean scrollAndFindOption(String optionText) {
        int attempts = 0;
        while (attempts < MAX_ATTEMPTS) {
            try {
                // Try to find the option and click it
                WebElement option = driver.findElement(optionLocator(optionText));

                // Scroll the option into view and click
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
                actions.moveToElement(option).click().perform();
                return true;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                // Scroll down to the next set of options
                actions.sendKeys(Keys.ARROW_DOWN).perform();
                attempts++;
            }
        }
        return false;
    }
}