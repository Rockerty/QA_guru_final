package tests.bookclub.ui;

import com.github.javafaker.Faker;
import models.club.CreateClubRequestModel;
import models.club.SuccessfulCreateClubResponseModel;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.BookClubPage;
import tests.bookclub.BookClubTestBase;

import static com.codeborne.selenide.Selenide.localStorage;
import static com.codeborne.selenide.Selenide.open;
import static helpers.LocalStorageHelper.buildAuthData;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParticipateClubUITests extends BookClubTestBase {
    BookClubPage bookClubPage = new BookClubPage();

    String username;
    String secondUsername;
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
    Integer clubId;

    @BeforeEach
    public void allTestsSetUp() {
        Faker faker = new Faker();
        username = faker.name().firstName() + faker.number().randomNumber();
        secondUsername = faker.name().firstName() + faker.number().randomNumber();
        password = faker.name().firstName();
        bookTitle = faker.book().title() + " " + faker.number().randomNumber();
        bookAuthors = faker.book().author();
        publicationYear = faker.number().numberBetween(1900, 2026);
        description = faker.lorem().sentence();
        telegramChatLink = "https://t.me/" + username;
    }

    @Test
    public void leaveMyClubTest(){
        step("Регистрация нового пользователя", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(username);
            registrationRequestModel.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequestModel);
        });

        step("Получение токенов созданного пользователя", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(username);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel = loginApiClient.successfulLogin(loginRequestModel);

            accessToken = successfulLoginResponseModel.getAccess();
            refreshToken = successfulLoginResponseModel.getRefresh();
        });

        step("Создание книжного клуба", () -> {
            CreateClubRequestModel createClubRequestModel = new CreateClubRequestModel();
            createClubRequestModel.setBookTitle(bookTitle);
            createClubRequestModel.setBookAuthors(bookAuthors);
            createClubRequestModel.setPublicationYear(publicationYear);
            createClubRequestModel.setDescription(description);
            createClubRequestModel.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    clubApiClient.successfulCreateClub(accessToken, createClubRequestModel);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Формирование localStorageData", () -> {
            localStorageData = buildAuthData(userId, username, accessToken, refreshToken);
        });

        step("Вход в карточку созданного клуба", () -> {
            bookClubPage.openFavicon();
            localStorage().setItem("book_club_auth", localStorageData);
            open("/clubs/" + clubId);
        });

        step("Нажатие кнопки выхода из клуба", () -> {
            bookClubPage.leaveClub();
        });

        step("Проверка ошибки при попытке выхода из своего клуба", () -> {
            bookClubPage.clubDetailMessage("Не удалось покинуть клуб");
        });
    }

    @Test
    public void joinClubTest(){
        step("Регистрация первого пользователя", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(username);
            registrationRequestModel.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequestModel);
        });

        step("Получение токенов созданного пользователя", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(username);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel = loginApiClient.successfulLogin(loginRequestModel);

            accessToken = successfulLoginResponseModel.getAccess();
            refreshToken = successfulLoginResponseModel.getRefresh();
        });

        step("Создание книжного клуба", () -> {
            CreateClubRequestModel createClubRequestModel = new CreateClubRequestModel();
            createClubRequestModel.setBookTitle(bookTitle);
            createClubRequestModel.setBookAuthors(bookAuthors);
            createClubRequestModel.setPublicationYear(publicationYear);
            createClubRequestModel.setDescription(description);
            createClubRequestModel.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    clubApiClient.successfulCreateClub(accessToken, createClubRequestModel);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Регистрация второго пользователя", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(secondUsername);
            registrationRequestModel.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequestModel);
        });

        step("Получение токенов созданного пользователя", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(secondUsername);
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

        step("Поиск клуба", () -> {
            bookClubPage.searchByTitle(bookTitle);
        });

        step("Нажатие на кнопку присоединия к клубу", () -> {
            bookClubPage.clickActionButtonOnCardByName(bookTitle);
        });

        step("Проверка карточки клуба", () -> {
            bookClubPage.verifyClubContainsInCard(bookTitle, bookAuthors,
                    publicationYear.toString(), description);
        });

        step("Проверка наличия кнопки для выхода из клуба", () -> {
            bookClubPage.leaveClubButtonExistsAssert();
        });
    }
}
