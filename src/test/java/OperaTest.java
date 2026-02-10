import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static com.codeborne.selenide.Selenide.*;

public class OperaTest {
    @Test
    void testOpera() {
        // 1. Автоматическая настройка ChromeDriver
        WebDriverManager.chromedriver().setup();

        // 2. Создаём опции и указываем путь к Opera
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\nsmirnov.IT-ONE\\AppData\\Local\\Programs\\Opera\\opera.exe");

        // 3. Передаём опции в Selenide
        Configuration.browserCapabilities = options;

        open("https://app.qa.guru/automation-practice-form/");
        $x("//input[@data-testid='firstName']").setValue("Иван");


        // 6. Закрываем браузер
        closeWebDriver();
    }
}