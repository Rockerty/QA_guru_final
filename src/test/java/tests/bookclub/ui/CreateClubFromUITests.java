package tests.bookclub.ui;

import com.github.javafaker.Faker;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationRequestModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.BookClubPage;
import tests.bookclub.BookClubTestBase;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static helpers.LocalStorageHelper.buildAuthData;

public class CreateClubFromUITests extends BookClubTestBase {
    BookClubPage bookClubPage = new BookClubPage();

    String username;
    String password;
    String userId;
    String accessToken;
    String refreshToken;
    String bookTitle;
    String bookAuthors;
    Integer publicationYear;
    String description;
    String telegramChatLink;
    String localStorageData;
    String requiredBookTitleMessage;
    String requiredBookAuthorsMessage;
    String requiredPublicationYearMessage;
    String requiredDescriptionMessage;
    String requiredTelegramChatLinkMessage;

    @BeforeEach
    public void allTestsSetUp() {
        Faker faker = new Faker();
        username = faker.name().firstName() + faker.number().randomNumber();
        password = faker.name().firstName();
        bookTitle = faker.book().title() + " " + faker.number().randomNumber();
        bookAuthors = faker.book().author();
        publicationYear = faker.number().numberBetween(1900, 2026);
        description = faker.lorem().sentence();
        telegramChatLink = "https://t.me/" + username;
        requiredBookTitleMessage = "Название книги обязательно";
        requiredBookAuthorsMessage = "Автор(ы) книги обязательно";
        requiredPublicationYearMessage = "Год выпуска обязательно";
        requiredDescriptionMessage = "Описание книги обязательно";
        requiredTelegramChatLinkMessage = "Ссылка на Telegram чат обязательна";
    }

    @Test
    public void successfulCreateClubFromUITest() {
        step("Регистрация нового пользователя", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(username);
            registrationRequestModel.setPassword(password);

            SuccessfulRegistrationResponseModel successfulRegistrationResponse = registrationApiClient.successfulRegistration(registrationRequestModel);

            userId = successfulRegistrationResponse.getId().toString();
        });

        step("Получение токенов созданного пользователя", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(username);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel = loginApiClient.successfulLogin(loginRequestModel);

            accessToken = successfulLoginResponseModel.getAccess();
            refreshToken = successfulLoginResponseModel.getRefresh();
        });


        step("Формирование localStorageData", () -> {
            localStorageData = buildAuthData(userId, username, accessToken, refreshToken);
        });

        step("Вход на главную страницу авторизированным", () -> {
            bookClubPage.openFavicon();
            localStorage().setItem("book_club_auth", localStorageData);
            bookClubPage.openMainPage();
        });

        step("Создание клуба", () -> {
            bookClubPage.clickCreateBookClubButton();
            bookClubPage.createClub(
                    bookTitle,
                    bookAuthors,
                    publicationYear.toString(),
                    description,
                    telegramChatLink);
        });

        step("Поиск клуба", () -> {
            bookClubPage.searchByTitle(bookTitle);
        });

        step("Проверка отображения клуба в списке", () -> {
            bookClubPage.clubInListAssert(
                    bookTitle,
                    bookAuthors,
                    publicationYear.toString(),
                    description);
        });

        step("Вход в карточку клуба", () -> {
            bookClubPage.clickActionButtonOnCardByName(bookTitle);
        });

        step("Проверка карточки клуба", () -> {
            bookClubPage.verifyClubContainsInCard(
                    bookTitle,
                    bookAuthors,
                    publicationYear.toString(),
                    description);
        });
    }

    @Test
    public void requiredFieldsInCreateFromUITest() {
        step("Регистрация нового пользователя", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(username);
            registrationRequestModel.setPassword(password);

            SuccessfulRegistrationResponseModel successfulRegistrationResponse = registrationApiClient.successfulRegistration(registrationRequestModel);

            userId = successfulRegistrationResponse.getId().toString();
        });

        step("Получение токенов созданного пользователя", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(username);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel = loginApiClient.successfulLogin(loginRequestModel);

            accessToken = successfulLoginResponseModel.getAccess();
            refreshToken = successfulLoginResponseModel.getRefresh();
        });


        step("Формирование localStorageData", () -> {
            localStorageData = buildAuthData(userId, username, accessToken, refreshToken);
        });

        step("Вход на главную страницу авторизированным", () -> {
            bookClubPage.openFavicon();
            localStorage().setItem("book_club_auth", localStorageData);
            bookClubPage.openMainPage();
        });

        step("Переход в форму создания", () -> {
            bookClubPage.clickCreateBookClubButton();
        });

        step("Нажатие на кнопку создания клуба", () -> {
            bookClubPage.clickSubmitButton();
        });

        step("Проверка подсказок обязательности полей", () -> {
            bookClubPage.createClubFormAssert(List.of(
                    requiredBookTitleMessage,
                    requiredBookAuthorsMessage,
                    requiredPublicationYearMessage,
                    requiredDescriptionMessage,
                    requiredTelegramChatLinkMessage
            ));
        });
    }
}
