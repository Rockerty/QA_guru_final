package api.logout;

import models.logout.IncorrectRefreshLogoutResponseModel;
import models.logout.NoRefreshLogoutResponseModel;

import static io.restassured.RestAssured.given;
import static specs.login.DefaultSpec.defaultRequestSpec;
import static specs.login.LogoutSpec.*;

public class LogoutApiClient {

    public void successfulLogout(String logoutBody) {
        given()
                .spec(defaultRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(successfulLogoutResponseSpec);
    }

    public NoRefreshLogoutResponseModel noTokenLogout(String logoutBody) {
        return given()
                .spec(defaultRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(emptyRefreshLogoutResponseSpec)
                .extract()
                .as(NoRefreshLogoutResponseModel.class);
    }

    public IncorrectRefreshLogoutResponseModel randomRefreshLogout(String logoutBody) {
        return given()
                .spec(defaultRequestSpec)
                .body(logoutBody)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(incorrectRefreshLogoutResponseSpec)
                .extract().as(IncorrectRefreshLogoutResponseModel.class);
    }
}