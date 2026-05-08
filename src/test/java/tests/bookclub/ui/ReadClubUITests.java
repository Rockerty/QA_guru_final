package tests.bookclub.ui;

import com.github.javafaker.Faker;
import models.club.CreateClubRequestModel;
import models.club.SuccessfulCreateClubResponseModel;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationRequestModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.BookClubPage;
import tests.bookclub.BookClubTestBase;

import static com.codeborne.selenide.Selenide.localStorage;
import static helpers.LocalStorageHelper.buildAuthData;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadClubUITests extends BookClubTestBase {
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
    Integer clubId;

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
    }

    @Test
    public void myClubsTabUITest(){
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

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Формирование localStorageData", () -> {
            localStorageData = buildAuthData(userId, username, accessToken, refreshToken);
        });

        step("Вход на главную страницу авторизированным", () -> {
            bookClubPage.openFavicon();
            localStorage().setItem("book_club_auth", localStorageData);
            bookClubPage.openMainPage();
        });

        step("Переход во вкладку 'Мои клубы'", () -> {
            bookClubPage.enterTabByName("Мои клубы");
        });

        step("Проверка отображения клуба в списке", () -> {
            bookClubPage.clubInListAssert(bookTitle, bookAuthors,
                    publicationYear.toString(), description);
        });
    }

    @Test
    public void participateClubsTabUITest(){
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

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Формирование localStorageData", () -> {
            localStorageData = buildAuthData(userId, username, accessToken, refreshToken);
        });

        step("Вход на главную страницу авторизированным", () -> {
            bookClubPage.openFavicon();
            localStorage().setItem("book_club_auth", localStorageData);
            bookClubPage.openMainPage();
        });

        step("Переход во вкладку 'Участвую'", () -> {
            bookClubPage.enterTabByName("Участвую");
        });

        step("Проверка отображения клуба в списке", () -> {
            bookClubPage.clubInListAssert(bookTitle, bookAuthors,
                    publicationYear.toString(), description);
        });
    }
}
