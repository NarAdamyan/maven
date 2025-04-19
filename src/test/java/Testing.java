import org.example.Filters;
import org.example.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.stream.Stream;

class Testing extends TestClass {

    // ✅ Data provider to return random filter options
    static Stream<Arguments> filterDataProvider() {
        Map<String, String> filterData = Filters.getRandomOptionPerFilter(WebDriverManager.getInstance().getDriver());

        return filterData.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    @ParameterizedTest
    @MethodSource("filterDataProvider")
    void testSingleFilterAndTwoFilters(String filterCategory1, String optionToSelect1) throws InterruptedException {
        WebDriver driver = WebDriverManager.getInstance().getDriver();
        driver.get("https://www.staff.am/en/jobs");

        Filters filters = new Filters();

        // Step 1: Apply the first filter (single filter)
        filters.selectFilterSection(filterCategory1);
//        Thread.sleep(1000);

        // Step 2: Select the first filter option
        WebElement optionElement1 = filters.selectOption(optionToSelect1);
        Thread.sleep(1000);

        // Step 3: Get expected count from option text
        int expectedSingle = filters.returnFilteredElementsCount(optionElement1);

        // Step 4: Get actual number of filtered job cards for single filter
        int actualSingle = filters.getFilteredResultsCount();
        System.out.printf("🧪 Testing with single filter → [%s]: [%s]%n", filterCategory1, optionToSelect1);

        // Step 5: Assert for single filter
        Assertions.assertEquals(expectedSingle, actualSingle,
                "❌ Mismatch for [" + filterCategory1 + "] → [" + optionToSelect1 + "]: expected " + expectedSingle + ", but got " + actualSingle);

        // Step 6: Now apply two filters
//        Thread.sleep(1000);
        Map<String, String> filterData = Filters.getRandomOptionPerFilter(driver); // Get new random filter data
        String filterCategory2 = (String) filterData.keySet().toArray()[0];
        String optionToSelect2 = filterData.get(filterCategory2);

        // Step 7: Apply the second filter (two filters)
        filters.selectFilterSection(filterCategory2);
//        Thread.sleep(1000);

        // Step 8: Select the second filter option
        WebElement optionElement2 = filters.selectOption(optionToSelect2);
        Thread.sleep(1000);

        // Step 9: Get expected count from option text for two filters
        int expectedTwo = filters.returnFilteredElementsCount(optionElement2);

        // Step 10: Get actual number of filtered job cards for two filters
        int actualTwo = filters.getFilteredResultsCount();
        System.out.printf("🧪 Testing with two filters → [%s]: [%s] and [%s]: [%s]%n", filterCategory1, optionToSelect1, filterCategory2, optionToSelect2);

        // Step 11: Assert for two filters
        Assertions.assertEquals(expectedTwo, actualTwo,
                "❌ Mismatch for [" + filterCategory1 + "] → [" + optionToSelect1 + "] and [" + filterCategory2 + "] → [" + optionToSelect2 + "]: expected " + expectedTwo + ", but got " + actualTwo);
        // Step 12: Uncheck the first filter (toggle the same element)
        filters.selectFilterSection(filterCategory1);
        filters.selectOption(optionToSelect1);
        Thread.sleep(2000);


// Step 13: Get filtered results count after unchecking the first filter
        int countAfterUncheck = filters.getFilteredResultsCount();
        System.out.printf("🔁 After unchecking [%s]: [%s], Results count: %d%n", filterCategory1, optionToSelect1, countAfterUncheck);

// Step 14: Assert that count after unchecking is equal to filtering with only second filter
        Assertions.assertEquals(filters.returnFilteredElementsCount(optionElement2), countAfterUncheck,
                "❌ After unchecking first filter, expected count with only second filter not matched.");

    }
}
