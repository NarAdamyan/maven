import org.example.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;


public class TestClass {
    static WebDriver driver;
    static WebDriverManager webDriverManager;
    RegisterPage registerPage;
    HomePage homePage;
    SearchResultPage searchResultPage;
    Header header;
    Footer footer;
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
        header = new Header();
        footer=new Footer();
        filters = new Filters();
        registerPage = new RegisterPage();
    }

    @AfterAll
    static void tearDown() {
        webDriverManager.quitDriver();
    }

}
