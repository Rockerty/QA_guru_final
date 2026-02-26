import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import pages.RegistrationFormPage;

import static com.codeborne.selenide.Selenide.*;
import static testdata.TestData.*;

public class RegistrationFormTest {
    RegistrationFormPage registrationFormPage = new RegistrationFormPage();

    @BeforeAll
    static void allTestsSetUp() {
        System.out.println("beforeAll");
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://app.qa.guru/";
    }

    @BeforeEach
    void setUp() {
        registrationFormPage.openPage();
        registrationFormPage.advertisingMenuClose();
    }

    @Test
    void successfulRegistrationTest() {
        registrationFormPage.enterField("firstName", firstName);
        registrationFormPage.enterField("lastName", lastName);
        registrationFormPage.enterField("email", email);
        registrationFormPage.enterField("phone", phone);
        registrationFormPage.openCombobox("Language");
        registrationFormPage.languageChoice(language);
        registrationFormPage.setBirthDate(birthYear, birthMonth, birthDay);
        registrationFormPage.genderChoice(gender);

        for (String hobby : hobbies) {
            registrationFormPage.hobbyChoice(hobby);
        }

        registrationFormPage.openCombobox("Subject");
        for (String subject : subjects) {
            registrationFormPage.selectOption(subject);
        }
        actions().sendKeys(Keys.ESCAPE).perform();

        registrationFormPage.openCombobox("State");
        registrationFormPage.stateChoice(state);
        registrationFormPage.cityChoice(city);
        registrationFormPage.sliderMove(100);
        registrationFormPage.textAreaFill("address", address);
        registrationFormPage.fileUpload("Little poito art.png");
        registrationFormPage.clickSubmitButton();
        // Проверки
        registrationFormPage.assertFieldOutput("firstName", firstName);
        registrationFormPage.assertFieldOutput("lastName", lastName);
        registrationFormPage.assertFieldOutput("email", email);
        registrationFormPage.assertFieldOutput("phone", phone);
        registrationFormPage.assertFieldOutput("gender", gender);
        registrationFormPage.assertFieldOutput("stateCity", state);
        registrationFormPage.assertFieldOutput("stateCity", city);
        registrationFormPage.assertFieldOutput("language", language);
        registrationFormPage.assertFieldOutput("address", address);
        registrationFormPage.assertFieldOutput("dateOfBirth", expectedBirthDate);
        for (String subject : subjects) {
            registrationFormPage.assertFieldOutput("subjects", subject);
        }
        for (String hobby : hobbies) {
            registrationFormPage.assertFieldOutput("hobbies", hobby);
        }
    }

    @Test
    void requiredFieldsErrorTest() {
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("First Name is required");
        registrationFormPage.registrationFormTextAssert("Last Name is required");
        registrationFormPage.registrationFormTextAssert("E-mail is required");
        registrationFormPage.registrationFormTextAssert("Phone number is required");
        registrationFormPage.registrationFormTextAssert("Gender is required");
    }

    @Test
    void shortEmailTest() {
        registrationFormPage.enterField("email", shortIncorrectEmail);
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("E-mail must be at least 10 symbols long");
    }

    @Test
    void invalidFileExtensionTest() {
        registrationFormPage.fileUpload("VanyaVPN.exe");
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("Upload failed");
        registrationFormPage.registrationFormTextAssert("Invalid extension");
    }

    @Test
    void invalidEmailTest() {
        registrationFormPage.enterField("email", incorrectFormatEmail);
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("E-mail is invalid");
    }

    @Test
    void requiredFieldsOnlyTest() {
        registrationFormPage.enterField("firstName", firstName);
        registrationFormPage.enterField("lastName", lastName);
        registrationFormPage.enterField("email", email);
        registrationFormPage.enterField("phone", phone);
        registrationFormPage.genderChoice(gender);
        registrationFormPage.clickSubmitButton();
        // Проверки
        registrationFormPage.assertFieldOutput("firstName", firstName);
        registrationFormPage.assertFieldOutput("lastName", lastName);
        registrationFormPage.assertFieldOutput("email", email);
        registrationFormPage.assertFieldOutput("phone", phone);
        registrationFormPage.assertFieldOutput("gender", gender);
    }
}
