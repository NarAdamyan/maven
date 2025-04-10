package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.Random;

public class RandomGeneratedItem {
    WebDriver driver;
    WebElement element;
    UsedWebElements elements=new UsedWebElements(driver);
    public void randomItem(List<WebElement> products){
        if (!products.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(products.size());
            products.get(randomIndex).click();
        }
    }
}
