import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationFormTest {

    @BeforeAll
    static void setUp() {
        System.out.println("beforeAll");
        Configuration.timeout = 10000;
        Configuration.baseUrl = "https://app.qa.guru/";
    }
    @Test
    void successfulRegistrationTest() {
        open("automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//input[@data-testid='firstName']").setValue("Nick");
        $x("//input[@data-testid='lastName']").setValue("Smirnov");
        $x("//input[@data-testid='email']").setValue("smirnov@mail.ru");
        $x("//input[@data-testid='phone']").setValue("999 999 9999");
        $x("//*[contains(text(), 'Language')]/following::*[@role='combobox'][1]").shouldBe(visible).click();
        $x("//*[@data-value='Russian']").shouldBe(visible).click();
        // Выбор даты рождения
        $x("//*[@data-testid='CalendarIcon']").scrollTo().shouldBe(visible).click();
        // Год
        $x("//*[contains(@class, 'MuiPickersCalendarHeader-switchViewButton')]").shouldBe(visible).click();
        $x("//button[text()='2022']").scrollTo().shouldBe(visible).click();
        // Месяц
        $x("//button[text()='Feb']").scrollTo().shouldBe(visible).click();
        // День
        $x("//button[text()='3']").scrollTo().shouldBe(visible).click();
        //$x("//button[@role='gridcell' and text()='" + dayOfMonth + "']").shouldBe(visible).click();
        // Выбоа пола
        $x("//input[@data-testid='gender' and @value='Male']").click();
        // Выбор хобби
        $x("//input[@value='Sports' and @data-testid='hobbies']").click();
        $x("//input[@value='Reading' and @data-testid='hobbies']").click();
        $x("//input[@value='Music' and @data-testid='hobbies']").click();
        // Выбор предметов
        $x("//*[contains(text(), 'Subject')]/following::*[@role='combobox'][1]").scrollTo().click();
        $x("//*[@role='listbox']//*[@data-value='Maths']").shouldBe(visible).click();
        $x("//*[@role='listbox']//*[@data-value='Arts']").shouldBe(visible).click();
        $x("//*[@role='listbox']//*[@data-value='Dance']").shouldBe(visible).click();
        $x("//*[@role='listbox']//*[@data-value='Physical']").shouldBe(visible).click();
        actions().sendKeys(Keys.ESCAPE).perform();
        // Выбор страны, города, адреса
        $x("//*[contains(text(), 'State')]/following::*[@role='combobox'][1]").click();
        $x("//*[@data-value='California']").shouldBe(visible).click();
        $(By.id("city-select")).selectOption("San Francisco");
        // Сдвиг ползунка
        actions()
                .clickAndHold($(By.xpath("//span[contains(@class, 'MuiSlider-thumb')]")))
                .moveByOffset(100, 0)
                .release()
                .perform();
        $(By.xpath("//textarea[@data-testid='address']")).setValue("My lovely address");
        // Загрузка файла
        $(By.xpath("//input[@type='file']")).uploadFromClasspath("Little poito art.png");
        $x("//button[@type='submit']").scrollTo().click();
        // Проверки
        $(By.xpath("//p[text()='firstName']/following::p[1]")).scrollTo().shouldBe(visible).shouldHave(text("Nick"));
        $(By.xpath("//p[text()='lastName']/following::p[1]")).shouldHave(text("Smirnov"));
        $(By.xpath("//p[text()='email']/following::p[1]")).shouldHave(text("smirnov@mail.ru"));
        $(By.xpath("//p[text()='gender']/following::p[1]")).shouldHave(text("Male"));
        $(By.xpath("//p[text()='phone']/following::p[1]")).shouldHave(text("999 999 9999"));
        $(By.xpath("//p[text()='dateOfBirth']/following::p[1]")).shouldHave(text("03/02/2022"));
        $(By.xpath("//p[text()='subjects']/following::p[1]")).shouldHave(text("Maths"));
        $(By.xpath("//p[text()='subjects']/following::p[1]")).shouldHave(text("Arts"));
        $(By.xpath("//p[text()='subjects']/following::p[1]")).shouldHave(text("Dance"));
        $(By.xpath("//p[text()='subjects']/following::p[1]")).shouldHave(text("Physical"));
        $(By.xpath("//p[text()='hobbies']/following::p[1]")).shouldHave(text("Sports"));
        $(By.xpath("//p[text()='hobbies']/following::p[1]")).shouldHave(text("Reading"));
        $(By.xpath("//p[text()='hobbies']/following::p[1]")).shouldHave(text("Music"));
        $(By.xpath("//p[text()='stateCity']/following::p[1]")).shouldHave(text("California"));
        $(By.xpath("//p[text()='stateCity']/following::p[1]")).shouldHave(text("San Francisco"));
        $(By.xpath("//p[text()='language']/following::p[1]")).shouldHave(text("Russian"));
        $(By.xpath("//p[text()='address']/following::p[1]")).shouldHave(text("My lovely address"));
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
        $x("//input[@data-testid='email']").setValue("sm@mail.ru");
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
        $x("//input[@data-testid='email']").setValue("smirnovsmir");
        $x("//button[@type='submit']").scrollTo().click();
        $(By.xpath("//form")).shouldHave(text("E-mail is invalid"));
    }

    @Test
    void requiredFieldsOnlyTest() {
        open("automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//input[@data-testid='firstName']").setValue("Monica");
        $x("//input[@data-testid='lastName']").setValue("Smith");
        $x("//input[@data-testid='email']").setValue("MonicaSmith@mail.ru");
        $x("//input[@data-testid='phone']").setValue("999 999 9999");
        $x("//input[@data-testid='gender' and @value='Female']").scrollTo().click();
        $x("//button[@type='submit']").scrollTo().click();
        // Проверки
        $(By.xpath("//p[text()='firstName']/following::p[1]")).scrollTo().shouldBe(visible).shouldHave(text("Monica"));
        $(By.xpath("//p[text()='lastName']/following::p[1]")).shouldHave(text("Smith"));
        $(By.xpath("//p[text()='email']/following::p[1]")).shouldHave(text("MonicaSmith@mail.ru"));
        $(By.xpath("//p[text()='phone']/following::p[1]")).shouldHave(text("999 999 9999"));
        $(By.xpath("//p[text()='gender']/following::p[1]")).shouldHave(text("Female"));
    }
}
