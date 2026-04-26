package tests.bookclub;

import com.github.javafaker.Faker;
import models.registration.NoUsernameRegistrationRequestModel;
import models.registration.NoUsernameRegistrationResponseModel;
import models.registration.RegistrationRequestModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testbases.BookClubTestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.login.DefaultSpec.defaultRequestSpec;
import static specs.login.DefaultSpec.defaultResponseSpec;

public class RegistrationTests extends BookClubTestBase {
    String username;
    String password;

    @BeforeEach
    public void allTestsSetUp() {
        Faker faker = new Faker();
        username = faker.name().firstName();
        password = faker.name().firstName();
    }

    @Test
    public void successfulRegistrationTest(){
        RegistrationRequestModel data = new RegistrationRequestModel();
        data.setUsername(username);
        data.setPassword(password);

        SuccessfulRegistrationResponseModel successfulRegistrationResponse = given()
                .spec(defaultRequestSpec)
                .body(data)
                .when()
                .post("/api/v1/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(201)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);

        assertEquals(username, successfulRegistrationResponse.getUsername());
    }

    @Test
    public void notUniqUserRegistrationTest(){
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setUsername("Racquel");
        registrationRequestModel.setPassword("Siobhan");

        given()
                .spec(defaultRequestSpec)
                .body(registrationRequestModel)
                .when()
                .post("/api/v1/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(400)
                .body("username[0]", is("A user with that username already exists."));
    }

    @Test
    public void noUsernameRegistrationTest(){
        NoUsernameRegistrationRequestModel data = new NoUsernameRegistrationRequestModel();
        data.setPassword(password);

        NoUsernameRegistrationResponseModel noUsernameRegistrationResponseModel = given()
                .spec(defaultRequestSpec)
                .body(data)
                .when()
                .post("/api/v1/users/register/")
                .then()
                .spec(defaultResponseSpec)
                .statusCode(400)
                .extract()
                .as(NoUsernameRegistrationResponseModel.class);

        String expectedError = "This field is required.";

        assertEquals(expectedError, noUsernameRegistrationResponseModel.getUsername().get(0));
    }
}
