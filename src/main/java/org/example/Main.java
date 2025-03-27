package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
public class Main {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://6pm.com");
        WebElement bugs = driver.findElement(UsedWebElements.bugs);
        WebElement luggage = driver.findElement(UsedWebElements.luggage);
        HelperClass.openCategory(bugs, luggage, driver);
        WebElement production = driver.findElement(UsedWebElements.production);
        List<WebElement> products = production.findElements(UsedWebElements.products);
        HelperClass.printProductPriceAndName(products);
        Thread.sleep(1000);
        HelperClass.randomItemFromListIfExist(products);
        Thread.sleep(3000);
        WebElement addToCard = driver.findElement(UsedWebElements.addToCard);
        HelperClass.addToCard(addToCard);
        Thread.sleep(20000);
        WebElement quantityOfBascet = driver.findElement(UsedWebElements.quantityOfBascet);
        int counter = HelperClass.countOfBasket(quantityOfBascet);
        Thread.sleep(1000);
        System.out.println(counter + "item in bascet");

        WebElement removeItem = driver.findElement(UsedWebElements.removeItem);

        HelperClass.removeFromBascet(removeItem);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Wait up to 15 seconds
        WebElement getSignInlink = wait.until(ExpectedConditions.elementToBeClickable(UsedWebElements.getSignInlink));
        System.out.println("Link: " + getSignInlink.getAttribute("href"));
        driver.quit();
    }

}