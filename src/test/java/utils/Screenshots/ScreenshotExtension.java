package utils.Screenshots;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import utils.Screenshots.AllureAttachments;

public class ScreenshotExtension implements AfterTestExecutionCallback, BeforeTestExecutionCallback {
    private final WebDriver driver;

    public ScreenshotExtension(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        // Optional: Log start
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean testFailed = context.getExecutionException().isPresent();

        if (testFailed) {
            AllureAttachments.attachScreenshot(driver, "Screenshot on Failure");
        } else {
            AllureAttachments.attachScreenshot(driver, "Screenshot on Success");
        }
    }
}
