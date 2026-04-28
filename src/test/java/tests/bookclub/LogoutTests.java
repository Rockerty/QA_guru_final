package tests.bookclub;

import models.login.LoginRequestModel;
import models.logout.IncorrectRefreshLogoutResponseModel;
import models.logout.NoRefreshLogoutResponseModel;
import org.junit.jupiter.api.Test;
import testbases.BookClubTestBase;

import static io.qameta.allure.Allure.step;
import static specs.login.DefaultSpec.defaultRequestSpec;
import static specs.login.LogoutSpec.*;
import static testdata.TestData.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.login.LoginSpec.*;

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
            return given()
                    .spec(loginRequestSpec)
                    .body(loginRequestModel)
                    .when()
                    .post("/auth/token/")
                    .then()
                    .spec(successfulLoginResponseSpec)
                    .extract().path("refresh");
        });

        step("Успешный logout", () -> {
            String logoutBody = "{\"refresh\":\"" + refreshToken + "\"}";

            given()
                    .spec(defaultRequestSpec)
                    .body(logoutBody)
                    .when()
                    .post("/auth/logout/")
                    .then()
                    .spec(successfulLogoutResponseSpec);
        });
    }

    @Test
    public void noTokenLogoutTest(){
        String logoutBody = "{\"refresh\":\"" + emptyToken + "\"}";

        step("logout: токен отсутствует", () -> {
        NoRefreshLogoutResponseModel noRefreshLogoutResponseModel = given()
                .spec(defaultRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(emptyRefreshLogoutResponseSpec)
                .extract()
                .as(NoRefreshLogoutResponseModel.class);

        String expectedError = "This field may not be blank.";
        assertEquals(expectedError, noRefreshLogoutResponseModel.getRefresh().get(0));
        });
    }

    @Test
    public void randomRefreshLogoutTest(){
        String logoutBody = "{\"refresh\":\"" + randomRefresh + "\"}";

        step("logout: случайный токен", () -> {
        IncorrectRefreshLogoutResponseModel incorrectRefreshLogoutResponseModel = given()
                .spec(defaultRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(incorrectRefreshLogoutResponseSpec)
                .extract().as(IncorrectRefreshLogoutResponseModel.class);

        String expectedDetail = "Token is invalid";
        String expectedCode = "token_not_valid";

        assertEquals(expectedDetail, incorrectRefreshLogoutResponseModel.getDetail());
        assertEquals(expectedCode, incorrectRefreshLogoutResponseModel.getCode());
        });
    }
}
