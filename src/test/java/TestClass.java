import org.example.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;

public class TestClass {
    static WebDriver driver;
    static WebDriverManager webDriverManager;
RegisterPage registerPage;
    HomePage homePage;
    SearchResultPage searchResultPage;
    HeaderFooter headerFooter;
    Filters filters;

    @BeforeAll

    static void globalSetup() {
        webDriverManager = WebDriverManager.getInstance();
        driver = webDriverManager.getDriver();
    }

    @BeforeEach
    void setup() {
        
        homePage = new HomePage();
        searchResultPage = new SearchResultPage();
        headerFooter = new HeaderFooter();
        filters = new Filters();
        registerPage=new RegisterPage();
    }

    @AfterAll
    static void tearDown() {
        webDriverManager.quitDriver();
    }

    @RegisterExtension
    static TestResultLogger watcher = new TestResultLogger(driver);
}
