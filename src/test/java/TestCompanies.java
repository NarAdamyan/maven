import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.example.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class TestCompanies extends TestClass {
    @Attachment(value = "Company List", type = "text/plain")
    public String attachCompanyList(List<Company> companies) {
        return companies.stream()
                .map(Company::toString)
                .collect(Collectors.joining("\n"));
    }
    @Test
    public void testSportIndustryFilterConsistencyAcrossNavigation() throws InterruptedException{
        driver.get("https://staff.am/en");
        goToCompaniesAndFilterBySport();
        List<Company> filteredList = getFilteredCompanyList();
        clickHiringFilter();
        List<Company> filteredHiredList = getFilteredCompanyList();
        attachCompanyList(filteredList);
        attachCompanyList(filteredHiredList);

        navigateToAllCompaniesFromFooter();
        waitForCompaniesPage();
        filterAgainBySport();
        List<Company> filteredList2 = getFilteredCompanyList();
        clickHiringFilter();
        List<Company> filteredHiredList2 = getFilteredCompanyList();
        compareResults(filteredList, filteredList2, filteredHiredList, filteredHiredList2);
    }

    @Step("Step 1: Navigate to Companies tab and filter by Sport")
    void goToCompaniesAndFilterBySport() {
        header.getNavbarTab("Companies");
        filters.selectFilterSection("Filter By Industry").selectOption("Sport");
    }

    @Step("Step 2: Get filtered company list")
    List<Company> getFilteredCompanyList() throws InterruptedException{
        Thread.sleep(5000);
        List<Company> companies = searchResultPage.getCompaniesList();
        return companies;
    }


    @Step("Step 3 & 6: Click 'Hiring' filter")
    void clickHiringFilter() {
        searchResultPage.clickHiringButton();
    }

    @Step("Step 4: Navigate to 'View All Companies' via footer")
    void navigateToAllCompaniesFromFooter() {
        footer.selectFooterCategoryViewAllCompanies();
    }

    @Step("Step 4.5: Wait for Companies page to load")
    void waitForCompaniesPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Companies')]")));
    }

    @Step("Step 5: Filter again by Sport industry")
    void filterAgainBySport()throws  InterruptedException {
        filters.selectFilterSection("Filter By Industry").selectOption("Sport");
        Thread.sleep(1000);
    }

    @Step("Step 7: Compare both filtered lists before and after footer navigation")
    void compareResults(List<Company> before, List<Company> after, List<Company> beforeHiring, List<Company> afterHiring) {
        Assertions.assertTrue(before.equals(after), "Filtered companies mismatch after footer navigation");
        Assertions.assertTrue(beforeHiring.equals(afterHiring), "Hiring filter results mismatch after footer nav");
    }
}
