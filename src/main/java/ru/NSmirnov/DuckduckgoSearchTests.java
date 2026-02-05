import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DuckduckgoSearchTests {
    @Test
    void successfulSearchTest() {
        open("https://duckduckgo.com");
        // Пауза для "чтения" страницы
        //sleep(2000);
        // Медленно вводим текст
        $("[name=q]").shouldBe(visible).click();
        //sleep(500);
        $("[name=q]").setValue("selenide");
        //sleep(1000);
        $("[name=q]").pressEnter();
        $("[data-testid=result]").shouldHave(text("selenide.org"));
    }
}


