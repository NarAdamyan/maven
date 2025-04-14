import org.example.WebDriverManager;
import org.example.HeaderFooter;
import org.example.HomePage;
import org.example.SearchResultPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class TestClass {
    WebDriverManager webDriverManager;
    HomePage homePage;
    WebDriver driver;
    SearchResultPage searchResultPage;
    HeaderFooter headerFooter;

    @BeforeEach
    public void setUp(){
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        homePage = new HomePage(driver);
        headerFooter=new HeaderFooter(driver);
        searchResultPage=new SearchResultPage(driver);
    }

    @AfterEach
    public void tearDown(){
        webDriverManager.quitDriver();
    }
}
