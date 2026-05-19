package tests.bookclub.api;

import com.github.javafaker.Faker;
import models.club.CreateClubRequestModel;
import models.club.SuccessfulCreateClubResponseModel;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.bookclub.BookClubTestBase;

public class DeleteClubTests extends BookClubTestBase {
    String username;
    String password;
    String bookTitle;
    String bookAuthors;
    Integer publicationYear;
    String description;
    String telegramChatLink;

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

    @Tag("dz_19")
    @Test
    public void successfulDeleteClubTest(){
        {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(username);
            registrationRequestModel.setPassword(password);

            apiClient.registration.successfulRegistration(registrationRequestModel);
        }

        String accessToken;
        {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(username);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    apiClient.login.successfulLogin(loginRequestModel);

            accessToken = successfulLoginResponseModel.getAccess();
        }

        Integer clubId;
        {
            CreateClubRequestModel createClubRequestModel = new CreateClubRequestModel();
            createClubRequestModel.setBookTitle(bookTitle);
            createClubRequestModel.setBookAuthors(bookAuthors);
            createClubRequestModel.setPublicationYear(publicationYear);
            createClubRequestModel.setDescription(description);
            createClubRequestModel.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    apiClient.club.successfulCreateClub(accessToken, createClubRequestModel);

            clubId = successfulCreateClubResponseModel.getId();
        }

        {
            apiClient.club.successfulDeleteClub(accessToken, clubId);
        }
    }
}