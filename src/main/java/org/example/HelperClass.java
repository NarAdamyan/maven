package org.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;

public class HelperClass {
    public static void openCategory(WebElement tag, WebElement category, WebDriver driver) throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(tag).perform();
        Thread.sleep(500);
        category.click();
        Thread.sleep(500);
    }

    public static void filter(WebElement type, WebElement subtype) throws InterruptedException {
        type.click();
        Thread.sleep(500);
        subtype.click();
    }

    public static WebElement randomItemFromListIfExist(List<WebElement> products) {
        if (products == null || products.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(products.size());
        WebElement randomItem = products.get(randomIndex);
        randomItem.click();
        return randomItem;
    }

    public static void printProductPriceAndName(List<WebElement> products) {
        for (WebElement product : products) {
            String name = product.findElement(UsedWebElements.getProductName).getText();
            String price = product.findElement(UsedWebElements.getProductPrice).getText();
            System.out.println("Product name: " + name + "Product price" + price);
        }
    }

    public static void addToCard(WebElement addToCard) {
        addToCard.click();
    }

    public static void removeFromBascet(WebElement removedItem) {
        removedItem.click();
    }

    public static int countOfBasket(WebElement quantityBasket) {
        try {
            Select select = new Select(quantityBasket);
            String selectedText = select.getFirstSelectedOption().getText().trim();
            int counter = Integer.parseInt(selectedText);
            if (counter == 0) {
                System.out.println("The basket is empty");
            }
            return counter;
        } catch (NumberFormatException e) {
            System.out.println("Error: Basket quantity is not a valid number!");
            return 0;
        }
    }
}