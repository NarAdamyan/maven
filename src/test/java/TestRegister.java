import Listener.ScreenshotOnFailureExtension;
import io.qameta.allure.*;
import org.example.DropDown;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.RegistrationTestSteps;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRegister extends TestClass {
    private static final Logger logger = LoggerFactory.getLogger(TestRegister.class);
    RegistrationTestSteps steps = new RegistrationTestSteps();

    // Register the ScreenshotOnFailureExtension and pass the WebDriver
    @RegisterExtension
    public ScreenshotOnFailureExtension screenshotOnFailure = new ScreenshotOnFailureExtension(driver);

    @Epic("Registration")
    @Feature("Email Validation")
    @Story("User enters invalid email")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checks whether the system handles invalid emails correctly")
    @Test
    @DisplayName("Register with valid email after invalid input")
    void testRegistration() throws InterruptedException {
        logger.info("Test started: Register with valid email after invalid input");
        Allure.step("Start registration test");

        driver = steps.getDriver();
        logger.info("Driver initialized");

        steps.selectRegisterFromCandidate();
        Allure.step("Selected register from candidate");

        steps.fillTheName("Nar");
        logger.info("Filled name: Nar");

        steps.fillTheLastName("Adam");
        logger.info("Filled last name: Adam");

        steps.fillBirthday("2025", "May", "10");
        logger.info("Filled birthday: 2025 May 10");

        steps.enterEmail("nar95mail.ru"); // invalid email
        logger.info("Entered invalid email: nar95mail.ru");
        Allure.step("Entered invalid email");

        assertTrue(
                registerPage.isEmailErrorMessageVisible(),
                "Expected error message for invalid email"
        );
        logger.info("Verified error message is shown for invalid email");


        steps.correctEmailIfInvalid("nar95@mail.ru"); // valid email
        logger.info("Corrected to valid email: nar99999999999995@mail.ru");
        Allure.step("Corrected invalid email");

        assertTrue(
                !registerPage.isEmailErrorMessageVisible(),
                "Expected error message to disappear after entering valid email"
        );
        logger.info("Verified error message is gone after valid email");

        Thread.sleep(1000);

        String password = System.getProperty("password");
        if (password == null || password.isEmpty()) {
            logger.error("Password not provided");
            throw new IllegalArgumentException("Password not provided via system property");
        }
        logger.info("Using password from system properties");

        steps.enterPassword("1234567Oo!");
        steps.confirmPassword("1234567Oo!");
        logger.info("Password entered and confirmed");

        steps.agreemetnPrivacyAndPolicy();
        logger.info("Agreed to Privacy and Policy");

        assertTrue(registerPage.isRegisterStyleChanges(), "Register button style should change");
        logger.info("Verified Register button style change");

        Allure.step("Registration test completed");
        logger.info("Test finished");
    }
}
