package api.logout;

import io.qameta.allure.Step;
import models.logout.IncorrectRefreshLogoutResponseModel;
import models.logout.LogoutRequestModel;
import models.logout.NoRefreshLogoutResponseModel;

import static io.restassured.RestAssured.given;
import static specs.login.DefaultSpec.defaultRequestSpec;
import static specs.login.LogoutSpec.*;

public class LogoutApiClient {

    @Step("Успешный logout")
    public void successfulLogout(LogoutRequestModel logoutRequestModel) {
        given()
                .spec(defaultRequestSpec)
                .body(logoutRequestModel)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(successfulLogoutResponseSpec);
    }

    @Step("Logout: токен отсутствует")
    public NoRefreshLogoutResponseModel noTokenLogout(LogoutRequestModel logoutRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .body(logoutRequestModel)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(emptyRefreshLogoutResponseSpec)
                .extract()
                .as(NoRefreshLogoutResponseModel.class);
    }

    @Step("Logout: случайный токен")
    public IncorrectRefreshLogoutResponseModel randomRefreshLogout(LogoutRequestModel logoutRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .body(logoutRequestModel)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(incorrectRefreshLogoutResponseSpec)
                .extract().as(IncorrectRefreshLogoutResponseModel.class);
    }
}
