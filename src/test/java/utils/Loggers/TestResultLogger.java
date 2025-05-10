package utils.Loggers;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestResultLogger implements AfterAllCallback {

    private static final Logger logger = LoggerFactory.getLogger(TestResultLogger.class);
    private WebDriver driver;

    public TestResultLogger(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        boolean allTestsSuccessful = context.getTestClass().map(testClass ->
                context.getTestMethod().map(testMethod ->
                        context.getExecutionException().isEmpty()
                ).orElse(true) // If no test method, consider successful
        ).orElse(true); // If no test class, consider successful

        if (allTestsSuccessful && driver instanceof TakesScreenshot) {
            logger.info("All tests in '{}' passed. Taking final screenshot...", context.getDisplayName());
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            saveScreenshot(screenshot);
        } else if (driver != null) {
            logger.info("Not all tests in '{}' were successful, or WebDriver does not support screenshots.", context.getDisplayName());
        }
    }

    @Attachment(value = "Final Screenshot on Success", type = "image/png")
    public byte[] saveScreenshot(byte[] screenshot) {
        logger.info("Final screenshot saved and attached to Allure report.");

        String screenshotDirPath = System.getProperty("screenshot.dir", "target/screenshots");
        File screenshotDir = new File(screenshotDirPath);

        try {
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            String screenshotFileName = "final_success_screenshot_" + System.currentTimeMillis() + ".png";
            File screenshotFile = new File(screenshotDir, screenshotFileName);

            try (FileOutputStream fos = new FileOutputStream(screenshotFile)) {
                fos.write(screenshot);
            }

            logger.info("Final screenshot saved locally at: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error saving final screenshot", e);
        }

        return screenshot;
    }
}