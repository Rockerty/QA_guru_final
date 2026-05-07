package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class BookClubPage {

    private static final String inputFieldXpathTemplate = "//*[@id='%s']";
    private static final String createClubButton = "//*[@data-testid='create-club-link']";
    private static final String submitButton = "//*[@type='submit']";
    private static final String searchInput = "//input[@class='search-input']";
    private static final String searchButton = "//*[@class='search-button']";
    private static final String clubCardInList = "//*[@class='club-card']";
    private static final String clubTabsByNameXpathTemplate = "//button[normalize-space()='%s']";
    private static final String clubEnterButtonByNameXpathTemplate = "//h2[contains(text(),\"%s\")]/ancestor::div[@class=\"club-card\"]//button";
    private static final String leaveClubButton = "//*[@class='leave-btn']";
    private static final String clubDetails = "//*[@class='club-details']";

    //Actions
    public void enterFieldById(String fieldId, String value) {
        String xpath = String.format(inputFieldXpathTemplate, fieldId);
        $x(xpath).scrollTo().setValue(value);
    }

    public void clickSubmitButton() {
        $x(submitButton).scrollTo().click();
    }

    public void clickCreateBookClubButton() {
        $x(createClubButton).scrollTo().click();
    }

    public void openMainPage(){
        open("/");
    }

    public void openFavicon(){
        open("/favicon.svg");
    }

    public void searchByTitle(String bookTitle) {
        $x(searchInput).setValue(bookTitle);
        $x(searchButton).click();
    }

    public void verifyClubContainsInList(String expectedText) {
        $x(clubCardInList).shouldHave(text(expectedText));
    }

    public void verifyClubContainsInCard(String bookTitle,
                                         String bookAuthors,
                                         String publicationYear,
                                         String description) {
        SelenideElement card = $x(clubCardInList);
        card.shouldHave(text(bookTitle));
        card.shouldHave(text(bookAuthors));
        card.shouldHave(text(publicationYear));
        card.shouldHave(text(description));
    }

    public void createClub(String bookTitle, String bookAuthors, String publicationYear,
                           String description, String telegramChatLink) {
        enterFieldById("bookTitle", bookTitle);
        enterFieldById("bookAuthors", bookAuthors);
        enterFieldById("publicationYear", publicationYear);
        enterFieldById("description", description);
        enterFieldById("telegramChatLink", telegramChatLink);
        clickSubmitButton();
    }

    public void enterTabByName(String tabName) {
        String xpath = String.format(clubTabsByNameXpathTemplate, tabName);
        $x(xpath).scrollTo().click();
    }

    public void enterClubCardByName(String clubName) {
        String xpath = String.format(clubEnterButtonByNameXpathTemplate, clubName);
        $x(xpath).scrollTo().click();
    }

    public void clubInListAssert(String bookTitle, String bookAuthors,
                                 String publicationYear, String description) {
        verifyClubContainsInList(bookTitle);
        verifyClubContainsInList(bookAuthors);
        verifyClubContainsInList(publicationYear);
        verifyClubContainsInList(description);
    }

    public void leaveClub() {
        $x(leaveClubButton).click();
        confirm();
    }

    public void clubDetailMessage(String messageText) {
        $x(clubDetails).shouldHave(text(messageText));
    }
}
