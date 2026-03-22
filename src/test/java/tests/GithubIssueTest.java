package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import pages.GithubSearchPage;
import steps.GithubSteps;

import static io.qameta.allure.Allure.step;

public class GithubIssueTest {
    GithubSearchPage githubSearchPage = new GithubSearchPage();

    @BeforeAll
    static void allTestsSetUp() {
        System.out.println("beforeAll");
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://github.com/";
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
