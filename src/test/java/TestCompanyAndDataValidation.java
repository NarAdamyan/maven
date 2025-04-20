//import org.example.Company;
//import org.example.SearchResultPage;
//import org.example.Tabs;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.apache.commons.lang3.RandomStringUtils;
//public class TestCompanyAndDataValidation extends TestClass{
//        @Test
//        public void testCompanyAndDataValidation() throws InterruptedException {
//            // Step 1-8: Select the category and perform search
//            SearchResultPage resultPage = homePage.selectRadioButtonOnTab(Tabs.Companies)
//                    .selectIndustryCategory(Tabs.Information_Technologies)
//                    .enterSearchButton();
//
//            // Step 1: Perform search with random keyword and validate results are empty
//            String randomKeyword = RandomStringUtils.randomAlphabetic(8);
//            performSearchAndValidateEmptyResults(resultPage, randomKeyword);
//
//            // Step 2: Perform search with "ser" and validate all results contain the keyword
//            performSearchAndValidateKeyword(resultPage, "ser");
//
//                // Step 3: Validate company details between pages
//            String expectedDetails = resultPage.getRandomCompanyDetails();
//            Company companyPage = resultPage.clickRandomPage();
//            validateCompanyDetails(expectedDetails, companyPage);
//
//            // Step 4: Validate industry detail consistency
//            validateIndustryDetails(companyPage);
//        }
//
//        // Helper method to perform search with random keyword and validate empty results
//        private void performSearchAndValidateEmptyResults(SearchResultPage resultPage, String randomKeyword) throws InterruptedException {
//            resultPage.enterKeywordInSearchField(randomKeyword)
//                    .submitSearchButton();
//            // Wait for the results to be loaded and assert that no companies match the random keyword
//            Assertions.assertTrue(resultPage.getNamesOfCompany().isEmpty(),
//                    "Result should be empty for random search with keyword: " + randomKeyword);
//        }
//
//        // Helper method to perform search and validate keyword presence in all company names
//        private void performSearchAndValidateKeyword(SearchResultPage resultPage, String keyword) throws InterruptedException {
//            resultPage.clearSearchField()
//                    .enterKeywordInSearchField(keyword)
//                    .submitSearchButton();
//
//            Assertions.assertTrue(resultPage.getNamesOfCompany()
//                            .stream().allMatch(name -> name.contains(keyword.toLowerCase())),
//                    String.format("All company names should contain the search word '%s'", keyword));
//        }
//
//        // Helper method to validate company details between pages
//        private void validateCompanyDetails(String expectedDetails, Company companyPage) {
//            String actualDetails = companyPage.getCompanyDetails();
//            Assertions.assertEquals(expectedDetails, actualDetails,
//                    "Company details in SearchResultPage should match those in CompanyPage");
//        }
//
//        // Helper method to validate that the industry details are the same across pages
//        private void validateIndustryDetails(Company companyPage) {
//            String expectedIndustry = homePage.getIndustryDetail();
//            String actualIndustry = companyPage.getIndustryDetail();
//            Assertions.assertEquals(expectedIndustry, actualIndustry,
//                    "Industry name of company should be the same as selected industry category");
//        }
//    }
//
