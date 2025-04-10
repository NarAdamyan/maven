package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.Objects;

public class Company extends BasePage {
    private final By detailsLoc = By.xpath("//div[img[@alt=\"company-poster\"]]//following-sibling::div/div[3]");
    private final By namesLoc = By.xpath("//h1[@role='heading']");
    private final By industryLoc = By.xpath("//div[text() ='Industry']/following-sibling::div");
    String name;

    public Company(WebDriver driver) {
        super(driver);
    }

    public Company(WebDriver driver, String name) {
        super(driver);
        this.name = name;
    }

    public String getCompanyDetails() {
        String name = driver.findElement(namesLoc).getText().toLowerCase();
        String details = driver.findElement(detailsLoc).getText()
                .replace("(", "").replace(")", "");
        return name + "\n" + details;
    }

    public String getIndustryDetail() {
        return driver.findElement(industryLoc).getText().toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name);
    }
}
