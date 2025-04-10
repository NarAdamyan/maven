import org.example.Company;
import org.example.Tabs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class TestCompanies extends TestClass {
    @Test
    public void testHomework()throws InterruptedException {
        // Step 1: Go to Companies from header, filter by Sport
        headerFooter.getNavbarTab(Tabs.Companies)
                .selectFilterIndustry(Tabs.Filter_Sport);

        // Step 2: Get filtered company list
        List<Company> filteredList = searchResultPage.getCompaniesList();

        // Step 3: Click "Hiring" filter
        searchResultPage.clickHiringButton();
        List<Company> filteredHiredList = searchResultPage.getCompaniesList();

        // Step 4: Navigate via footer to "View all companies"
        headerFooter.selectFooterCategoryViewAllCompanies();

        // Step 4.5: Ensure Companies page is loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Companies')]")));

        // Step 5: Filter again by "Sport"
        searchResultPage.selectFilterIndustry(Tabs.Filter_Sport);
        List<Company> filteredList2 = searchResultPage.getCompaniesList();

        // Step 6: Click "Hiring" again
        searchResultPage.clickHiringButton();
        List<Company> filteredHiredList2 = searchResultPage.getCompaniesList();

        // Step 7: Compare both sets
        Assertions.assertTrue(filteredList.equals(filteredList2), "Filtered companies mismatch after footer navigation");
        Assertions.assertTrue(filteredHiredList.equals(filteredHiredList2), "Hiring filter results mismatch after footer nav");
    }
}