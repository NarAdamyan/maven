package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    private final By allIndustriesDropDown = By.xpath("//input[@class='ant-select-selection-search-input']");
    private final By informationTechnologiesOption = By.xpath("//div[text()='Information technologies']");
    private final By searchButton = By.xpath("//img[@alt='search-icon']");
public HomePage (){
}
    public HomePage selectRadioButtonOnTab(Tabs radioButton) {
        String radioButtonText = radioButton.getDisplayName();
        By radioButtonLocator = By.xpath(String.format("(//div//div[contains(text(), '%s')])[2]", radioButtonText));
        wait.until(ExpectedConditions.elementToBeClickable(radioButtonLocator)).click();
        return this;
    }


    public HomePage selectIndustryCategory(Tabs industry) {
        wait.until(ExpectedConditions.elementToBeClickable(allIndustriesDropDown));
        driver.findElement(allIndustriesDropDown).sendKeys(industry.getDisplayName());

        By industryOption = By.xpath(String.format("//div[text()='%s']", industry.getDisplayName()));
        wait.until(ExpectedConditions.elementToBeClickable(industryOption)).click();
        return this;
    }

    public SearchResultPage enterSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        // Wait for the next page's key element to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Enter keywords...']")
        ));

        return new SearchResultPage();
    }

    public String getIndustryDetail() {
        return driver.findElement(informationTechnologiesOption).getText().toLowerCase();
    }
}
