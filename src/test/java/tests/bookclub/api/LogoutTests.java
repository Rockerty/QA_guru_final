package tests.bookclub.api;

import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.logout.IncorrectRefreshLogoutResponseModel;
import models.logout.LogoutRequestModel;
import models.logout.NoRefreshLogoutResponseModel;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.bookclub.BookClubTestBase;

import static testdata.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutTests extends BookClubTestBase {

    String username = "qaguru";
    String password = "qaguru123";
    String emptyToken = "";

    @Tag("dz_19")
    @Test
    public void successfulLogoutTest() {
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        SuccessfulLoginResponseModel successfulLoginResponseModel = apiClient.login.successfulLogin(loginRequestModel);
        String refreshToken = successfulLoginResponseModel.getRefresh();

        LogoutRequestModel logoutRequestModel = new LogoutRequestModel();
        logoutRequestModel.setRefresh(refreshToken);

        apiClient.logout.successfulLogout(logoutRequestModel);
    }

    @Tag("dz_19")
    @Test
    public void noTokenLogoutTest() {
        LogoutRequestModel logoutRequestModel = new LogoutRequestModel();
        logoutRequestModel.setRefresh(emptyToken);

        NoRefreshLogoutResponseModel noRefreshLogoutResponseModel = apiClient.logout.noTokenLogout(logoutRequestModel);

        String expectedError = "This field may not be blank.";
        assertEquals(expectedError, noRefreshLogoutResponseModel.getRefresh().get(0));
    }

    @Tag("dz_19")
    @Test
    public void randomRefreshLogoutTest() {
        LogoutRequestModel logoutRequestModel = new LogoutRequestModel();
        logoutRequestModel.setRefresh(randomRefresh);

        IncorrectRefreshLogoutResponseModel incorrectRefreshLogoutResponseModel =
                apiClient.logout.randomRefreshLogout(logoutRequestModel);

        String expectedDetail = "Token is invalid";
        String expectedCode = "token_not_valid";

        assertEquals(expectedDetail, incorrectRefreshLogoutResponseModel.getDetail());
        assertEquals(expectedCode, incorrectRefreshLogoutResponseModel.getCode());
    }
}
