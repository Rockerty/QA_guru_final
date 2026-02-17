import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static testdata.TestData.*;

public class RegistrationFormTest {

    @BeforeAll
    static void allTestsSetUp() {
        System.out.println("beforeAll");
        Configuration.timeout = 10000;
        Configuration.baseUrl = "https://app.qa.guru/";
    }

    @Test
    void successfulRegistrationTest() {
        open("automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//input[@data-testid='firstName']").setValue(firstName);
        $x("//input[@data-testid='lastName']").setValue(lastName);
        $x("//input[@data-testid='email']").setValue(email);
        $x("//input[@data-testid='phone']").setValue(phone);
        $x("//*[contains(text(), 'Language')]/following::*[@role='combobox'][1]").shouldBe(visible).click();
        $x("//*[@data-value='" + language + "']").shouldBe(visible).click();
        // Выбор даты рождения
        $x("//*[@data-testid='CalendarIcon']").scrollTo().shouldBe(visible).click();
        // Год
        $x("//*[contains(@class, 'MuiPickersCalendarHeader-switchViewButton')]").shouldBe(visible).click();
        $x("//button[text()='" + birthYear + "']").scrollTo().shouldBe(visible).click();
        // Месяц
        $x("//button[text()='" + birthMonth + "']").scrollTo().shouldBe(visible).click();
        // День
        $x("//button[text()='" + birthDay + "']").scrollTo().shouldBe(visible).click();
        // Выбор пола
        $x("//input[@data-testid='gender' and @value='" + gender + "']").click();
        // Выбор хобби
        for (String hobby : hobbies) {
            $x("//input[@value='" + hobby + "' and @data-testid='hobbies']").click();
        }
        // Выбор предметов
        $x("//*[contains(text(), 'Subject')]/following::*[@role='combobox'][1]").scrollTo().click();
        for (String subject : subjects) {
            $x("//*[@role='listbox']//*[@data-value='" + subject + "']").shouldBe(visible).click();
        }
        actions().sendKeys(Keys.ESCAPE).perform();
        // Выбор страны, города, адреса
        $x("//*[contains(text(), 'State')]/following::*[@role='combobox'][1]").click();
        $x("//*[@data-value='" + state + "']").shouldBe(visible).click();
        $(By.id("city-select")).selectOption(city);
        // Сдвиг ползунка
        actions()
                .clickAndHold($(By.xpath("//span[contains(@class, 'MuiSlider-thumb')]")))
                .moveByOffset(100, 0)
                .release()
                .perform();
        $(By.xpath("//textarea[@data-testid='address']")).setValue(address);
        // Загрузка файла
        $(By.xpath("//input[@type='file']")).uploadFromClasspath("Little poito art.png");
        $x("//button[@type='submit']").scrollTo().click();
        // Проверки
        $(By.xpath("//p[text()='firstName']/following::p[1]")).scrollTo().shouldBe(visible).shouldHave(text(firstName));
        $(By.xpath("//p[text()='lastName']/following::p[1]")).shouldHave(text(lastName));
        $(By.xpath("//p[text()='email']/following::p[1]")).shouldHave(text(email));
        $(By.xpath("//p[text()='gender']/following::p[1]")).shouldHave(text(gender));
        $(By.xpath("//p[text()='phone']/following::p[1]")).shouldHave(text(phone));
        $(By.xpath("//p[text()='dateOfBirth']/following::p[1]")).shouldHave(text(expectedBirthDate));
        for (String subject : subjects) {
            $(By.xpath("//p[text()='subjects']/following::p[1]")).shouldHave(text(subject));
        }
        for (String hobby : hobbies) {
            $(By.xpath("//p[text()='hobbies']/following::p[1]")).shouldHave(text(hobby));
        }
        $(By.xpath("//p[text()='stateCity']/following::p[1]")).shouldHave(text(state));
        $(By.xpath("//p[text()='stateCity']/following::p[1]")).shouldHave(text(city));
        $(By.xpath("//p[text()='language']/following::p[1]")).shouldHave(text(language));
        $(By.xpath("//p[text()='address']/following::p[1]")).shouldHave(text(address));
    }

    @Test
    void requiredFieldsErrorTest() {
        open("automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//button[@type='submit']").scrollTo().click();
        $(By.xpath("//form")).shouldHave(text("First Name is required"));
        $(By.xpath("//form")).shouldHave(text("Last Name is required"));
        $(By.xpath("//form")).shouldHave(text("E-mail is required"));
        $(By.xpath("//form")).shouldHave(text("Phone number is required"));
        $(By.xpath("//form")).shouldHave(text("Gender is required"));
    }

    @Test
    void shortEmailTest() {
        open("automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//input[@data-testid='email']").setValue(shortIncorrectEmail);
        $x("//button[@type='submit']").scrollTo().click();
        $(By.xpath("//form")).shouldHave(text("E-mail must be at least 10 symbols long"));
    }

    @Test
    void invalidFileExtensionTest() {
        open("automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $(By.xpath("//input[@type='file']")).uploadFromClasspath("VanyaVPN.exe");
        $x("//button[@type='submit']").scrollTo().click();
        $(By.xpath("//form")).shouldHave(text("Upload failed"));
        $(By.xpath("//form")).shouldHave(text("Invalid extension"));
    }

    @Test
    void invalidEmailTest() {
        open("automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//input[@data-testid='email']").setValue(incorrectFormatEmail);
        $x("//button[@type='submit']").scrollTo().click();
        $(By.xpath("//form")).shouldHave(text("E-mail is invalid"));
    }

    @Test
    void requiredFieldsOnlyTest() {
        open("automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//input[@data-testid='firstName']").setValue(firstName);
        $x("//input[@data-testid='lastName']").setValue(lastName);
        $x("//input[@data-testid='email']").setValue(email);
        $x("//input[@data-testid='phone']").setValue(phone);
        $x("//input[@data-testid='gender' and @value='" + gender + "']").scrollTo().click();
        $x("//button[@type='submit']").scrollTo().click();
        // Проверки
        $(By.xpath("//p[text()='firstName']/following::p[1]")).scrollTo().shouldBe(visible).shouldHave(text(firstName));
        $(By.xpath("//p[text()='lastName']/following::p[1]")).shouldHave(text(lastName));
        $(By.xpath("//p[text()='email']/following::p[1]")).shouldHave(text(email));
        $(By.xpath("//p[text()='phone']/following::p[1]")).shouldHave(text(phone));
        $(By.xpath("//p[text()='gender']/following::p[1]")).shouldHave(text(gender));
    }
}
