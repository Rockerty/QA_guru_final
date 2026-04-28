package tests.bookclub;

import com.github.javafaker.Faker;
import models.login.LoginRequestModel;
import models.registration.RegistrationRequestModel;
import models.registration.SuccessfulRegistrationResponseModel;
import models.update.InvalidEmailUpdateResponseModel;
import models.update.SuccessfulUpdateResponseModel;
import models.update.UpdateRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testbases.BookClubTestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.login.DefaultSpec.defaultRequestSpec;
import static specs.login.DefaultSpec.defaultResponseSpec;
import static specs.login.LoginSpec.*;

public class UpdateUserTests extends BookClubTestBase {
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
    String invalidEmail;
    String emptyEmail;

    @BeforeEach
    public void allTestsSetUp() {
        Faker faker = new Faker();
        username = faker.name().firstName() + faker.number().randomNumber();
        password = faker.name().firstName();
        firstName = faker.name().firstName();
        lastName = faker.name().firstName();
        email = faker.internet().emailAddress();
    }

    @Test
    public void successfulUpdateUserTest(){
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setUsername(username);
        registrationRequestModel.setPassword(password);

        step("Регистрация нового пользователя", () -> {
        SuccessfulRegistrationResponseModel successfulRegistrationResponse = given()
                .spec(defaultRequestSpec)
                .body(registrationRequestModel)
                .when()
                .post("/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(201)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
        });

        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        String accessToken = step("Получение токена созданного пользователя", () -> {
            return given()
                    .spec(loginRequestSpec)
                    .body(loginRequestModel)
                    .when()
                    .post("/auth/token/")
                    .then()
                    .spec(successfulLoginResponseSpec)
                    .extract().path("access");
        });

        UpdateRequestModel updateRequestModel = new UpdateRequestModel();
        updateRequestModel.setUsername(username);
        updateRequestModel.setFirstName(firstName);
        updateRequestModel.setLastName(lastName);
        updateRequestModel.setEmail(email);

        step("Редактирование пользователя", () -> {
        SuccessfulUpdateResponseModel successfulUpdateResponseModel = given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateRequestModel)
                .when()
                .patch("/users/me/")
                .then()
                .spec(defaultResponseSpec)
                .extract()
                .as(SuccessfulUpdateResponseModel.class);

        assertEquals(username, successfulUpdateResponseModel.getUsername());
        assertEquals(firstName, successfulUpdateResponseModel.getFirstName());
        assertEquals(lastName, successfulUpdateResponseModel.getLastName());
        assertEquals(email, successfulUpdateResponseModel.getEmail());
        });
    }

    @Test
    public void invalidEmailUpdateUserTest() {
        invalidEmail = "isNotEmail";

        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setUsername(username);
        registrationRequestModel.setPassword(password);

        step("Регистрация нового пользователя", () -> {
        SuccessfulRegistrationResponseModel successfulRegistrationResponse = given()
                .spec(defaultRequestSpec)
                .body(registrationRequestModel)
                .when()
                .post("/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(201)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
        });

        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        String accessToken = step("Получение токена созданного пользователя", () -> {
            return given()
                    .spec(loginRequestSpec)
                    .body(loginRequestModel)
                    .when()
                    .post("/auth/token/")
                    .then()
                    .spec(successfulLoginResponseSpec)
                    .extract().path("access");
        });

        UpdateRequestModel updateRequestModel = new UpdateRequestModel();
        updateRequestModel.setUsername(username);
        updateRequestModel.setFirstName(firstName);
        updateRequestModel.setLastName(lastName);
        updateRequestModel.setEmail(invalidEmail);

        step("Редактирование пользователя с некорректным email", () -> {
        InvalidEmailUpdateResponseModel invalidEmailUpdateResponseModel = given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateRequestModel)
                .when()
                .patch("/users/me/")
                .then()
                .spec(defaultResponseSpec)
                .extract()
                .as(InvalidEmailUpdateResponseModel.class);

        String expectedError = "Enter a valid email address.";

        assertEquals(expectedError, invalidEmailUpdateResponseModel.getEmail().get(0));
        });
    }

    @Test
    public void nullEmailUpdateUserTest() {
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setUsername(username);
        registrationRequestModel.setPassword(password);

        step("Регистрация нового пользователя", () -> {
        SuccessfulRegistrationResponseModel successfulRegistrationResponse = given()
                .spec(defaultRequestSpec)
                .body(registrationRequestModel)
                .when()
                .post("/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(201)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
        });

        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        String accessToken = step("Получение токена созданного пользователя", () -> {
            return given()
                    .spec(loginRequestSpec)
                    .body(loginRequestModel)
                    .when()
                    .post("/auth/token/")
                    .then()
                    .spec(successfulLoginResponseSpec)
                    .extract().path("access");
        });

        UpdateRequestModel updateRequestModel = new UpdateRequestModel();
        updateRequestModel.setUsername(username);
        updateRequestModel.setFirstName(firstName);
        updateRequestModel.setLastName(lastName);
        updateRequestModel.setEmail(emptyEmail);

        step("Редактирование пользователя с некорректным email", () -> {
        InvalidEmailUpdateResponseModel invalidEmailUpdateResponseModel = given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateRequestModel)
                .when()
                .patch("/users/me/")
                .then()
                .spec(defaultResponseSpec)
                .extract()
                .as(InvalidEmailUpdateResponseModel.class);

        String expectedError = "This field may not be null.";

        assertEquals(expectedError, invalidEmailUpdateResponseModel.getEmail().get(0));
        });

    }
}
