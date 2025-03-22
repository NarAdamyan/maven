package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://6pm.com");
        UsedWebElements elements = new UsedWebElements(driver);
        Actions actions = new Actions(driver);
        WebElement bugs = driver.findElement(elements.bugs);
        bugs.click();
        WebElement luggage = driver.findElement(elements.luggage);
        Thread.sleep(15000);
        NavBar navbar=new NavBar(driver);
        navbar.openCategory(bugs,luggage);
        Thread.sleep(10000);
        WebElement production = driver.findElement(elements.production);
        List<WebElement> products = production.findElements(elements.products);
        GetText getText = new GetText();
        getText.getProductText(products);
        Thread.sleep(1000);
        RandomGeneratedItem item = new RandomGeneratedItem();
        item.randomItem(products);
        Thread.sleep(3000);
        WebElement addToCard = driver.findElement(elements.addToCard);
        addToCard.click();
        Thread.sleep(20000);
        WebElement removeItem = driver.findElement(elements.removeItem);
        removeItem.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Wait up to 15 seconds
        WebElement getSignInlink = wait.until(ExpectedConditions.elementToBeClickable(elements.getSignInlink));
        System.out.println("Link: " + getSignInlink.getAttribute("href"));
        driver.quit();
    }
}