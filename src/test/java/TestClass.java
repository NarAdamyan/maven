import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class TestClass {
    WebDriverManager webDriverManager;
    HomePage homePage;
    WebDriver driver;
    SearchResultPage searchResultPage;
    HeaderFooter headerFooter;
    Filters filters;

    @BeforeEach
    public void setUp() {
        webDriverManager = WebDriverManager.getInstance(); // ✅ Singleton call
        driver = webDriverManager.getDriver();             // ✅ Get driver from it

        // Pass driver to your page classes
        homePage = new HomePage();
        headerFooter = new HeaderFooter();
        searchResultPage = new SearchResultPage();
        filters = new Filters();
    }

    @AfterEach
    public void tearDown() {
        webDriverManager.quitDriver(); // ✅ Properly quit the Singleton driver
    }
}
