package tests.bookclub.api;

import models.login.EmptyCredsLoginResponseModel;
import models.login.IncorrectLoginResponseModel;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.bookclub.BookClubTestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTests extends BookClubTestBase {
    String username = "qaguru";
    String password = "qaguru123";
    String incorrectPassword = "qaguru321";
    String emptyUsername = "";
    String emptyPassword = "";

    @Tag("dz_19")
    @Test
    public void successfulLoginTest() {
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        SuccessfulLoginResponseModel successfulLoginResponseModel = apiClient.login.successfulLogin(loginRequestModel);

        String actualAccess = successfulLoginResponseModel.getAccess();
        String actualRefresh = successfulLoginResponseModel.getRefresh();
        assertThat(actualAccess).isNotEqualTo(actualRefresh);
    }

    @Tag("dz_19")
    @Test
    public void incorrectPasswordLoginTest() {
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(incorrectPassword);

        IncorrectLoginResponseModel incorrectLoginResponseModel = apiClient.login.incorrectPasswordLogin(loginRequestModel);

        String incorrectCredsError = "Invalid username or password.";
        String actualCredsError = incorrectLoginResponseModel.getDetail();

        assertThat(incorrectCredsError).isEqualTo(actualCredsError);
    }

    @Tag("dz_19")
    @Test
    public void emptyCredsLoginTest() {
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(emptyUsername);
        loginRequestModel.setPassword(emptyPassword);

        EmptyCredsLoginResponseModel emptyCredsLoginResponseModel = apiClient.login.emptyCredsLogin(loginRequestModel);

        String expectedUsernameError = "This field may not be blank.";
        String expectedPasswordError = "This field may not be blank.";

        assertEquals(expectedUsernameError, emptyCredsLoginResponseModel.getUsername().get(0));
        assertEquals(expectedPasswordError, emptyCredsLoginResponseModel.getPassword().get(0));
    }
}
