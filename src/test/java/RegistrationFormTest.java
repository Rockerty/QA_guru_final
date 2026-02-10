import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationFormTest {
    @Test
    void successfulRegistration() {
        open("https://app.qa.guru/automation-practice-form/");
        //input[@data-testid="firstName"]
        $x("//input[@data-testid='firstName']").setValue("Иван");


    }
}
