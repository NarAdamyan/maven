package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsedWebElements {
    private WebDriver driver;

    By bugs = (By.cssSelector("a[href=\"/c/bags\"]"));
    By luggage = By.cssSelector("a[href*=\"luggage\"]");
    By production = By.cssSelector("#products");
    By addToCard=By.id("add-to-cart-button");
    By removeItem=By.cssSelector("button[aria-label=\"Remove Item\"]");
    By products = By.cssSelector("article");
    By getProductName=By.xpath(".//dt[contains(text(),'Product Name')]/following-sibling::dd");
    By getSignInlink = By.cssSelector("a[href*='signin']");
    By getProductPrice=By.xpath(".//dt[contains(text(),'Price')]/following-sibling::dd");

    public UsedWebElements(WebDriver driver) {
        this.driver = driver;
    }
}
