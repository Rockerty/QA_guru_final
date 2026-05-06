package tests.bookclub;

import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import models.logout.IncorrectRefreshLogoutResponseModel;
import models.logout.NoRefreshLogoutResponseModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static testdata.TestData.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutTests extends BookClubTestBase {

    String username = "qaguru";
    String password = "qaguru123";
    String emptyToken = "";

    @Test
    public void successfulLogoutTest(){
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        String refreshToken = step("Авторизация и получение токена", () -> {
            SuccessfulLoginResponseModel successfulLoginResponseModel = loginApiClient.successfulLogin(loginRequestModel);
            return successfulLoginResponseModel.getRefresh();
        });

        step("Успешный logout", () -> {
            String logoutBody = "{\"refresh\":\"" + refreshToken + "\"}";

            logoutApiClient.successfulLogout(logoutBody);
        });
    }

    @Test
    public void noTokenLogoutTest(){
        String logoutBody = "{\"refresh\":\"" + emptyToken + "\"}";

        step("logout: токен отсутствует", () -> {
            NoRefreshLogoutResponseModel noRefreshLogoutResponseModel = logoutApiClient.noTokenLogout(logoutBody);

            String expectedError = "This field may not be blank.";
            assertEquals(expectedError, noRefreshLogoutResponseModel.getRefresh().get(0));
        });
    }

    @Test
    public void randomRefreshLogoutTest(){
        String logoutBody = "{\"refresh\":\"" + randomRefresh + "\"}";

        step("logout: случайный токен", () -> {
            IncorrectRefreshLogoutResponseModel incorrectRefreshLogoutResponseModel = logoutApiClient.randomRefreshLogout(logoutBody);

            String expectedDetail = "Token is invalid";
            String expectedCode = "token_not_valid";

            assertEquals(expectedDetail, incorrectRefreshLogoutResponseModel.getDetail());
            assertEquals(expectedCode, incorrectRefreshLogoutResponseModel.getCode());
        });
    }
}
