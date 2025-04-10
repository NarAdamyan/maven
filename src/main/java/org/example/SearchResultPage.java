package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SearchResultPage extends BasePage {
    private final By searchInput = By.xpath("//input[@placeholder='Enter keywords...']");
    private final By enterSearchButton = By.xpath("//div[text()='Search']");
    private final By companiesResult = By.xpath(".//img[@alt='company-logo']/following-sibling::div/div[1]");
    private final By viewMoreLoc = By.xpath("(//div[@tabindex]/div[text()= 'View more'])[1]");
    private final By hiringLoc = By.xpath("//div[text()='Hiring']");

    public static int randomCompany;
    public static List<WebElement> companies;


    public SearchResultPage(WebDriver driver){
        super(driver);
    }

    public SearchResultPage selectFilterIndustry(Tabs industryName) throws InterruptedException {
        Actions actions = new Actions(driver);
        Thread.sleep(5000);
        actions.click(driver.findElement(viewMoreLoc)).perform();
        Thread.sleep(5000);

        actions.click(driver.findElement(By.xpath(String.format("//span[text()='%s']", industryName.getDisplayName())))).perform();
        Thread.sleep(5000);
        return this;
    }
    public SearchResultPage enterKeywordInSearchField(String keyword){
        driver.findElement(searchInput).sendKeys(keyword);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(searchInput)));
        return this;
    }

    public SearchResultPage clearSearchField(){
        driver.findElement(searchInput).clear();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(searchInput)));
        return this;
    }

    public void submitSearchButton() throws InterruptedException {
        driver.findElement(enterSearchButton).click();
        Thread.sleep(3000);

    }

    public List<String> getNamesOfCompany() {
        List<String> names;
        names = driver.findElements(companiesResult)
                .stream()
                .map(el -> el.getText().toLowerCase())
                .collect(Collectors.toList());
        return names;

    }

    public String getRandomCompanyDetails(){

        companies = driver.findElements((companiesResult));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(companiesResult)));
        Random rand = new Random();
        randomCompany = rand.nextInt(companies.size());
        return getCompanyDetails(companies.get(randomCompany).getText());
    }


    public Company clickRandomPage() {
        Actions actions = new Actions(driver);
        actions.click(companies.get(randomCompany)).perform();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[@role='heading']"))));
        return new Company(driver);
    }


    public String getCompanyDetails(String nameCompany) {
        WebElement detail = driver.findElement(By.xpath(String.format("//div[contains(text(),'%s')]/ancestor-or-self::div[4]", nameCompany)));
        wait.until(ExpectedConditions.visibilityOf(detail));
        return detail.getText().toLowerCase();

    }
    public List<Company> getCompaniesList() {
        List<Company> companies = new ArrayList<>();
        List<String> companiesNames = getNamesOfCompany();
        wait.until(ExpectedConditions.visibilityOfElementLocated(companiesResult));

        for (String names : companiesNames) {
            System.out.println(names);
            companies.add(new Company(driver,names));
        }
        return companies;
    }

    public SearchResultPage clickHiringButton(){
        wait.until(ExpectedConditions.elementToBeClickable(hiringLoc)).click();
        return new SearchResultPage(driver);
    }
}