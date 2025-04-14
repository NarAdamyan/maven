package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filters extends BasePage {

    // ✅ Constructor with PageFactory
    public Filters(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // ✅ Using @FindBy for elements that exist immediately after page load

    @FindBy(xpath = "//div[contains(text(), 'Your search returned no results')]")
    WebElement noResultsMsg;

    @FindBy(css = "a[role='button']")
    List<WebElement> paginationLinks;

    @FindBy(css = "img[alt='left-icon']")
    List<WebElement> companyResults;

    @FindBy(xpath = "//button[contains(@class, 'clear')]")
    List<WebElement> clearButtons;


    // ✅ Filters defined statically
    public static Map<String, List<String>> getFilters() {
        return Map.of(
                "Job category", List.of(
                        "Banking/credit",
                        "Sales/service management",
                        "Accounting/Bookkeeping/Cash register",
                        "Other IT",
                        "Marketing/Advertising",
                        "Software development",
                        "Administrative/office-work",
                        "Finance Management"
                ),
                "Job special tag", List.of(
                        "Bachelor's degree",
                        "Fresh graduates",
                        "Training provided",
                        "Student-friendly",
                        "Flexible hours",
                        "Professional certification"
                ),
                "Specialist level", List.of(
                        "Student",
                        "Junior",
                        "Mid level",
                        "Senior",
                        "C level",
                        "Not defined"
                ),
                "Job salary", List.of(
                        "Mentioned",
                        "Not Mentioned"
                ),
                "Job types", List.of(
                        "Full time",
                        "Part time",
                        "Internship",
                        "Training",
                        "Fixed term contract",
                        "Other",
                        "Tender",
                        "Remote"
                ),
                "Job terms", List.of(
                        "Permanent",
                        "Temporary",
                        "Freelance",
                        "Other",
                        "Contract",
                        "Internship"
                ),
                "By cities", List.of(
                        "Yerevan",
                        "Gyumri",
                        "Abovyan",
                        "Vanadzor",
                        "Ashtarak",
                        "Artashat",
                        "Charentsavan",
                        "Kapan",
                        "Vagharshapat"
                )
        );
    }

    public int returnFilteredElementsCount(WebElement option) {
        String number = "";
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(option.getText());
        if (matcher.find()) {
            number = matcher.group(1);
        }
        return Integer.parseInt(number);
    }

    public int returnSearchResulCardsCount() {
        try {
            WebElement message = wait.until(ExpectedConditions.visibilityOf(noResultsMsg));
            if (message.isDisplayed()) {
                System.out.println("No results found.");
                return 0;
            }
        } catch (TimeoutException e) {
            // Continue if no message found
        }

        int lastPage = 1;
        for (WebElement el : paginationLinks) {
            String text = el.getText().trim();
            if (text.matches("^\\d+$")) {
                int pageNum = Integer.parseInt(text);
                lastPage = Math.max(lastPage, pageNum);
            }
        }

        if (lastPage == 1) {
            System.out.println("Only 1 page. Count of result: " + companyResults.size());
            return companyResults.size();
        }

        // ❗ Dynamic element, still use driver.findElement here:
        WebElement lastPageButton = driver.findElement(By.xpath("//a[normalize-space(text())='" + lastPage + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastPageButton);
//        Thread.sleep(500);
        wait.until(ExpectedConditions.elementToBeClickable(lastPageButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lastPageButton);

        // Wait for new results to load
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("img[alt='left-icon']"), 0));

        List<WebElement> lastPageResults = driver.findElements(By.cssSelector("img[alt='left-icon']"));
        int total = (lastPage - 1) * 50 + lastPageResults.size();

        System.out.println("Total results: " + total);
        return total;
    }

}
