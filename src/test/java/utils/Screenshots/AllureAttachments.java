package utils.Screenshots;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AllureAttachments {

    @Attachment(value = "{screenshotName}", type = "image/png")
    public static byte[] attachScreenshot(WebDriver driver, String screenshotName) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}