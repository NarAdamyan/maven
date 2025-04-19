//import org.example.Filter;
//import org.example.Filters;
//import org.example.WebDriverManager;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//
//import java.util.Map;
//import java.util.stream.Stream;
//
//public class Testing extends TestClass {
//
//    // Provide random filter data
//    static Stream<Arguments> filterDataProvider() {
//        WebDriver driver = WebDriverManager.getInstance().getDriver();
//        Filter filter = new Filter();
//        Map<String, String> randomFilters = filter.getRandomOptionPerFilter(driver);
//
//        if (randomFilters.isEmpty()) {
//            throw new RuntimeException("❌ No filter data found!");
//        }
//
//        return randomFilters.entrySet().stream()
//                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
//    }
//
//    @ParameterizedTest
//    @MethodSource("filterDataProvider")
//    void testFilterWithRandomOption(String filterCategory, String optionToSelect) throws InterruptedException {
//        Filters filters = new Filters();
//
//        // Step 1: Expand the filter section
//        filters.selectFilterSection(filterCategory);
//
//        // Step 2: Select the option
//        WebElement optionElement = filters.selectOption(optionToSelect);
//
//        // Step 3: Get expected count from option text
//        int expected = filters.returnFilteredElementsCount(optionElement);
//
//        // Step 4: Get actual number of filtered job cards
//        int actual = filters.getFilteredResultsCount();
//
//        // Step 5: Assert expected = actual
//        Assertions.assertEquals(expected, actual,
//                "❌ Mismatch for [" + filterCategory + "] → [" + optionToSelect + "]: expected " + expected + ", but got " + actual);
//    }
//}
