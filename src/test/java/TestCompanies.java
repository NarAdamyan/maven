import io.qameta.allure.Step;
import org.example.Company;
import org.example.Tabs;
import org.example.utils.AllureAttachments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TestCompanies extends TestClass {

    @Test
    public void testHomework() throws InterruptedException {
        driver.get("https://staff.am/en");

        goToCompaniesAndFilterBySport();

        List<Company> filteredList = getFilteredCompanyList();

        clickHiringFilter();
        List<Company> filteredHiredList = getFilteredCompanyList();

        navigateToAllCompaniesFromFooter();

        waitForCompaniesPage();

        filterAgainBySport();
        List<Company> filteredList2 = getFilteredCompanyList();

        clickHiringFilter();
        List<Company> filteredHiredList2 = getFilteredCompanyList();

        compareResults(filteredList, filteredList2, filteredHiredList, filteredHiredList2);
    }

    @Step("Step 1: Navigate to Companies tab and filter by Sport")
    void goToCompaniesAndFilterBySport() throws InterruptedException{
        headerFooter.getNavbarTab(Tabs.Companies)
                .selectFilterIndustry(Tabs.Filter_Sport);
    }
    @Step("Step 2: Get filtered company list")
    List<Company> getFilteredCompanyList() {
        List<Company> companies = searchResultPage.getCompaniesList();
        AllureAttachments.takeScreenshot("nnnnnnn",driver); // Attach screenshot
        return companies;
    }


    @Step("Step 3 & 6: Click 'Hiring' filter")
    void clickHiringFilter() {
        searchResultPage.clickHiringButton();
    }

    @Step("Step 4: Navigate to 'View All Companies' via footer")
    void navigateToAllCompaniesFromFooter() {
        headerFooter.selectFooterCategoryViewAllCompanies();
    }

    @Step("Step 4.5: Wait for Companies page to load")
    void waitForCompaniesPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Companies')]")));
    }

    @Step("Step 5: Filter again by Sport industry")
    void filterAgainBySport() throws InterruptedException{
        searchResultPage.selectFilterIndustry(Tabs.Filter_Sport);
    }

    @Step("Step 7: Compare both filtered lists before and after footer navigation")
    void compareResults(List<Company> before, List<Company> after, List<Company> beforeHiring, List<Company> afterHiring) {
        Assertions.assertTrue(before.equals(after), "Filtered companies mismatch after footer navigation");
        Assertions.assertTrue(beforeHiring.equals(afterHiring), "Hiring filter results mismatch after footer nav");
    }
}
