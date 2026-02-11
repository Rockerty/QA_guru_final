import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationFormTest {
    @Test
    void successfulRegistration() {
        open("https://app.qa.guru/automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//input[@data-testid='firstName']").setValue("Иван");
        $x("//input[@data-testid='lastName']").setValue("Смирнов");
        $x("//input[@data-testid='email']").setValue("smirnov@mail.ru");
        $x("//input[@data-testid='phone']").setValue("9999999999");


    }
}
