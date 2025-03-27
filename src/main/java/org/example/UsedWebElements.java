package org.example;

import org.openqa.selenium.By;

public class UsedWebElements {

    public final static By bugs = (By.cssSelector("a[href=\"/c/bags\"]"));
    public final static By luggage = By.cssSelector("a[href*=\"luggage\"]");
    public final static By production = By.cssSelector("#products");
    public final static By addToCard = By.id("add-to-cart-button");
    public final static By removeItem = By.cssSelector("button[aria-label=\"Remove Item\"]");
    public final static By products = By.cssSelector("article");
    public final static By getProductName = By.xpath(".//dt[contains(text(),'Product Name')]/following-sibling::dd");
    public final static By getSignInlink = By.cssSelector("a[href*='signin']");
    public final static By getProductPrice = By.xpath(".//dt[contains(text(),'Price')]/following-sibling::dd");
    public final static By quantityOfBascet = By.cssSelector("select[name=\"quantity\"]");
    public final static By clothing = By.cssSelector("a[href*='clothing']");
    public final static By shirts = By.xpath("//a[contains(text(), 'T-Shirts')]");
    public final static By color = By.id("colorFacet");
    public final static By brown = By.xpath("//span[contains(text(), 'Brown')]");
    public final static By removeSelectedBrown = By.xpath("//*[@aria-label='Remove Brown filter']");
    public final static By countOfSelectedColors = By.cssSelector("li[class=\"Nu-z\"]");

}
