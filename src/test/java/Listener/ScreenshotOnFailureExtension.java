package Listener;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class ScreenshotOnFailureExtension implements TestWatcher {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotOnFailureExtension.class);
    private WebDriver driver;

    // Constructor to receive the WebDriver instance
    public ScreenshotOnFailureExtension(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        logger.info("Test '{}' failed. Taking screenshot...", context.getDisplayName());
        if (driver instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            saveScreenshot(screenshot);
        } else {
            logger.warn("WebDriver instance does not implement TakesScreenshot. Cannot capture screenshot.");
        }
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    public byte[] saveScreenshot(byte[] screenshot) {
        logger.info("Screenshot saved and attached to Allure report.");

        // 1. Get the screenshot directory from the system property (or use a default)
        String screenshotDirPath = System.getProperty("screenshot.dir", "target/screenshots");
        File screenshotDir = new File(screenshotDirPath);

        try {
            // 2. Create the directory if it doesn't exist
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            String screenshotFileName = "screenshot_" + System.currentTimeMillis() + ".png";
            File screenshotFile = new File(screenshotDir, screenshotFileName);

            try (FileOutputStream fos = new FileOutputStream(screenshotFile)) {
                fos.write(screenshot);
            }

            logger.info("Screenshot saved locally at: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error saving screenshot", e);
        }

        return screenshot;
    }
    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info("Test '{}' passed.", context.getDisplayName());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.warn("Test '{}' aborted: {}", context.getDisplayName(), cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        logger.info("Test '{}' disabled: {}", context.getDisplayName(), reason.orElse("No reason provided"));
    }
}
