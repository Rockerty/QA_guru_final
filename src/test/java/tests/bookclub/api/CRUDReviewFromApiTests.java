package tests.bookclub.api;

import com.github.javafaker.Faker;
import models.club.*;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.bookclub.BookClubTestBase;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CRUDReviewFromApiTests extends BookClubTestBase {
    String creatorUsername;
    String participantUsername;
    String password;
    String creatorRefreshToken;
    String participantRefreshToken;
    String bookTitle;
    String bookAuthors;
    Integer publicationYear;
    String description;
    String telegramChatLink;
    Integer clubId;
    String creatorAccessToken;
    String participantAccessToken;
    Integer readPages;
    String reviewText;
    Integer clubAssessment;
    Integer updatedReadPages;
    String updatedReviewText;
    Integer updatedClubAssessment;
    Integer reviewId;

    @BeforeEach
    public void allTestsSetUp() {
        Faker faker = new Faker();
        creatorUsername = faker.name().firstName() + faker.number().randomNumber();
        participantUsername = faker.name().firstName() + faker.number().randomNumber();
        password = faker.name().firstName();
        bookTitle = faker.book().title() + " " + faker.number().randomNumber();
        bookAuthors = faker.book().author();
        publicationYear = faker.number().numberBetween(1900, 2026);
        description = faker.lorem().sentence();
        telegramChatLink = "https://t.me/" + creatorUsername;
        readPages = faker.number().numberBetween(1, 1000);
        reviewText = faker.lorem().sentence();
        clubAssessment = faker.number().numberBetween(1, 6);
        updatedReadPages = faker.number().numberBetween(1, 1000);
        updatedReviewText = faker.lorem().sentence();
        updatedClubAssessment = faker.number().numberBetween(1, 6);
    }

    @Tag("dz_19")
    @Test
    public void successfulCreateClubReviewTest(){
        step("Регистрация создателя клуба", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(creatorUsername);
            registrationRequestModel.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequestModel);
        });

        step("Получение токена созданного пользователя (создателя)", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(creatorUsername);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    loginApiClient.successfulLogin(loginRequestModel);

            creatorAccessToken = successfulLoginResponseModel.getAccess();
            creatorRefreshToken = successfulLoginResponseModel.getRefresh();
        });

        step("Создание книжного клуба", () -> {
            CreateClubRequestModel createClubRequest = new CreateClubRequestModel();
            createClubRequest.setBookTitle(bookTitle);
            createClubRequest.setBookAuthors(bookAuthors);
            createClubRequest.setPublicationYear(publicationYear);
            createClubRequest.setDescription(description);
            createClubRequest.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    clubApiClient.successfulCreateClub(creatorAccessToken, createClubRequest);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Создание отзыва о клубе", () -> {
            CreateClubReviewRequestModel createClubReviewRequest = new CreateClubReviewRequestModel();
            createClubReviewRequest.setClub(clubId);
            createClubReviewRequest.setReview(reviewText);
            createClubReviewRequest.setAssessment(clubAssessment);
            createClubReviewRequest.setReadPages(readPages);

            SuccessfulCreateReviewResponseModel successfulCreateReviewResponseModel = clubApiClient.successfulCreateReview(creatorAccessToken, createClubReviewRequest);

            reviewId = successfulCreateReviewResponseModel.getId();
        });

        step("Чтение отзыва клуба", () -> {
            GetReviewResponseModel getReviewResponse = clubApiClient.successfulGetReview(reviewId);

            assertEquals(clubAssessment, getReviewResponse.getAssessment());
            assertEquals(clubId, getReviewResponse.getClub());
            assertEquals(creatorUsername, getReviewResponse.getUser().getUsername());
            assertEquals(reviewText, getReviewResponse.getReview());
            assertEquals(readPages, getReviewResponse.getReadPages());
        });
    }

    @Tag("dz_19")
    @Test
    public void successfulUpdateClubReviewTest(){
        step("Регистрация создателя клуба", () -> {
            RegistrationRequestModel registrationRequest = new RegistrationRequestModel();
            registrationRequest.setUsername(creatorUsername);
            registrationRequest.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequest);
        });

        step("Получение токена созданного пользователя (создателя)", () -> {
            LoginRequestModel loginRequest = new LoginRequestModel();
            loginRequest.setUsername(creatorUsername);
            loginRequest.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    loginApiClient.successfulLogin(loginRequest);

            creatorAccessToken = successfulLoginResponseModel.getAccess();
            creatorRefreshToken = successfulLoginResponseModel.getRefresh();
        });

        step("Создание книжного клуба", () -> {
            CreateClubRequestModel createClubRequest = new CreateClubRequestModel();
            createClubRequest.setBookTitle(bookTitle);
            createClubRequest.setBookAuthors(bookAuthors);
            createClubRequest.setPublicationYear(publicationYear);
            createClubRequest.setDescription(description);
            createClubRequest.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    clubApiClient.successfulCreateClub(creatorAccessToken, createClubRequest);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Создание отзыва о клубе", () -> {
            CreateClubReviewRequestModel createClubReviewRequest = new CreateClubReviewRequestModel();
            createClubReviewRequest.setClub(clubId);
            createClubReviewRequest.setReview(reviewText);
            createClubReviewRequest.setAssessment(clubAssessment);
            createClubReviewRequest.setReadPages(readPages);

            SuccessfulCreateReviewResponseModel successfulCreateReviewResponseModel = clubApiClient.successfulCreateReview(creatorAccessToken, createClubReviewRequest);

            reviewId = successfulCreateReviewResponseModel.getId();
        });

        step("Редактирование отзыва о клубе", () -> {
            UpdateReviewRequestModel updateReviewRequest = new UpdateReviewRequestModel();
            updateReviewRequest.setClub(clubId);
            updateReviewRequest.setReview(updatedReviewText);
            updateReviewRequest.setAssessment(updatedClubAssessment);
            updateReviewRequest.setReadPages(updatedReadPages);

            SuccessfulUpdateReviewResponseModel successfulUpdateReviewResponseModel = clubApiClient.successfulUpdateReviewResponseModel(creatorAccessToken, reviewId, updateReviewRequest);

            assertEquals(updatedClubAssessment, successfulUpdateReviewResponseModel.getAssessment());
            assertEquals(clubId, successfulUpdateReviewResponseModel.getClub());
            assertEquals(creatorUsername, successfulUpdateReviewResponseModel.getUser().getUsername());
            assertEquals(updatedReviewText, successfulUpdateReviewResponseModel.getReview());
            assertEquals(updatedReadPages, successfulUpdateReviewResponseModel.getReadPages());
        });
    }

    @Tag("dz_19")
    @Test
    public void successfulDeleteClubReviewAsCreatorTest(){
        step("Регистрация создателя клуба", () -> {
            RegistrationRequestModel registrationRequest = new RegistrationRequestModel();
            registrationRequest.setUsername(creatorUsername);
            registrationRequest.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequest);
        });

        step("Получение токена созданного пользователя (создателя)", () -> {
            LoginRequestModel loginRequest = new LoginRequestModel();
            loginRequest.setUsername(creatorUsername);
            loginRequest.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    loginApiClient.successfulLogin(loginRequest);

            creatorAccessToken = successfulLoginResponseModel.getAccess();
            creatorRefreshToken = successfulLoginResponseModel.getRefresh();
        });

        step("Создание книжного клуба", () -> {
            CreateClubRequestModel createClubRequest = new CreateClubRequestModel();
            createClubRequest.setBookTitle(bookTitle);
            createClubRequest.setBookAuthors(bookAuthors);
            createClubRequest.setPublicationYear(publicationYear);
            createClubRequest.setDescription(description);
            createClubRequest.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    clubApiClient.successfulCreateClub(creatorAccessToken, createClubRequest);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Создание отзыва о клубе", () -> {
            CreateClubReviewRequestModel createClubReviewRequest = new CreateClubReviewRequestModel();
            createClubReviewRequest.setClub(clubId);
            createClubReviewRequest.setReview(reviewText);
            createClubReviewRequest.setAssessment(clubAssessment);
            createClubReviewRequest.setReadPages(readPages);

            SuccessfulCreateReviewResponseModel successfulCreateReviewResponseModel = clubApiClient.successfulCreateReview(creatorAccessToken, createClubReviewRequest);

            reviewId = successfulCreateReviewResponseModel.getId();
        });

        step("Удаление отзыва о клубе", () -> {
            clubApiClient.SuccessfulDeleteReviewClub(creatorAccessToken, reviewId);
        });
    }

    @Tag("dz_19")
    @Test
    public void deleteClubReviewAsParticipantTest(){
        step("Регистрация создателя клуба и отзыва", () -> {
            RegistrationRequestModel registrationRequest = new RegistrationRequestModel();
            registrationRequest.setUsername(creatorUsername);
            registrationRequest.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequest);
        });

        step("Получение токенов создателя", () -> {
            LoginRequestModel loginRequest = new LoginRequestModel();
            loginRequest.setUsername(creatorUsername);
            loginRequest.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel = loginApiClient.successfulLogin(loginRequest);

            creatorAccessToken = successfulLoginResponseModel.getAccess();
            creatorRefreshToken = successfulLoginResponseModel.getRefresh();
        });

        step("Создание книжного клуба", () -> {
            CreateClubRequestModel createClubRequest = new CreateClubRequestModel();
            createClubRequest.setBookTitle(bookTitle);
            createClubRequest.setBookAuthors(bookAuthors);
            createClubRequest.setPublicationYear(publicationYear);
            createClubRequest.setDescription(description);
            createClubRequest.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    clubApiClient.successfulCreateClub(creatorAccessToken, createClubRequest);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        });

        step("Создание отзыва о клубе", () -> {
            CreateClubReviewRequestModel createClubReviewRequest = new CreateClubReviewRequestModel();
            createClubReviewRequest.setClub(clubId);
            createClubReviewRequest.setReview(reviewText);
            createClubReviewRequest.setAssessment(clubAssessment);
            createClubReviewRequest.setReadPages(readPages);

            SuccessfulCreateReviewResponseModel successfulCreateReviewResponseModel = clubApiClient.successfulCreateReview(creatorAccessToken, createClubReviewRequest);

            reviewId = successfulCreateReviewResponseModel.getId();
        });

        step("Регистрация участника клуба", () -> {
            RegistrationRequestModel registrationRequest = new RegistrationRequestModel();
            registrationRequest.setUsername(participantUsername);
            registrationRequest.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequest);
        });

        step("Получение токенов участника", () -> {
            LoginRequestModel loginRequest = new LoginRequestModel();
            loginRequest.setUsername(participantUsername);
            loginRequest.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel = loginApiClient.successfulLogin(loginRequest);

            participantAccessToken = successfulLoginResponseModel.getAccess();
            participantRefreshToken = successfulLoginResponseModel.getRefresh();
        });

        step("Удаление участником отзыва создателя", () -> {
            InvalidDeleteReviewResponseModel invalidDeleteReviewResponse = clubApiClient.invalidDeleteReviewClub(participantAccessToken, reviewId);

            assertEquals("You do not have permission to perform this action.", invalidDeleteReviewResponse.getDetail());
        });
    }
}
