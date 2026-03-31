package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class DuckduckgoSearchTests {

    @BeforeAll
    static void allTestsSetUp() {
        System.out.println("beforeAll");
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";

        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    }


    @Test
    void successfulSearchTest() {
        open("https://duckduckgo.com");
        $("[name=q]").shouldBe(visible).click();
        //sleep(5000);
        sleep(3000);
        $("[name=q]").setValue("selenide");
        //sleep(2000);
        $("[name=q]").pressEnter();
        $("[data-testid=result]").shouldHave(text("selenide.org"));
    }
}


