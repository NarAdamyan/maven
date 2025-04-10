package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class GetText {
    UsedWebElements elements;
    private WebDriver driver;
    public GetText() {
        this.elements = new UsedWebElements(driver);
    }
    public void getProductText(List<WebElement> products){
        for(WebElement product:products){
            String name = product.findElement(elements.getProductName).getText();
            String price = product.findElement(elements.getProductPrice).getText();
            System.out.println("Product name: "+name +"Product price"+price);
        }
    }
}
