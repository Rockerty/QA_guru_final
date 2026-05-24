package api.update;

import io.qameta.allure.Step;
import models.update.InvalidEmailUpdateResponseModel;
import models.update.SuccessfulUpdateResponseModel;
import models.update.UpdateRequestModel;

import static io.restassured.RestAssured.given;
import static specs.login.DefaultSpec.defaultRequestSpec;
import static specs.login.DefaultSpec.defaultResponseSpec;

public class UpdateUserApiClient {

    @Step("Редактирование пользователя")
    public SuccessfulUpdateResponseModel successfulUpdateUser(String accessToken, UpdateRequestModel updateRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateRequestModel)
                .when()
                .patch("/users/me/")
                .then()
                .spec(defaultResponseSpec)
                .extract()
                .as(SuccessfulUpdateResponseModel.class);
    }

    @Step("Редактирование пользователя с некорректным email")
    public InvalidEmailUpdateResponseModel invalidEmailUpdateUser(String accessToken, UpdateRequestModel updateRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateRequestModel)
                .when()
                .patch("/users/me/")
                .then()
                .spec(defaultResponseSpec)
                .extract()
                .as(InvalidEmailUpdateResponseModel.class);
    }

    @Step("Редактирование пользователя с null email")
    public InvalidEmailUpdateResponseModel nullEmailUpdateUser(String accessToken, UpdateRequestModel updateRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateRequestModel)
                .when()
                .patch("/users/me/")
                .then()
                .spec(defaultResponseSpec)
                .extract()
                .as(InvalidEmailUpdateResponseModel.class);
    }
}