package tests.bookclub.api;

import com.github.javafaker.Faker;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationRequestModel;
import models.update.InvalidEmailUpdateResponseModel;
import models.update.SuccessfulUpdateResponseModel;
import models.update.UpdateRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.bookclub.BookClubTestBase;

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

    @Tag("dz_19")
    @Test
    public void successfulUpdateUserTest(){
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

        {
            UpdateRequestModel updateRequestModel = new UpdateRequestModel();
            updateRequestModel.setUsername(username);
            updateRequestModel.setFirstName(firstName);
            updateRequestModel.setLastName(lastName);
            updateRequestModel.setEmail(email);

            SuccessfulUpdateResponseModel successfulUpdateResponseModel =
                    apiClient.updateUser.successfulUpdateUser(accessToken, updateRequestModel);

            assertEquals(username, successfulUpdateResponseModel.getUsername());
            assertEquals(firstName, successfulUpdateResponseModel.getFirstName());
            assertEquals(lastName, successfulUpdateResponseModel.getLastName());
            assertEquals(email, successfulUpdateResponseModel.getEmail());
        }
    }

    @Tag("dz_19")
    @Test
    public void invalidEmailUpdateUserTest() {
        invalidEmail = "isNotEmail";

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

        {
            UpdateRequestModel updateRequestModel = new UpdateRequestModel();
            updateRequestModel.setUsername(username);
            updateRequestModel.setFirstName(firstName);
            updateRequestModel.setLastName(lastName);
            updateRequestModel.setEmail(invalidEmail);

            InvalidEmailUpdateResponseModel invalidEmailUpdateResponseModel =
                    apiClient.updateUser.invalidEmailUpdateUser(accessToken, updateRequestModel);

            String expectedError = "Enter a valid email address.";

            assertEquals(expectedError, invalidEmailUpdateResponseModel.getEmail().get(0));
        }
    }

    @Tag("dz_19")
    @Test
    public void nullEmailUpdateUserTest() {
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

        {
            UpdateRequestModel updateRequestModel = new UpdateRequestModel();
            updateRequestModel.setUsername(username);
            updateRequestModel.setFirstName(firstName);
            updateRequestModel.setLastName(lastName);
            updateRequestModel.setEmail(emptyEmail);

            InvalidEmailUpdateResponseModel invalidEmailUpdateResponseModel =
                    apiClient.updateUser.nullEmailUpdateUser(accessToken, updateRequestModel);

            String expectedError = "This field may not be null.";

            assertEquals(expectedError, invalidEmailUpdateResponseModel.getEmail().get(0));
        }
    }
}