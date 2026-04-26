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
//      Регистрация нового пользователя
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setUsername(username);
        registrationRequestModel.setPassword(password);

        SuccessfulRegistrationResponseModel successfulRegistrationResponse = given()
                .spec(defaultRequestSpec)
                .body(registrationRequestModel)
                .when()
                .post("/api/v1/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(201)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);

//      Получение токена созданного пользователя (необходимо для update)
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        String accessToken = given()
                .spec(loginRequestSpec)
                .body(loginRequestModel)
                .when()
                .post("/api/v1/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("access");

//      Редактирование пользователя
        UpdateRequestModel updateRequestModel = new UpdateRequestModel();
        updateRequestModel.setUsername(username);
        updateRequestModel.setFirstName(firstName);
        updateRequestModel.setLastName(lastName);
        updateRequestModel.setEmail(email);

        SuccessfulUpdateResponseModel successfulUpdateResponseModel = given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateRequestModel)
                .when()
                .patch("/api/v1/users/me/")
                .then()
                .spec(defaultResponseSpec)
                .extract()
                .as(SuccessfulUpdateResponseModel.class);

        assertEquals(username, successfulUpdateResponseModel.getUsername());
        assertEquals(firstName, successfulUpdateResponseModel.getFirstName());
        assertEquals(lastName, successfulUpdateResponseModel.getLastName());
        assertEquals(email, successfulUpdateResponseModel.getEmail());
    }

    @Test
    public void invalidEmailUpdateUserTest() {
        invalidEmail = "isNotEmail";

//      Регистрация нового пользователя
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setUsername(username);
        registrationRequestModel.setPassword(password);

        SuccessfulRegistrationResponseModel successfulRegistrationResponse = given()
                .spec(defaultRequestSpec)
                .body(registrationRequestModel)
                .when()
                .post("/api/v1/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(201)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);

//      Получение токена созданного пользователя (необходимо для update)
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        String accessToken = given()
                .spec(loginRequestSpec)
                .body(loginRequestModel)
                .when()
                .post("/api/v1/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("access");
//      Редактирование пользователя с некорректным email
        UpdateRequestModel updateRequestModel = new UpdateRequestModel();
        updateRequestModel.setUsername(username);
        updateRequestModel.setFirstName(firstName);
        updateRequestModel.setLastName(lastName);
        updateRequestModel.setEmail(invalidEmail);

        InvalidEmailUpdateResponseModel invalidEmailUpdateResponseModel = given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateRequestModel)
                .when()
                .patch("/api/v1/users/me/")
                .then()
                .spec(defaultResponseSpec)
                .extract()
                .as(InvalidEmailUpdateResponseModel.class);

        String expectedError = "Enter a valid email address.";

        assertEquals(expectedError, invalidEmailUpdateResponseModel.getEmail().get(0));
    }

    @Test
    public void nullEmailUpdateUserTest() {
//      Регистрация нового пользователя
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setUsername(username);
        registrationRequestModel.setPassword(password);

        SuccessfulRegistrationResponseModel successfulRegistrationResponse = given()
                .spec(defaultRequestSpec)
                .body(registrationRequestModel)
                .when()
                .post("/api/v1/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(201)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);

//      Получение токена созданного пользователя (необходимо для update)
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        String accessToken = given()
                .spec(loginRequestSpec)
                .body(loginRequestModel)
                .when()
                .post("/api/v1/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("access");
//      Редактирование пользователя с некорректным email
        UpdateRequestModel updateRequestModel = new UpdateRequestModel();
        updateRequestModel.setUsername(username);
        updateRequestModel.setFirstName(firstName);
        updateRequestModel.setLastName(lastName);
        updateRequestModel.setEmail(emptyEmail);

        InvalidEmailUpdateResponseModel invalidEmailUpdateResponseModel = given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateRequestModel)
                .when()
                .patch("/api/v1/users/me/")
                .then()
                .spec(defaultResponseSpec)
                .extract()
                .as(InvalidEmailUpdateResponseModel.class);

        String expectedError = "This field may not be null.";

        assertEquals(expectedError, invalidEmailUpdateResponseModel.getEmail().get(0));

    }
}
