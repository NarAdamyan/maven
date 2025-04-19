package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class Filter {

    // XPath for filter options (change this according to your structure)
    private final String filterCategoryXpath = "div[class=\"css-175oi2r r-1v456y7\"]";
    private final String filterNameXpath = "//following-sibling::div//span[text()=\"%s\"]";
    private final String filterTypeXpath = "//div[text()='%s']";

    // Get all categories on the filter page
    public List<String> getFilterCategories() {
        WebDriver driver = WebDriverManager.getInstance().getDriver();
        List<WebElement> categories = driver.findElements(By.xpath(filterCategoryXpath));
        List<String> categoryNames = new ArrayList<>();
        for (WebElement category : categories) {
            categoryNames.add(category.getText());
        }
        return categoryNames;
    }

    public List<String> getFilterOptions(String categoryName) {
        // Define XPath for the filter category and options
        String filterCategoryXpath = String.format(filterTypeXpath, categoryName);
        String filterOptionsXpath = String.format(filterNameXpath, categoryName);

        // Scroll to the filter category element to ensure it's in view
        WebDriver driver = WebDriverManager.getInstance().getDriver();
        WebElement filterCategoryElement = driver.findElement(By.xpath(filterCategoryXpath));
        scrollToElement(filterCategoryElement);

        // Wait for the filter category element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        filterCategoryElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(filterCategoryXpath)));

        // Scroll to the options section if necessary
        WebElement filterOptionsSection = driver.findElement(By.xpath(filterOptionsXpath));
        scrollToElement(filterOptionsSection);

        // Wait for the options to be visible
        List<WebElement> optionElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(filterOptionsXpath)));

        // Extract the options into a list
        List<String> options = new ArrayList<>();
        for (WebElement option : optionElements) {
            options.add(option.getText().trim());
        }

        // Debugging output
        System.out.println("Number of options found for category " + categoryName + ": " + options.size());

        return options;
    }

    public int getFilteredResultsCount() {
        // Find the container of the job results on the page
        List<WebElement> jobResults = WebDriverManager.getInstance().getDriver().findElements(By.xpath("//div[@class='job-result']")); // Update this XPath as per your page

        return jobResults.size();  // Return the count of job results
    }

    // Select a random option from a given filter category
    public WebElement selectRandomOption(String categoryName) {
        List<String> options = getFilterOptions(categoryName);
        if (options.size() > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(options.size());

            String randomOption = options.get(randomIndex);

            // Find the random option element using filterNameXpath
            return WebDriverManager.getInstance().getDriver().findElement(By.xpath(String.format(filterNameXpath, randomOption)));
        } else {
            System.out.println("No options available.");
            return null;
        }
    }

    // Scroll to an element on the page
    private void scrollToElement(WebElement element) {
        WebDriver driver = WebDriverManager.getInstance().getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element)); // Wait for the element to be visible
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element); // Scroll into view
    }
}
