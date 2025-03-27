package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class RunClothingsForMen {

    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://6pm.com");
        WebElement clothing=driver.findElement(UsedWebElements.clothing);
        WebElement shirts= driver.findElement(UsedWebElements.shirts);
        HelperClass.openCategory(clothing,shirts,driver);
        Thread.sleep(1000);
        WebElement color=driver.findElement(UsedWebElements.color);
        WebElement brown=driver.findElement(UsedWebElements.brown);
        HelperClass.filter(color,brown);
        Thread.sleep(20000);
        driver.findElement(UsedWebElements.removeSelectedBrown).click();
        Thread.sleep(1000);
        List<WebElement> selectedColors = driver.findElements(UsedWebElements.countOfSelectedColors); // Finds all selected colors
        int a= selectedColors.size(); // Returns the count
    }
}