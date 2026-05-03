package tests.bookclub;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import models.registration.NoUsernameRegistrationRequestModel;
import models.registration.NoUsernameRegistrationResponseModel;
import models.registration.RegistrationRequestModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTests extends BookClubTestBase {

    String username;
    String password;

    @BeforeEach
    public void allTestsSetUp() {
        Faker faker = new Faker();
        username = faker.name().firstName() + faker.number().randomNumber();
        password = faker.internet().password();
    }

    @Test
    public void successfulRegistrationTest(){
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setUsername(username);
        registrationRequestModel.setPassword(password);

        step("Успешная регистрация", () -> {
            SuccessfulRegistrationResponseModel successfulRegistrationResponse = registrationApiClient.successfulRegistration(registrationRequestModel);

            assertEquals(username, successfulRegistrationResponse.getUsername());
        });
    }

    @Test
    public void notUniqUserRegistrationTest(){
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setUsername("Racquel");
        registrationRequestModel.setPassword("Siobhan");

        step("Регистрация: пользователь уже существует", () -> {
            Response notUniqUserRegistrationResponse = registrationApiClient.notUniqUserRegistration(registrationRequestModel);

            String expectedError = "A user with that username already exists.";

            assertEquals(expectedError, notUniqUserRegistrationResponse.path("username[0]"));
        });
    }

    @Test
    public void noUsernameRegistrationTest(){
        NoUsernameRegistrationRequestModel noUsernameRegistrationRequestModel = new NoUsernameRegistrationRequestModel();
        noUsernameRegistrationRequestModel.setPassword(password);

        step("Регистрация: имя пользователя отсутствует", () -> {
            NoUsernameRegistrationResponseModel noUsernameRegistrationResponseModel = registrationApiClient.noUsernameRegistration(noUsernameRegistrationRequestModel);

            String expectedError = "This field is required.";

            assertEquals(expectedError, noUsernameRegistrationResponseModel.getUsername().get(0));
        });
    }
}
