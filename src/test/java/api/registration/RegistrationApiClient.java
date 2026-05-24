package api.registration;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.registration.NoUsernameRegistrationRequestModel;
import models.registration.NoUsernameRegistrationResponseModel;
import models.registration.RegistrationRequestModel;
import models.registration.SuccessfulRegistrationResponseModel;

import static io.restassured.RestAssured.given;
import static specs.login.DefaultSpec.defaultRequestSpec;
import static specs.login.DefaultSpec.defaultResponseSpec;

public class RegistrationApiClient {

    @Step("Успешная регистрация")
    public SuccessfulRegistrationResponseModel successfulRegistration(RegistrationRequestModel data) {
        return given()
                .spec(defaultRequestSpec)
                .body(data)
                .when()
                .post("/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(201)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
    }

    @Step("Регистрация: пользователь уже существует")
    public Response notUniqUserRegistration(RegistrationRequestModel registrationRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .body(registrationRequestModel)
                .when()
                .post("/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(400)
                .extract()
                .response();
    }

    @Step("Регистрация: имя пользователя отсутствует")
    public NoUsernameRegistrationResponseModel noUsernameRegistration(NoUsernameRegistrationRequestModel data) {
        return given()
                .spec(defaultRequestSpec)
                .body(data)
                .when()
                .post("/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(400)
                .extract()
                .as(NoUsernameRegistrationResponseModel.class);
    }
}