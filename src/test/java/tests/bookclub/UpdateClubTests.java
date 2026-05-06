package tests.bookclub;

import com.github.javafaker.Faker;
import models.club.CreateClubRequestModel;
import models.club.SuccessfulCreateClubResponseModel;
import models.club.SuccessfulUpdateClubResponseModel;
import models.club.UpdateClubRequestModel;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateClubTests extends BookClubTestBase {
    String username;
    String password;
    String bookTitle;
    String bookAuthors;
    Integer publicationYear;
    String description;
    String telegramChatLink;
    String updatedBookTitle;
    String updatedBookAuthors;
    Integer updatedPublicationYear;
    String updatedDescription;
    String updatedTelegramChatLink;

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
        updatedBookTitle = faker.book().title() + " " + faker.number().randomNumber();
        updatedBookAuthors = faker.book().author();
        updatedPublicationYear = faker.number().numberBetween(1900, 2026);
        updatedDescription = faker.lorem().sentence();
        updatedTelegramChatLink = "https://t.me/updated" + username;
    }

    @Test
    public void successfulUpdateClubTest(){
        step("Регистрация нового пользователя", () -> {
            RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
            registrationRequestModel.setUsername(username);
            registrationRequestModel.setPassword(password);

            registrationApiClient.successfulRegistration(registrationRequestModel);
        });

        String accessToken = step("Получение токена созданного пользователя", () -> {
            LoginRequestModel loginRequestModel = new LoginRequestModel();
            loginRequestModel.setUsername(username);
            loginRequestModel.setPassword(password);

            SuccessfulLoginResponseModel successfulLoginResponseModel =
                    loginApiClient.successfulLogin(loginRequestModel);

            return successfulLoginResponseModel.getAccess();
        });

        Integer clubId = step("Создание книжного клуба", () -> {
            CreateClubRequestModel createClubRequestModel = new CreateClubRequestModel();
            createClubRequestModel.setBookTitle(bookTitle);
            createClubRequestModel.setBookAuthors(bookAuthors);
            createClubRequestModel.setPublicationYear(publicationYear);
            createClubRequestModel.setDescription(description);
            createClubRequestModel.setTelegramChatLink(telegramChatLink);

            SuccessfulCreateClubResponseModel successfulCreateClubResponseModel =
                    clubApiClient.successfulCreateClub(accessToken, createClubRequestModel);

            return successfulCreateClubResponseModel.getId();
        });

        step("Редактирование книжного клуба", () -> {
            UpdateClubRequestModel updateClubRequestModel = new UpdateClubRequestModel();
            updateClubRequestModel.setBookTitle(updatedBookTitle);
            updateClubRequestModel.setBookAuthors(updatedBookAuthors);
            updateClubRequestModel.setPublicationYear(updatedPublicationYear);
            updateClubRequestModel.setDescription(updatedDescription);
            updateClubRequestModel.setTelegramChatLink(updatedTelegramChatLink);

            SuccessfulUpdateClubResponseModel successfulUpdateClubResponseModel =
                    clubApiClient.successfulUpdateClub(accessToken, clubId, updateClubRequestModel);

            assertEquals(updatedBookTitle, successfulUpdateClubResponseModel.getBookTitle());
            assertEquals(updatedBookAuthors, successfulUpdateClubResponseModel.getBookAuthors());
            assertEquals(updatedPublicationYear, successfulUpdateClubResponseModel.getPublicationYear());
            assertEquals(updatedDescription, successfulUpdateClubResponseModel.getDescription());
            assertEquals(updatedTelegramChatLink, successfulUpdateClubResponseModel.getTelegramChatLink());
        });
    }
}