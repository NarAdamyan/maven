import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.Company;
import org.example.SearchResultPage;
import org.example.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class TestCompanyAndDataValidation extends TestClass  {

    private SearchResultPage searchResultPage;

    @Test
    public void testCompanyAndDataValidation() throws InterruptedException {
        openAndNavigate();
        selectAndPerform();
        fillSearchInput();
        validateCompaniesContainWord();
        getDetails();
    }

    @Step("Step 1: Open browser and navigate to the link")
    void openAndNavigate() {
        WebDriver driver = WebDriverManager.getInstance().getDriver();
        driver.get("https://www.staff.am/en");
    }

    @Step("Step 2: Select the category and perform search")
    void selectAndPerform() {
        searchResultPage = homePage
                .selectRadioButtonOnTab("Companies")
                .selectIndustryCategory("Information technologies")
                .enterSearchButton();
    }

    @Step("Step 3: Perform search with random keyword and validate results are empty")
    void fillSearchInput() throws InterruptedException {
        String randomKeyword = RandomStringUtils.randomAlphabetic(8);
        performSearchAndValidateEmptyResults(searchResultPage, randomKeyword);
    }

    @Step("Step 4: Perform search with 'ser' and validate all results contain the keyword")
    void validateCompaniesContainWord() throws InterruptedException {
        performSearchAndValidateKeyword(searchResultPage, "ser");
    }

    @Step("Step 5: Validate company details between pages")
    void getDetails() {
        String expectedDetails = searchResultPage.getRandomCompanyDetails();
        Company companyPage = searchResultPage.clickRandomPage();
        validateCompanyDetails(expectedDetails, companyPage);
        validateIndustryDetails(companyPage);
    }

    // ✅ Helper method: validate empty results for random keyword
    @Step("Helper: Search random keyword and check if results are empty")
    private void performSearchAndValidateEmptyResults(SearchResultPage resultPage, String randomKeyword) throws InterruptedException {
        resultPage.enterKeywordInSearchField(randomKeyword)
                .submitSearchButton();

        Assertions.assertTrue(resultPage.getNamesOfCompany().isEmpty(),
                "Result should be empty for random search with keyword: " + randomKeyword);
    }

    // ✅ Helper method: validate all results contain the keyword
    @Step("Helper: Search keyword and check all company names contain it")
    private void performSearchAndValidateKeyword(SearchResultPage resultPage, String keyword) throws InterruptedException {
        resultPage.clearSearchField()
                .enterKeywordInSearchField(keyword)
                .submitSearchButton();

        Assertions.assertTrue(
                resultPage.getNamesOfCompany().stream()
                        .allMatch(name -> name.toLowerCase().contains(keyword.toLowerCase())),
                String.format("All company names should contain the search word '%s'", keyword)
        );
    }

    // ✅ Helper method: validate details consistency
    private void validateCompanyDetails(String expectedDetails, Company companyPage) {
        String actualDetails = companyPage.getCompanyDetails();
        Assertions.assertEquals(expectedDetails, actualDetails,
                "Company details in SearchResultPage should match those in CompanyPage");
    }

    // ✅ Helper method: validate industry name
    private void validateIndustryDetails(Company companyPage) {
        String expectedIndustry = homePage.getIndustryDetail();
        String actualIndustry = companyPage.getIndustryDetail();
        Assertions.assertEquals(expectedIndustry, actualIndustry,
                "Industry name of company should be the same as selected industry category");
    }
}