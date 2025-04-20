import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestResultLogger implements TestWatcher {

    private final WebDriver driver;

    public TestResultLogger(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Allure.addAttachment("FAIL TEST - " + context.getDisplayName(), "text/plain", cause.getMessage());
        attachScreenshotOnFailure(); // Call the method to attach screenshot on failure
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public byte[] attachScreenshotOnFailure() {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        return null;
    }
}