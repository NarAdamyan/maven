import org.example.HelperClass;
import org.openqa.selenium.WebElement;
import org.example.UsedWebElements;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("https://6pm.com");
    }

    @Test
    @Tag("bags")
    void testOpenCategory() throws InterruptedException {
        WebElement bugs = driver.findElement(UsedWebElements.bugs);
        WebElement luggage = driver.findElement(UsedWebElements.luggage);
        assertDoesNotThrow(() -> HelperClass.openCategory(bugs, luggage, driver));
    }

    @Tag("bags")
    @Test
    void testPrintProductPriceAndName() throws InterruptedException {
        WebElement bugs = driver.findElement(UsedWebElements.bugs);
        WebElement luggage = driver.findElement(UsedWebElements.luggage);
        HelperClass.openCategory(bugs, luggage, driver);
        WebElement production = driver.findElement(UsedWebElements.production);
        List<WebElement> products = production.findElements(UsedWebElements.products);
        assertFalse(products.isEmpty(), "Product list should not be empty.");
        assertDoesNotThrow(() -> HelperClass.printProductPriceAndName(products));
    }

    @Tag("bags")
    @Test
    void testandomItemFromListIfExist() throws InterruptedException {
        WebElement bugs = driver.findElement(UsedWebElements.bugs);
        WebElement luggage = driver.findElement(UsedWebElements.luggage);
        HelperClass.openCategory(bugs, luggage, driver);
        WebElement production = driver.findElement(UsedWebElements.production);
        List<WebElement> products = production.findElements(UsedWebElements.products);
        assertFalse(products.isEmpty(), "Product list should not be empty.");
        assertDoesNotThrow(() -> HelperClass.randomItemFromListIfExist(products));

    }

    @Tag("bags")
    @Test
    void testaddToCard() throws InterruptedException {
        WebElement bugs = driver.findElement(UsedWebElements.bugs);
        WebElement luggage = driver.findElement(UsedWebElements.luggage);
        HelperClass.openCategory(bugs, luggage, driver);
        Thread.sleep(1000);
        WebElement production = driver.findElement(UsedWebElements.production);
        List<WebElement> products = production.findElements(UsedWebElements.products);
        assertFalse(products.isEmpty(), "Product list should not be empty.");
        HelperClass.randomItemFromListIfExist(products);
        Thread.sleep(3000);
        WebElement addToCard = driver.findElement(UsedWebElements.addToCard);
        assertDoesNotThrow(() -> HelperClass.addToCard(addToCard));
    }

    @Tag("bags")
    @Test
    void testprintProductPriceAndName() throws InterruptedException {
        WebElement bugs = driver.findElement(UsedWebElements.bugs);
        WebElement luggage = driver.findElement(UsedWebElements.luggage);
        HelperClass.openCategory(bugs, luggage, driver);
        WebElement production = driver.findElement(UsedWebElements.production);
        List<WebElement> products = production.findElements(UsedWebElements.products);
        assertFalse(products.isEmpty(), "Product list should not be empty.");
        assertDoesNotThrow(() -> HelperClass.printProductPriceAndName(products));
    }

    @Tag("bags")
    @Test
    void removeFromCard() throws InterruptedException {
        WebElement bugs = driver.findElement(UsedWebElements.bugs);
        WebElement luggage = driver.findElement(UsedWebElements.luggage);
        HelperClass.openCategory(bugs, luggage, driver);
        Thread.sleep(1000);
        WebElement production = driver.findElement(UsedWebElements.production);
        List<WebElement> products = production.findElements(UsedWebElements.products);
        assertFalse(products.isEmpty(), "Product list should not be empty.");
        HelperClass.randomItemFromListIfExist(products);
        Thread.sleep(3000);
        WebElement addToCard = driver.findElement(UsedWebElements.addToCard);
        HelperClass.addToCard(addToCard);
        Thread.sleep(1000);
        Thread.sleep(1000);
        WebElement quantityOfBascet = driver.findElement(UsedWebElements.quantityOfBascet);
        int counter = HelperClass.countOfBasket(quantityOfBascet);
        assertEquals(1, counter, "Basket count should match expected value.");
        WebElement removeItem = driver.findElement(UsedWebElements.removeItem);
        assertDoesNotThrow(() -> HelperClass.removeFromBascet(removeItem));
    }

    @Tag("bags")
    @Test
    void TestPrintSignInLink() throws InterruptedException{
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait for bugs and luggage elements to be present
        WebElement bugs = wait.until(ExpectedConditions.presenceOfElementLocated(UsedWebElements.bugs));
        WebElement luggage = wait.until(ExpectedConditions.presenceOfElementLocated(UsedWebElements.luggage));

        // Open category
        HelperClass.openCategory(bugs, luggage, driver);

        // Wait for the production element
        WebElement production = wait.until(ExpectedConditions.presenceOfElementLocated(UsedWebElements.production));

        // Get the product list
        List<WebElement> products = production.findElements(UsedWebElements.products);
        assertFalse(products.isEmpty(), "Product list should not be empty.");

        // Select a random product if it exists
        HelperClass.randomItemFromListIfExist(products);

        // Wait for the 'Add to Cart' button
        WebElement addToCard = wait.until(ExpectedConditions.elementToBeClickable(UsedWebElements.addToCard));
        HelperClass.addToCard(addToCard);

        // Wait for the 'Sign In' link and print its href
        WebElement getSignInlink = wait.until(ExpectedConditions.visibilityOfElementLocated(UsedWebElements.getSignInlink));
        System.out.println("Link: " + getSignInlink.getAttribute("href"));
    }


    @Test
    @Tag("clothing")
    void testSelectFilter() throws InterruptedException {
        WebElement clothing = driver.findElement(UsedWebElements.clothing);
        WebElement shirts = driver.findElement(UsedWebElements.shirts);
        HelperClass.openCategory(clothing, shirts, driver);
        Thread.sleep(1000);
        WebElement color = driver.findElement(UsedWebElements.color);
        WebElement brown = driver.findElement(UsedWebElements.brown);
        assertDoesNotThrow(() -> HelperClass.filter(color, brown));
    }

    @Test
    @Tag("clothing")
    void testRemoveSelectedFilter() throws InterruptedException {
        WebElement clothing = driver.findElement(UsedWebElements.clothing);
        WebElement shirts = driver.findElement(UsedWebElements.shirts);
        HelperClass.openCategory(clothing, shirts, driver);
        Thread.sleep(1000);
        WebElement color = driver.findElement(UsedWebElements.color);
        WebElement brown = driver.findElement(UsedWebElements.brown);
        HelperClass.filter(color, brown);
        Thread.sleep(1000);
        driver.findElement(UsedWebElements.removeSelectedBrown).click();
        Thread.sleep(1000);
        driver.navigate().refresh();
        Thread.sleep(1000);
        List<WebElement> selectedColors = driver.findElements(UsedWebElements.countOfSelectedColors); // Finds all selected colors
        int a = selectedColors.size(); // Returns the count
        assertEquals(0, a, "filtered elements mismatch");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}

