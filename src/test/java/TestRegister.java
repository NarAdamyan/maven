import Listener.ScreenshotOnFailureExtension;
import io.qameta.allure.*;
import org.example.RegisterPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import steps.RegistrationTestSteps;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRegister {

    private static final Logger logger = LoggerFactory.getLogger(TestRegister.class);
    RegistrationTestSteps steps = new RegistrationTestSteps();
    RegisterPage registerPage=new RegisterPage();

    @RegisterExtension
    public ScreenshotOnFailureExtension screenshotOnFailure = new ScreenshotOnFailureExtension(steps.getDriver());

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

        // Use the steps to perform registration actions
        steps.selectRegisterFromCandidate();
        Allure.step("Selected register from candidate");

        steps.fillTheNameLastName("Nar", "Adam");
        logger.info("Filled name: Nar, last name: Adam");

        steps.enterEmail("nar95mail.ru"); // invalid email
        assertTrue(registerPage.isEmailErrorMessageVisible(), "Wrong Email");
        logger.info("Entered invalid email: nar95mail.ru");

        steps.correctWrongMail("nar.9999999999999995@mail.ru");
        logger.info("Corrected to valid email: nar99999999999995@mail.ru");

        steps.enterPassword();
        logger.info("Password entered and confirmed");


        Thread.sleep(1000);

        steps.fillBirthday("2025", "May","10");
        logger.info("Filled birthday: 2025 May 10");
        steps.agreementAndRegister();
        logger.info("Agreed to Privacy and Policy");

        assertTrue(registerPage.isRegisterStyleChanges(), "Register button style should change");
        logger.info("Verified Register button style change");

        Allure.step("Registration test completed");
        logger.info("Test finished");
    }
}
