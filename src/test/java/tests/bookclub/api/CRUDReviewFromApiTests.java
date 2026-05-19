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
        {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(creatorUsername);
            registrationRequestModel.setPassword(password);

            apiClient.registration.successfulRegistration(registrationRequestModel);
        }

        {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(creatorUsername);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    apiClient.login.successfulLogin(loginRequestModel);

            creatorAccessToken = successfulLoginResponseModel.getAccess();
            creatorRefreshToken = successfulLoginResponseModel.getRefresh();
        }

        {
            CreateClubRequestModel createClubRequest = new CreateClubRequestModel();
            createClubRequest.setBookTitle(bookTitle);
            createClubRequest.setBookAuthors(bookAuthors);
            createClubRequest.setPublicationYear(publicationYear);
            createClubRequest.setDescription(description);
            createClubRequest.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    apiClient.club.successfulCreateClub(creatorAccessToken, createClubRequest);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        }

        {
            CreateClubReviewRequestModel createClubReviewRequest = new CreateClubReviewRequestModel();
            createClubReviewRequest.setClub(clubId);
            createClubReviewRequest.setReview(reviewText);
            createClubReviewRequest.setAssessment(clubAssessment);
            createClubReviewRequest.setReadPages(readPages);

            SuccessfulCreateReviewResponseModel successfulCreateReviewResponseModel = apiClient.club.successfulCreateReview(creatorAccessToken, createClubReviewRequest);

            reviewId = successfulCreateReviewResponseModel.getId();
        }

            GetReviewResponseModel getReviewResponse = apiClient.club.successfulGetReview(reviewId);

            assertEquals(clubAssessment, getReviewResponse.getAssessment());
            assertEquals(clubId, getReviewResponse.getClub());
            assertEquals(creatorUsername, getReviewResponse.getUser().getUsername());
            assertEquals(reviewText, getReviewResponse.getReview());
            assertEquals(readPages, getReviewResponse.getReadPages());

    }

    @Tag("dz_19")
    @Test
    public void successfulUpdateClubReviewTest(){
        {
            RegistrationRequestModel registrationRequest = new RegistrationRequestModel();
            registrationRequest.setUsername(creatorUsername);
            registrationRequest.setPassword(password);

            apiClient.registration.successfulRegistration(registrationRequest);
        }

        {
            LoginRequestModel loginRequest = new LoginRequestModel();
            loginRequest.setUsername(creatorUsername);
            loginRequest.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    apiClient.login.successfulLogin(loginRequest);

            creatorAccessToken = successfulLoginResponseModel.getAccess();
            creatorRefreshToken = successfulLoginResponseModel.getRefresh();
        }

        {
            CreateClubRequestModel createClubRequest = new CreateClubRequestModel();
            createClubRequest.setBookTitle(bookTitle);
            createClubRequest.setBookAuthors(bookAuthors);
            createClubRequest.setPublicationYear(publicationYear);
            createClubRequest.setDescription(description);
            createClubRequest.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    apiClient.club.successfulCreateClub(creatorAccessToken, createClubRequest);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        }

        {
            CreateClubReviewRequestModel createClubReviewRequest = new CreateClubReviewRequestModel();
            createClubReviewRequest.setClub(clubId);
            createClubReviewRequest.setReview(reviewText);
            createClubReviewRequest.setAssessment(clubAssessment);
            createClubReviewRequest.setReadPages(readPages);

            SuccessfulCreateReviewResponseModel successfulCreateReviewResponseModel = apiClient.club.successfulCreateReview(creatorAccessToken, createClubReviewRequest);

            reviewId = successfulCreateReviewResponseModel.getId();
        }

        {
            UpdateReviewRequestModel updateReviewRequest = new UpdateReviewRequestModel();
            updateReviewRequest.setClub(clubId);
            updateReviewRequest.setReview(updatedReviewText);
            updateReviewRequest.setAssessment(updatedClubAssessment);
            updateReviewRequest.setReadPages(updatedReadPages);

            SuccessfulUpdateReviewResponseModel successfulUpdateReviewResponseModel = apiClient.club.successfulUpdateReview(creatorAccessToken, reviewId, updateReviewRequest);

            assertEquals(updatedClubAssessment, successfulUpdateReviewResponseModel.getAssessment());
            assertEquals(clubId, successfulUpdateReviewResponseModel.getClub());
            assertEquals(creatorUsername, successfulUpdateReviewResponseModel.getUser().getUsername());
            assertEquals(updatedReviewText, successfulUpdateReviewResponseModel.getReview());
            assertEquals(updatedReadPages, successfulUpdateReviewResponseModel.getReadPages());
        }
    }

    @Tag("dz_19")
    @Test
    public void successfulDeleteClubReviewAsCreatorTest(){
        {
            RegistrationRequestModel registrationRequest = new RegistrationRequestModel();
            registrationRequest.setUsername(creatorUsername);
            registrationRequest.setPassword(password);

            apiClient.registration.successfulRegistration(registrationRequest);
        }

        {
            LoginRequestModel loginRequest = new LoginRequestModel();
            loginRequest.setUsername(creatorUsername);
            loginRequest.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    apiClient.login.successfulLogin(loginRequest);

            creatorAccessToken = successfulLoginResponseModel.getAccess();
            creatorRefreshToken = successfulLoginResponseModel.getRefresh();
        }

        {
            CreateClubRequestModel createClubRequest = new CreateClubRequestModel();
            createClubRequest.setBookTitle(bookTitle);
            createClubRequest.setBookAuthors(bookAuthors);
            createClubRequest.setPublicationYear(publicationYear);
            createClubRequest.setDescription(description);
            createClubRequest.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    apiClient.club.successfulCreateClub(creatorAccessToken, createClubRequest);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        }

        {
            CreateClubReviewRequestModel createClubReviewRequest = new CreateClubReviewRequestModel();
            createClubReviewRequest.setClub(clubId);
            createClubReviewRequest.setReview(reviewText);
            createClubReviewRequest.setAssessment(clubAssessment);
            createClubReviewRequest.setReadPages(readPages);

            SuccessfulCreateReviewResponseModel successfulCreateReviewResponseModel = apiClient.club.successfulCreateReview(creatorAccessToken, createClubReviewRequest);

            reviewId = successfulCreateReviewResponseModel.getId();
        }

        {
            apiClient.club.SuccessfulDeleteReviewClub(creatorAccessToken, reviewId);
        }
    }

    @Tag("dz_19")
    @Test
    public void deleteClubReviewAsParticipantTest(){
        {
            RegistrationRequestModel registrationRequest = new RegistrationRequestModel();
            registrationRequest.setUsername(creatorUsername);
            registrationRequest.setPassword(password);

            apiClient.registration.successfulRegistration(registrationRequest);
        }

        {
            LoginRequestModel loginRequest = new LoginRequestModel();
            loginRequest.setUsername(creatorUsername);
            loginRequest.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel = apiClient.login.successfulLogin(loginRequest);

            creatorAccessToken = successfulLoginResponseModel.getAccess();
            creatorRefreshToken = successfulLoginResponseModel.getRefresh();
        }

        {
            CreateClubRequestModel createClubRequest = new CreateClubRequestModel();
            createClubRequest.setBookTitle(bookTitle);
            createClubRequest.setBookAuthors(bookAuthors);
            createClubRequest.setPublicationYear(publicationYear);
            createClubRequest.setDescription(description);
            createClubRequest.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    apiClient.club.successfulCreateClub(creatorAccessToken, createClubRequest);

            clubId = successfulCreateClubResponseModel.getId();

            assertEquals(bookTitle, successfulCreateClubResponseModel.getBookTitle());
            assertEquals(bookAuthors, successfulCreateClubResponseModel.getBookAuthors());
            assertEquals(publicationYear, successfulCreateClubResponseModel.getPublicationYear());
            assertEquals(description, successfulCreateClubResponseModel.getDescription());
            assertEquals(telegramChatLink, successfulCreateClubResponseModel.getTelegramChatLink());
        }

        {
            CreateClubReviewRequestModel createClubReviewRequest = new CreateClubReviewRequestModel();
            createClubReviewRequest.setClub(clubId);
            createClubReviewRequest.setReview(reviewText);
            createClubReviewRequest.setAssessment(clubAssessment);
            createClubReviewRequest.setReadPages(readPages);

            SuccessfulCreateReviewResponseModel successfulCreateReviewResponseModel = apiClient.club.successfulCreateReview(creatorAccessToken, createClubReviewRequest);

            reviewId = successfulCreateReviewResponseModel.getId();
        }

        {
            RegistrationRequestModel registrationRequest = new RegistrationRequestModel();
            registrationRequest.setUsername(participantUsername);
            registrationRequest.setPassword(password);

            apiClient.registration.successfulRegistration(registrationRequest);
        }

        {
            LoginRequestModel loginRequest = new LoginRequestModel();
            loginRequest.setUsername(participantUsername);
            loginRequest.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel = apiClient.login.successfulLogin(loginRequest);

            participantAccessToken = successfulLoginResponseModel.getAccess();
            participantRefreshToken = successfulLoginResponseModel.getRefresh();
        }

        {
            InvalidDeleteReviewResponseModel invalidDeleteReviewResponse = apiClient.club.invalidDeleteReviewClub(participantAccessToken, reviewId);

            assertEquals("You do not have permission to perform this action.", invalidDeleteReviewResponse.getDetail());
        }
    }
}
