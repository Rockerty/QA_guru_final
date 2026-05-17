package api.logout;

import models.logout.IncorrectRefreshLogoutResponseModel;
import models.logout.LogoutRequestModel;
import models.logout.NoRefreshLogoutResponseModel;

import static io.restassured.RestAssured.given;
import static specs.login.DefaultSpec.defaultRequestSpec;
import static specs.login.LogoutSpec.*;

public class LogoutApiClient {

    public void successfulLogout(LogoutRequestModel logoutRequestModel) {
        given()
                .spec(defaultRequestSpec)
                .body(logoutRequestModel)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(successfulLogoutResponseSpec);
    }

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