package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.remote.DesiredCapabilities;
import pages.GithubSearchPage;
import steps.GithubSteps;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class GithubIssueTest {
    GithubSearchPage githubSearchPage = new GithubSearchPage();

    @BeforeAll
    static void allTestsSetUp() {
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://github.com/";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        Configuration.browserVersion = "128.0";
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    @AfterEach
    void addAttachment() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.attachAsText("", "");
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @Test
    public void issueSearchWithClearListenerTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("");
        $x("//*[@class='search-input']").click();
        $x("//*[@id='query-builder-test']").scrollTo().setValue("rockerty/qa_guru_final");
        $x("//*[@id='query-builder-test']").submit();
        $x("(//div[@data-testid='results-list']//a)[1]").scrollTo().click();
        $x("//*[@id='issues-tab']").scrollTo().click();
        $x("(//div[contains(@class, 'ListItems-module__listItem')])[1]").shouldHave(text("First issue for automated test"));
    }


    @Test
    public void issueSearchWithListenerTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        githubSearchPage.openPage();
        githubSearchPage.enterSearchField("rockerty/qa_guru_final");
        githubSearchPage.resultClickByNumber(1);
        githubSearchPage.enterMainPageTabByName("issues");
        githubSearchPage.nameAssertInIssueListByNumber("First issue for automated test", 1);
    }

    @Tag("dz_11")
    @Test
    public void issueSearchWithLambdaStepsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открывается главная страница", () -> {
            githubSearchPage.openPage();
        });

        step("Поиск репозитория", () -> {
            githubSearchPage.enterSearchField("rockerty/qa_guru_final");
            githubSearchPage.resultClickByNumber(1);
        });

        step("Переход к вкладке по имени", () -> {
            githubSearchPage.enterMainPageTabByName("issues");
        });

        step("Проверка имени issue в списке по ее номеру", () -> {
            githubSearchPage.nameAssertInIssueListByNumber("First issue for automated test", 1);
        });
    }

    @Tag("dz_11")
    @Test
    public void issueSearchWithAnnotatedStepsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        GithubSteps steps = new GithubSteps();

        steps.openMainPage();
        steps.searchForRepository("rockerty/qa_guru_final");
        steps.chooseFirstRepositoryInList(1);
        steps.enterMainPageTabByName("issues");
        steps.nameAssertInIssueListByNumber("First issue for automated test", 1);
    }
}
