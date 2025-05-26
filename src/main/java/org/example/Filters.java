package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filters extends BasePage {
    WebDriver driver = WebDriverManager.getInstance().getDriver();

    public Filters() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "a[role='button']")
    List<WebElement> paginationButtons;

    @FindBy(css = "img[alt='left-icon']")
    List<WebElement> jobResultIcons;

    public static Map<String, String> getRandomOptionPerFilter(WebDriver driver) {
        Map<String, List<String>> filterOptionsMap = new HashMap<>();
        filterOptionsMap.put("Job category", List.of("Accounting/Bookkeeping/Cash register", "Sales/service management"));
        filterOptionsMap.put("Job special tag", List.of("Flexible hours", "Fresh graduates"));
        filterOptionsMap.put("Specialist level", List.of("Junior", "Mid level", "Senior"));
        filterOptionsMap.put("Job salary", List.of("Mentioned", "Not Mentioned"));

        Random random = new Random();
        List<String> keys = new ArrayList<>(filterOptionsMap.keySet());
        String randomFilter = keys.get(random.nextInt(keys.size()));
        List<String> options = filterOptionsMap.get(randomFilter);
        String randomOption = options.get(random.nextInt(options.size()));
        return Map.of(randomFilter, randomOption);
    }

    public Filters selectFilterSection(String filterName) {

        WebElement filterSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format("//div[text()='%s']", filterName))
        ));
        actions.moveToElement(filterSection).perform();
        openViewMoreIfAvailable();
        return this;
    }

    private void openViewMoreIfAvailable() {
        driver.findElements(By.xpath("//div[@tabindex]/div[text()= 'View more']")).stream()
                .findFirst()
                .ifPresent(button -> {
                    actions.moveToElement(button).click().perform();
//                    wait.until(ExpectedConditions.invisibilityOf(button));
                });
    }

    public WebElement selectOption(String optionToSelect) {
        String oldUrl = driver.getCurrentUrl(); // store current URL

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//span[text()='%s']", optionToSelect))
        ));
        actions.moveToElement(option).click().perform();

        // Wait for job cards (or any indication of page update)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt='company-logo']")));

        // ✅ Wait until URL changes
        wait.until(driver -> !driver.getCurrentUrl().equals(oldUrl));

        return option;
    }

    public int returnFilteredElementsCount(WebElement option) {
        wait.until(ExpectedConditions.visibilityOf(option));
        Matcher matcher = Pattern.compile("\\((\\d+)\\)").matcher(option.getText());
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    public int getFilteredResultsCount() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("img[alt='left-icon']")));
        jobResultIcons = driver.findElements(By.cssSelector("img[alt='left-icon']"));

        if (jobResultIcons.isEmpty()) {
            System.out.println("❌ No results found.");
            return 0;
        }

        int lastPage = getLastPageNumber();
        return (lastPage == 1) ? jobResultIcons.size() : calculateTotalResults(lastPage);
    }

    private int getLastPageNumber() {
        paginationButtons = driver.findElements(By.cssSelector("a[role='button']"));

        if (paginationButtons.isEmpty()) {
            System.out.println("⚠️ No pagination found in getLastPageNumber().");
            return 1; // No pagination = 1 page
        }

        System.out.println(paginationButtons.size() + " pagination buttons found.");
        return paginationButtons.stream()
                .map(WebElement::getText)
                .filter(text -> text.matches("\\d+"))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(1);
    }


    private int calculateTotalResults(int lastPage) throws InterruptedException {
        // Wait for pagination buttons to become visible
        WebDriverWait waitForPagination = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Wait for pagination buttons to be present and visible
        List<WebElement> paginationButtons;
        try {
            paginationButtons = driver.findElements(By.cssSelector("a[role='button']"));
            if (paginationButtons.isEmpty()) {
                System.out.println("⚠️ No pagination found. Calculating results without pagination.");
                return getResultsWithoutPagination();
            }

        } catch (TimeoutException e) {
            System.out.println("⚠️ No pagination found (timeout). Calculating results without pagination.");
            int totalResults = driver.findElements(By.cssSelector("img[alt='left-icon']")).size();
            return totalResults;
        }

        try {
            // Wait for the last page button to be clickable only if pagination buttons are present

            WebElement lastPageButton = waitForPagination.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[normalize-space(text())='" + lastPage + "']")

                    )

            );
            System.out.println("👆 Clicking on last page button: " + lastPageButton.getText());

            // Scroll to the element to make sure it's in view
            scrollToElement(lastPageButton);

            // Try normal user-like click
            try {
                actions.moveToElement(lastPageButton).click().perform();
            } catch (ElementClickInterceptedException e) {
                System.out.println("🔁 Click intercepted. Retrying using JavaScript click.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lastPageButton);
            }
            lastPageButton.click();

            Thread.sleep(1000);
            // Ensure the left-icon is present, meaning results have been loaded
            waitForPagination.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img[alt='left-icon']")));
            waitForPagination.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("img[alt='left-icon']"), 0));

            Thread.sleep(2000);
            // Calculate total results by counting the number of visible left icons
            int lastPageSize = driver.findElements(By.cssSelector("img[alt='left-icon']")).size();
            int total = (lastPage - 1) * 50 + lastPageSize;
            System.out.println("✅ Total results: " + total);
            return total;
        } catch (TimeoutException e) {
            System.out.println("❌ Timeout while waiting for pagination or elements: " + e.getMessage());
            return 0;
        } catch (Exception e) {
            System.out.println("❌ Error occurred: " + e.getMessage());
            return 0;
        }
    }


    private int getResultsWithoutPagination() throws InterruptedException {
        Thread.sleep(1000);
        int totalResults = driver.findElements(By.cssSelector("img[alt='left-icon']")).size();
        System.out.println("✅ Total results (no pagination): " + totalResults);
        return totalResults;
    }

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}