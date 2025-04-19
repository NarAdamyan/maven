package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeaderFooter extends BasePage {
    private By navBar = By.cssSelector("div[class=\"container\"]");

    public SearchResultPage getNavbarTab(Tabs tab) {
        WebElement navigBar = driver.findElement(navBar);
        wait.until(ExpectedConditions.visibilityOf(navigBar));
        navigBar.findElement(By.xpath(String.format("//div[contains(text(), '%s')]", tab))).click();
        return new SearchResultPage();
    }

    public HeaderFooter() {}

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


