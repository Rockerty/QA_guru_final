package api.login;

import models.login.EmptyCredsLoginResponseModel;
import models.login.IncorrectLoginResponseModel;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;

import static io.restassured.RestAssured.given;
import static specs.login.LoginSpec.emptyCredsLoginResponseSpec;
import static specs.login.LoginSpec.incorrectPasswordLoginResponseSpec;
import static specs.login.LoginSpec.loginRequestSpec;
import static specs.login.LoginSpec.successfulLoginResponseSpec;

public class LoginApiClient {

    public SuccessfulLoginResponseModel successfulLogin(LoginRequestModel loginRequestModel) {
        return given()
                .spec(loginRequestSpec)
                .body(loginRequestModel)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().as(SuccessfulLoginResponseModel.class);
    }

    public IncorrectLoginResponseModel incorrectPasswordLogin(LoginRequestModel loginRequestModel) {
        return given()
                .spec(loginRequestSpec)
                .body(loginRequestModel)
                .when()
                .post("/auth/token/")
                .then()
                .spec(incorrectPasswordLoginResponseSpec)
                .extract().as(IncorrectLoginResponseModel.class);
    }

    public EmptyCredsLoginResponseModel emptyCredsLogin(LoginRequestModel loginRequestModel) {
        return given()
                .spec(loginRequestSpec)
                .body(loginRequestModel)
                .when()
                .post("/auth/token/")
                .then()
                .spec(emptyCredsLoginResponseSpec)
                .extract().as(EmptyCredsLoginResponseModel.class);
    }
}