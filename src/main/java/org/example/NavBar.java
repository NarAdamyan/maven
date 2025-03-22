package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class NavBar {
    WebDriver driver;
    Actions actions;
    UsedWebElements elements;

    public NavBar(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.elements = new UsedWebElements(driver);
    }
    public void openCategory(WebElement button, WebElement category) throws InterruptedException {
        actions.moveToElement(button).perform();
        Thread.sleep(500);
        category.click();
        Thread.sleep(500);
    }
}
