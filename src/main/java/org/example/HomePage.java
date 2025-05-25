package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private final By allIndustriesDropDown = By.xpath("//input[@class='ant-select-selection-search-input']");
    private final By informationTechnologiesOption = By.xpath("//div[text()='Information technologies']");
    private final By searchButton = By.xpath("//img[@alt='search-icon']");

    public HomePage() {
    }

    public HomePage selectRadioButtonOnTab(String radioButton) {
        // Directly use radioButton as the text to find the element
        By radioButtonLocator = By.xpath(String.format("(//div//div[contains(text(), '%s')])[2]", radioButton));

        // Wait until the element is clickable and then click it
        wait.until(ExpectedConditions.elementToBeClickable(radioButtonLocator)).click();

        return this;
    }


    public HomePage selectIndustryCategory(String industry) {
        // Wait for the dropdown to be clickable and interact with it
        wait.until(ExpectedConditions.elementToBeClickable(allIndustriesDropDown));
        driver.findElement(allIndustriesDropDown).sendKeys(industry);  // Directly use 'industry' (String)

        // Define the XPath locator for the selected industry option
        By industryOption = By.xpath(String.format("//div[text()='%s']", industry));  // Directly use 'industry'

        // Wait for the industry option to be clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(industryOption)).click();

        return this;
    }

    public SearchResultPage enterSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Enter keywords...']")
        ));

        return new SearchResultPage();
    }

    public String getIndustryDetail() {
        return driver.findElement(informationTechnologiesOption).getText().toLowerCase();
    }
}
