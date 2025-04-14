import org.example.Filters;
import org.example.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

class FiltersTest extends TestClass {

    static Stream<org.junit.jupiter.params.provider.Arguments> filterDataProvider() {
        Map<String, List<String>> filters = Filters.getFilters();
        Random random = new Random();
        return filters.entrySet().stream()
                .map(entry -> {
                    String filterName = entry.getKey();
                    List<String> options = entry.getValue();
                    String randomOption = options.get(random.nextInt(options.size()));
                    return org.junit.jupiter.params.provider.Arguments.of(filterName, randomOption);
                });
    }

    @ParameterizedTest
    @MethodSource("filterDataProvider")
    void testFilterWithRandomOption(String filterName, String optionToSelect) throws InterruptedException {
        // ✅ Use driver from TestClass
        driver.get("https://staff.am/en/jobs");

        Actions actions = new Actions(driver);
        Filters filters = new Filters(driver); // ✅ Uses the same driver

        // Scroll to the filter type section
        WebElement filterSection = filters.wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[text()='" + filterName + "']")
        ));
        actions.moveToElement(filterSection).perform();
        Thread.sleep(1000);

        // Click 'View more' if available
        List<WebElement> viewMoreButtons = driver.findElements(By.xpath("//button[contains(text(),'View more')]"));
        if (!viewMoreButtons.isEmpty()) {
            actions.moveToElement(viewMoreButtons.get(0)).click().perform();
            Thread.sleep(2000);
        }

        actions.moveToElement(filterSection).perform();
        Thread.sleep(1000);

        // Select option
        WebElement option = filters.wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='" + optionToSelect + "']")
        ));
        actions.moveToElement(option).click().perform();

        System.out.println("✅ Selected: " + filterName + " → " + optionToSelect);

        filters.wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//img[@alt='company-logo']")));
        Assertions.assertEquals(filters.returnFilteredElementsCount(option), filters.returnSearchResulCardsCount());
    }
}
