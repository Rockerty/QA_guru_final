import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OperaTest {

    @BeforeEach
    void beforeEach() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\nsmirnov.IT-ONE\\AppData\\Local\\Programs\\Opera\\opera.exe");
        Configuration.browserCapabilities = options;
        //Configuration.browser = "opera";
    }

    @Test
    void testOpera() {
        open("https://app.qa.guru/automation-practice-form/");
        $x("//*[@data-testid='ClearIcon']").shouldBe(visible).click();
        $x("//input[@data-testid='firstName']").setValue("Николай");
        $x("//input[@data-testid='lastName']").setValue("Смирнов");
        $x("//input[@data-testid='email']").setValue("smirnov@mail.ru");
        $x("//input[@data-testid='phone']").setValue("9999999999");

    }
}