package tests.bookclub;

import com.github.javafaker.Faker;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationRequestModel;
import models.update.InvalidEmailUpdateResponseModel;
import models.update.SuccessfulUpdateResponseModel;
import models.update.UpdateRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserTests extends BookClubTestBase {
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    String invalidEmail;
    String emptyEmail;

    @BeforeEach
    public void allTestsSetUp() {
        Faker faker = new Faker();
        username = faker.name().firstName() + faker.number().randomNumber();
        password = faker.name().firstName();
        firstName = faker.name().firstName();
        lastName = faker.name().firstName();
        email = faker.internet().emailAddress();
    }

    @Test
    public void successfulUpdateUserTest(){
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

        step("Редактирование пользователя", () -> {
            UpdateRequestModel updateRequestModel = new UpdateRequestModel();
            updateRequestModel.setUsername(username);
            updateRequestModel.setFirstName(firstName);
            updateRequestModel.setLastName(lastName);
            updateRequestModel.setEmail(email);

            SuccessfulUpdateResponseModel successfulUpdateResponseModel =
                    updateUserApiClient.successfulUpdateUser(accessToken, updateRequestModel);

            assertEquals(username, successfulUpdateResponseModel.getUsername());
            assertEquals(firstName, successfulUpdateResponseModel.getFirstName());
            assertEquals(lastName, successfulUpdateResponseModel.getLastName());
            assertEquals(email, successfulUpdateResponseModel.getEmail());
        });
    }

    @Test
    public void invalidEmailUpdateUserTest() {
        invalidEmail = "isNotEmail";

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

        step("Редактирование пользователя с некорректным email", () -> {
            UpdateRequestModel updateRequestModel = new UpdateRequestModel();
            updateRequestModel.setUsername(username);
            updateRequestModel.setFirstName(firstName);
            updateRequestModel.setLastName(lastName);
            updateRequestModel.setEmail(invalidEmail);

            InvalidEmailUpdateResponseModel invalidEmailUpdateResponseModel =
                    updateUserApiClient.invalidEmailUpdateUser(accessToken, updateRequestModel);

            String expectedError = "Enter a valid email address.";

            assertEquals(expectedError, invalidEmailUpdateResponseModel.getEmail().get(0));
        });
    }

    @Test
    public void nullEmailUpdateUserTest() {
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

        step("Редактирование пользователя с некорректным email", () -> {
            UpdateRequestModel updateRequestModel = new UpdateRequestModel();
            updateRequestModel.setUsername(username);
            updateRequestModel.setFirstName(firstName);
            updateRequestModel.setLastName(lastName);
            updateRequestModel.setEmail(emptyEmail);

            InvalidEmailUpdateResponseModel invalidEmailUpdateResponseModel =
                    updateUserApiClient.nullEmailUpdateUser(accessToken, updateRequestModel);

            String expectedError = "This field may not be null.";

            assertEquals(expectedError, invalidEmailUpdateResponseModel.getEmail().get(0));
        });
    }
}