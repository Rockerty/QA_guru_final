package steps;

import io.qameta.allure.Step;
import pages.GithubSearchPage;

import static com.codeborne.selenide.Selenide.open;

public class GithubSteps {
    GithubSearchPage githubSearchPage = new GithubSearchPage();

    @Step("Открытие главной страницы")
    public void openMainPage(){
        open("");
    }

    @Step("Поиск репозитория {repositoryName}")
    public void searchForRepository(String repositoryName){
        githubSearchPage.enterSearchField(repositoryName);
    }

    @Step("Переход к первому репозиторию в списке")
    public void chooseFirstRepositoryInList(int issueNumber){
        githubSearchPage.resultClickByNumber(issueNumber);
    }

    @Step("Переход во вкладку по ее имени")
    public void enterMainPageTabByName(String tabName){
        githubSearchPage.enterMainPageTabByName(tabName);
    }

    @Step("Проверка имени issue в списке по ее номеру")
    public void nameAssertInIssueListByNumber(String issueName, int number){
        githubSearchPage.nameAssertInIssueListByNumber(issueName, number);
    }
}
