package api.update;

import models.update.InvalidEmailUpdateResponseModel;
import models.update.SuccessfulUpdateResponseModel;
import models.update.UpdateRequestModel;

import static io.restassured.RestAssured.given;
import static specs.login.DefaultSpec.defaultRequestSpec;
import static specs.login.DefaultSpec.defaultResponseSpec;

public class UpdateUserApiClient {

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