package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
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

        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        Configuration.browserVersion = "128.0";
    }

    @BeforeEach
    void setUp() {
        registrationFormPage.openPage();
        registrationFormPage.advertisingMenuClose();
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    @Test
    @DisplayName("Successful registration with all fields filled")
    @Tag("positiveTest")
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
        registrationFormPage.fileUpload("datafiles/Little poito art.png");
        registrationFormPage.clickSubmitButton();
        // Проверки
        registrationFormPage.assertFieldOutput("firstName", firstName);
        registrationFormPage.assertFieldOutput("lastName", lastName);
        registrationFormPage.assertFieldOutput("email", email);
        registrationFormPage.assertFieldOutput("phone", expectedPhone);
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
    @DisplayName("Form submission with empty required fields")
    @Tag("negativeTest")
    void requiredFieldsErrorTest() {
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("First Name is required");
        registrationFormPage.registrationFormTextAssert("Last Name is required");
        registrationFormPage.registrationFormTextAssert("E-mail is required");
        registrationFormPage.registrationFormTextAssert("Phone number is required");
        registrationFormPage.registrationFormTextAssert("Gender is required");
    }

    @Test
    @DisplayName("Form submission with invalid email - less than 10 characters")
    @Tag("negativeTest")
    void shortEmailTest() {
        registrationFormPage.enterField("email", shortIncorrectEmail);
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("E-mail must be at least 10 symbols long");
    }

    @ParameterizedTest(name = "Form submission with invalid file extension - {0}")
    @ValueSource(strings  = {"datafiles/ConfusedCultureProt.exe", "datafiles/Logos in the Ruins 2.mp3", "datafiles/ShakeConfig.asset"})
    @Tag("negativeTest")
    void invalidFileExtensionTest(String fileName) {
        registrationFormPage.fileUpload(fileName);
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("Upload failed");
        registrationFormPage.registrationFormTextAssert("Invalid extension");
    }

    @Test
    @DisplayName("Form submission with invalid email - incorrect format")
    @Tag("negativeTest")
    void invalidEmailTest() {
        registrationFormPage.enterField("email", incorrectFormatEmail);
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("E-mail is invalid");
    }

    @Test
    @DisplayName("Form submission with only required fields filled")
    @Tag("positiveTest")
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
        registrationFormPage.assertFieldOutput("phone", expectedPhone);
        registrationFormPage.assertFieldOutput("gender", gender);
    }

    @ParameterizedTest
    @CsvSource({
            "Nick, Smirnov, NSmirnov@mail.ru, 6955824926",
            "Max, Ivanov, MIvanov@mail.ru, 6955824936",
            "Alex, Sidorov, ASidorov@mail.ru, 6955824936",
    })
    @DisplayName("Form submission with only required fields filled")
    @Tag("positiveTest")
    void requiredFieldsOnlyWithParametersTest(String firstName, String lastName, String email, String phone) {
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
        registrationFormPage.assertFieldOutput("gender", gender);
    }

    @ParameterizedTest(name = "Form submission with invalid email - {0}")
    @MethodSource("testdata.TestData#shortEmailTestWithSourceMethod")
    @Tag("negativeTest")
    void shortEmailTestWithSourceMethod(String shortIncorrectEmail) {
        registrationFormPage.enterField("email", shortIncorrectEmail);
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("E-mail must be at least 10 symbols long");
    }

    @ParameterizedTest(name = "Form submission with invalid email - incorrect format - {0}")
    @MethodSource("testdata.TestData#invalidEmailTestWithSourceMethod")
    @Tag("negativeTest")
    void invalidEmailTestWithSourceMethod(String incorrectFormatEmail) {
        registrationFormPage.enterField("email", incorrectFormatEmail);
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert("E-mail is invalid");
    }

    @ParameterizedTest(name = "Form submission with invalid email {0} with error text {1}")
    @MethodSource("testdata.TestData#errorMessageWithIncorrectEmailTest")
    @Tag("negativeTest")
    void errorMessageWithIncorrectEmailTest(String incorrectEmail, String errorMessage) {
        registrationFormPage.enterField("email", incorrectEmail);
        registrationFormPage.clickSubmitButton();
        registrationFormPage.registrationFormTextAssert(errorMessage);
    }
}