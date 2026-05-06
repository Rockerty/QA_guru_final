package tests.bookclub;

import models.login.EmptyCredsLoginResponseModel;
import models.login.IncorrectLoginResponseModel;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTests extends BookClubTestBase {
    String username = "qaguru";
    String password = "qaguru123";
    String incorrectPassword = "qaguru321";
    String emptyUsername = "";
    String emptyPassword = "";


    @Test
    public void successfulLoginTest() {
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        step("Успешная авторизация", () -> {
            SuccessfulLoginResponseModel successfulLoginResponseModel = loginApiClient.successfulLogin(loginRequestModel);

            String actualAccess = successfulLoginResponseModel.getAccess();
            String actualRefresh = successfulLoginResponseModel.getRefresh();
            assertThat(actualAccess).isNotEqualTo(actualRefresh);
        });
    }

    @Test
    public void incorrectPasswordLoginTest(){
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(incorrectPassword);

        step("Авторизация: некорректный пароль", () -> {
            IncorrectLoginResponseModel incorrectLoginResponseModel = loginApiClient.incorrectPasswordLogin(loginRequestModel);

            String incorrectCredsError = "Invalid username or password.";
            String actualCredsError = incorrectLoginResponseModel.getDetail();

            assertThat(incorrectCredsError).isEqualTo(actualCredsError);
        });
    }

    @Test
    public void emptyCredsLoginTest(){
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(emptyUsername);
        loginRequestModel.setPassword(emptyPassword);

        step("Авторизация: пустые имя и пароль", () -> {
            EmptyCredsLoginResponseModel emptyCredsLoginResponseModel = loginApiClient.emptyCredsLogin(loginRequestModel);

            String expectedUsernameError = "This field may not be blank.";
            String expectedPasswordError = "This field may not be blank.";

            assertEquals(expectedUsernameError, emptyCredsLoginResponseModel.getUsername().get(0));
            assertEquals(expectedPasswordError, emptyCredsLoginResponseModel.getPassword().get(0));
        });
    }
}
