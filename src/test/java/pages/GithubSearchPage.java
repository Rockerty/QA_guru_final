package pages;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class GithubSearchPage {
    private static final String shortMainPageSearchField = "//*[@class='search-input']";
    private static final String fullMainPageSearchField = "//*[@id='query-builder-test']";
    private static final String firstElementInResultList = "(//div[@data-testid='results-list']//a)[%s]";
    private static final String mainPageTabs = "//*[@id='%s-tab']";
    private static final String issueNameInIssueList = "(//div[contains(@class, 'ListItems-module__listItem')])[%s]";

    //Actions
    public void openPage(){
        open("");
    };

    public void enterSearchField(String repositoryName) {
        $x(shortMainPageSearchField).click();
        $x(fullMainPageSearchField).scrollTo().setValue(repositoryName);
        $x(fullMainPageSearchField).submit();
    }

    public void resultClickByNumber(int number) {
        String xpath = String.format(firstElementInResultList, number);
        $x(xpath).scrollTo().click();
    }

    public void enterMainPageTabByName(String tabName) {
        String xpath = String.format(mainPageTabs, tabName);
        $x(xpath).scrollTo().click();
    }

    public void nameAssertInIssueListByNumber(String issueName, int number){
        String xpath = String.format(issueNameInIssueList, number);
        $x(xpath).shouldHave(text(issueName));
    }
}
