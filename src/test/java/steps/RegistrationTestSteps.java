package steps;

import io.qameta.allure.Step;
import org.example.DropDown;
import org.example.Header;
import org.example.RegisterPage;
import org.example.WebDriverManager;
import org.openqa.selenium.WebDriver;

import java.util.Scanner;

public class RegistrationTestSteps {

    private final RegisterPage registerPage = new RegisterPage(); // Pass driver instance
    private final DropDown dropdown = new DropDown();       // Pass driver instance
    private final Header headerFooter = new Header(); // Pass driver instance
    private static Scanner scanner = new Scanner(System.in);


    @Step("Get driver")
    public WebDriver getDriver() {
        WebDriver driver = WebDriverManager.getInstance().getDriver();
        driver.get("https://staff.am/en");
        return driver;
    }

    @Step("Select Register from Candidate")
    public void selectRegisterFromCandidate() {
        headerFooter.selectRegisterFromCandidate();
    }

    @Step("Fill the name,last name")
    public void fillTheNameLastName(String name, String lastName) {
        registerPage.fillFirstName(name).
                fillLastName(lastName);
    }

    @Step("Enter emailText and check wheather it false to correct")
    public void enterEmail(String mailText) {
        registerPage.enterEmail(mailText).isEmailErrorMessageVisible();
    }

    @Step("Check if email error message is visible")
    public void correctWrongMail(String rightMail) {
        registerPage.isEmailErrorMessageVisible();
        registerPage.correctMailFieldIfReceiveEmailError(rightMail);

    }

    @Step("Enter passwordText")
    public void enterPassword() {
        // Retrieve the password from the system property
        String passwordText = System.getProperty("password");
        if (passwordText == null) {
            System.out.println("Password is not provided! Please provide it.");
            return;
        }
        registerPage.enterPassword(passwordText).confirmPassword(passwordText);
    }

    @Step("fill the birthday")
    public void fillBirthday(String year, String month, String day)  {
        System.out.println("Selecting Year: " + year);
        dropdown.selectOption("Year", year);
        System.out.println("Year selected.");
        System.out.println("Selecting Month: " + month);
        dropdown.selectOption("Month", month);
        System.out.println("Month selected.");
        System.out.println("Selecting Day: " + day);
        dropdown.selectOption("Day", day);
        System.out.println("Day selected.");
    }

    @Step("Agreement and Register")
    public void agreementAndRegister() {
        registerPage.agreementPrivacyAndPolicy()
                .clickRegisterButton();
    }
}