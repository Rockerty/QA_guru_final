package tests.bookclub.ui;

import com.github.javafaker.Faker;
import models.club.CreateClubRequestModel;
import models.club.CreateClubReviewRequestModel;
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

public class CreateClubReviewTests extends BookClubTestBase {
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
    String creatorAccessToken;
    String reviewerAccessToken;
    String readPages;
    String reviewText;
    String clubAssessment;
    String updatedReadPages;
    String updatedReviewText;
    String updatedClubAssessment;

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
        readPages = faker.number().digits(5);
        reviewText = faker.lorem().sentence();
        clubAssessment = String.valueOf(faker.number().numberBetween(1, 6));
        updatedReadPages = faker.number().digits(5);
        updatedReviewText = faker.lorem().sentence();
        updatedClubAssessment = String.valueOf(faker.number().numberBetween(1, 6));
    }

    @Test
    public void successfulCreateClubReviewTest(){
        step("Регистрация создателя клуба", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(username);
            registrationRequestModel.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequestModel);
        });

        creatorAccessToken = step("Получение токена созданного пользователя (создателя)", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(username);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    loginApiClient.successfulLogin(loginRequestModel);

            return successfulLoginResponseModel.getAccess();
        });

        step("Создание книжного клуба", () -> {
            CreateClubRequestModel createClubRequestModel = new CreateClubRequestModel();
            createClubRequestModel.setBookTitle(bookTitle);
            createClubRequestModel.setBookAuthors(bookAuthors);
            createClubRequestModel.setPublicationYear(publicationYear);
            createClubRequestModel.setDescription(description);
            createClubRequestModel.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    clubApiClient.successfulCreateClub(creatorAccessToken, createClubRequestModel);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Регистрация ревьюера", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(secondUsername);
            registrationRequestModel.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequestModel);
        });

        step("Получение токена созданного пользователя (ревьюера)", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(secondUsername);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    loginApiClient.successfulLogin(loginRequestModel);

            reviewerAccessToken = successfulLoginResponseModel.getAccess();
            refreshToken = successfulLoginResponseModel.getRefresh();
        });

        step("Формирование localStorageData", () -> {
            localStorageData = buildAuthData(userId, username, reviewerAccessToken, refreshToken);
            System.out.println("localStorageData: " + localStorageData);
        });

        step("Присоединение к клубу", () -> {
            clubApiClient.successfulJoinClub(reviewerAccessToken, clubId);
        });

        step("Вход в карточку созданного клуба", () -> {
            bookClubPage.openFavicon();
            localStorage().setItem("book_club_auth", localStorageData);
            open("/clubs/" + clubId);
        });

        step("Создание отзыва", () -> {
            bookClubPage.successfulCreateReview(clubAssessment, readPages, reviewText);
        });

        step("Проверка отзыва в карточке клуба", () -> {
            bookClubPage.userClubReviewAssertInCard(secondUsername, readPages, reviewText);
        });
    }

    @Test
    public void successfulUpdateClubReviewFromUITest(){
        step("Регистрация создателя клуба", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(username);
            registrationRequestModel.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequestModel);
        });

        step("Получение токена созданного пользователя (создателя)", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(username);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    loginApiClient.successfulLogin(loginRequestModel);

            creatorAccessToken = successfulLoginResponseModel.getAccess();
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
                    clubApiClient.successfulCreateClub(creatorAccessToken, createClubRequestModel);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Создание отзыва о клубе", () -> {
            CreateClubReviewRequestModel createClubReviewRequestModel = new CreateClubReviewRequestModel();
            createClubReviewRequestModel.setClub(clubId);
            createClubReviewRequestModel.setReview(reviewText);
            createClubReviewRequestModel.setAssessment(Integer.valueOf(clubAssessment));
            createClubReviewRequestModel.setReadPages(Integer.valueOf(readPages));

            clubApiClient.successfulCreateReview(creatorAccessToken, createClubReviewRequestModel);
        });

        step("Формирование localStorageData", () -> {
            localStorageData = buildAuthData(userId, username, creatorAccessToken, refreshToken);
            System.out.println("localStorageData: " + localStorageData);
        });

        step("Вход в карточку созданного клуба", () -> {
            bookClubPage.openFavicon();
            localStorage().setItem("book_club_auth", localStorageData);
            open("/clubs/" + clubId);
        });

        step("Редактирование отзыва", () -> {
            bookClubPage.successfulUpdateReview(updatedClubAssessment, updatedReadPages, updatedReviewText);
        });

        step("Проверка отзыва в карточке клуба", () -> {
            bookClubPage.userClubReviewAssertInCard(username, updatedReadPages, updatedReviewText);
        });
    }
}
