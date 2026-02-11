import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RegistrationFormTest {

    @BeforeAll
    static void beforeAll() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        int dayOfMonth = yesterday.getDayOfMonth();
        System.out.println(dayOfMonth);
    }
    @Test
    void successfulRegistration() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        int dayOfMonth = yesterday.getDayOfMonth();

        open("https://app.qa.guru/automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//input[@data-testid='firstName']").setValue("Nick");
        $x("//input[@data-testid='lastName']").setValue("Smirnov");
        $x("//input[@data-testid='email']").setValue("smirnovsmirnov@mail.ru");
        $x("//input[@data-testid='phone']").setValue("999 999 9999");
        //$(By.xpath("//input[@data-testid='phone']")).click();
        //$(By.xpath("//input[@data-testid='phone']")).sendKeys("9");
        //$x("//input[@data-testid='phone']").setValue("9");

        $x("//*[contains(text(), 'Language')]/following::*[@role='combobox'][1]").shouldBe(visible).click();
        //$x("//*[@data-testid='language']").click();
        $x("//*[@data-value='Russian']").shouldBe(visible).click();
        // Клик по вчерашней дате
        $x("//*[@data-testid='CalendarIcon']").scrollTo().shouldBe(visible).click();
        $x("//button[@role='gridcell' and text()='" + dayOfMonth + "']").shouldBe(visible).click();
        $x("//input[@data-testid='gender' and @value='Male']").click();
        // Выбор хобби
        $x("//input[@value='Sports' and @data-testid='hobbies']").click();
        $x("//input[@value='Reading' and @data-testid='hobbies']").click();
        $x("//input[@value='Music' and @data-testid='hobbies']").click();
        // Выбор предметов
        $x("//*[contains(text(), 'Subject')]/following::*[@role='combobox'][1]").click();
        $x("//*[@role='listbox']//*[@data-value='Maths']").shouldBe(visible).click();
        $x("//*[@role='listbox']//*[@data-value='Arts']").shouldBe(visible).click();
        $x("//*[@role='listbox']//*[@data-value='Dance']").shouldBe(visible).click();
        $x("//*[@role='listbox']//*[@data-value='Physical']").shouldBe(visible).click();
        actions().sendKeys(Keys.ESCAPE).perform();

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
        $(By.xpath("//input[@type='file']")).uploadFile(new File("C:\\Users\\nsmirnov.IT-ONE\\Downloads\\Telegram Desktop\\Little poito art.png"));
        $x("//button[@type='submit']").scrollTo().click();
        // Проверки
        $(By.xpath("//p[text()='firstName']/following::p[1]")).scrollTo().shouldBe(visible).shouldHave(text("Nick"));
        $(By.xpath("//p[text()='lastName']/following::p[1]")).shouldHave(text("Smirnov"));
        $(By.xpath("//p[text()='email']/following::p[1]")).shouldHave(text("smirnov@mail.ru"));
        $(By.xpath("//p[text()='gender']/following::p[1]")).shouldHave(text("Male"));
        $(By.xpath("//p[text()='phone']/following::p[1]")).shouldHave(text("999 999 9999"));
        $(By.xpath("//p[text()='address']/following::p[1]")).shouldHave(text("My lovely address"));


    }
}
