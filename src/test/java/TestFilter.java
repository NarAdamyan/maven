import io.qameta.allure.*;
import org.example.Filters;
import org.example.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.stream.Stream;

@Epic("Filter Feature")
@Feature("Filter Functionality")
class TestFilter extends TestClass {

    static Stream<Arguments> filterDataProvider() {
        Map<String, String> filterData = Filters.getRandomOptionPerFilter(WebDriverManager.getInstance().getDriver());

        return filterData.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    @ParameterizedTest
    @MethodSource("filterDataProvider")
    @DisplayName("Test single and double filter functionality")
    void testSingleFilterAndTwoFilters(String filterCategory1, String optionToSelect1) throws InterruptedException {
        Allure.step("Start filters test");
        WebDriver driver = WebDriverManager.getInstance().getDriver();
        driver.get("https://www.staff.am/en/jobs");
        Filters filters = new Filters();

        WebElement optionElement1 = applyFilterAndGetElement(filters, filterCategory1, optionToSelect1);
        int expectedSingle = getExpectedCount(filters, optionElement1);
        int actualSingle = getActualCount(filters);

        logInfo("🧪 Testing with single filter → [%s]: [%s]", filterCategory1, optionToSelect1);
        assertCount(expectedSingle, actualSingle, filterCategory1, optionToSelect1);
        Map<String, String> filterData = Filters.getRandomOptionPerFilter(driver);
        String filterCategory2 = (String) filterData.keySet().toArray()[0];
        String optionToSelect2 = filterData.get(filterCategory2);

        WebElement optionElement2 = applyFilterAndGetElement(filters, filterCategory2, optionToSelect2);
        int expectedTwo = getExpectedCount(filters, optionElement2);
        int actualTwo = getActualCount(filters);

        logInfo("🧪 Testing with two filters → [%s]: [%s] and [%s]: [%s]",
                filterCategory1, optionToSelect1, filterCategory2, optionToSelect2);
        assertCount(expectedTwo, actualTwo, filterCategory2, optionToSelect2);

        uncheckFilter(filters, filterCategory1, optionToSelect1);
        int countAfterUncheck = getActualCount(filters);

        logInfo("🔁 After unchecking [%s]: [%s], Results count: %d", filterCategory1, optionToSelect1, countAfterUncheck);
        Assertions.assertEquals(filters.returnFilteredElementsCount(optionElement2), countAfterUncheck,
                "❌ After unchecking first filter, expected count with only second filter not matched.");
    }

    @Test

    @DisplayName("Deliberately Failing Test for Screenshot")
    void deliberatelyFailingTest() {
        WebDriver driver = WebDriverManager.getInstance().getDriver();
        driver.get("https://www.staff.am/en/jobs");
        Assertions.fail("This test is designed to fail and trigger screenshot attachment.");
    }

    @Step("Apply filter: category = {0}, option = {1}")
    WebElement applyFilterAndGetElement(Filters filters, String category, String option) throws InterruptedException {
        filters.selectFilterSection(category);
        WebElement optionElement = filters.selectOption(option);
        Thread.sleep(1000); // Replace with wait if possible
        return optionElement;
    }

    @Step("Get expected count from element")
    int getExpectedCount(Filters filters, WebElement optionElement) {
        return filters.returnFilteredElementsCount(optionElement);
    }

    @Step("Get actual count of filtered job cards")
    int getActualCount(Filters filters) throws InterruptedException {
        return filters.getFilteredResultsCount();
    }

    @Step("Unchecking filter: category = {0}, option = {1}")
    void uncheckFilter(Filters filters, String category, String option) throws InterruptedException {
        filters.selectFilterSection(category);
        filters.selectOption(option); // toggling
        Thread.sleep(2000);
    }

    @Step("Assert expected = {0}, actual = {1} for [{2} → {3}]")
    void assertCount(int expected, int actual, String category, String option) {
        Assertions.assertEquals(expected, actual,
                "❌ Mismatch for [" + category + "] → [" + option + "]: expected " + expected + ", but got " + actual);
    }

    @Step("{0}")
    void logInfo(String message, Object... args) {
        System.out.printf((message) + "%n", args);
    }

}