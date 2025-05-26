package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

abstract class BasePage {
    protected WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public BasePage() {
        this.driver = WebDriverManager.getInstance().getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);

    }
}