package tests.bookclub;

import models.login.EmptyCredsLoginResponseModel;
import models.login.IncorrectLoginResponseModel;
import models.login.LoginRequestModel;
import models.login.SuccessfulLoginResponseModel;
import org.junit.jupiter.api.Test;
import testbases.BookClubTestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.login.LoginSpec.*;

public class LoginTests extends BookClubTestBase {

    String username = "qaguru";
    String password = "qaguru123";
    String incorrectPassword = "qaguru321";
    String emptyUsername = "";
    String emptyPassword = "";


    @Test
    public void successfulLoginTest(){
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(password);

        SuccessfulLoginResponseModel successfulLoginResponseModel = given()
                .spec(loginRequestSpec)
                .body(loginRequestModel)
                .when()
                .post("/api/v1/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().as(SuccessfulLoginResponseModel.class);

        String actualAccess = successfulLoginResponseModel.getAccess();
        String actualRefresh = successfulLoginResponseModel.getRefresh();

        assertThat(actualAccess).isNotEqualTo(actualRefresh);
    }

    @Test
    public void incorrectPasswordLoginTest(){
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(username);
        loginRequestModel.setPassword(incorrectPassword);

        IncorrectLoginResponseModel incorrectLoginResponseModel = given()
                .spec(loginRequestSpec)
                .body(loginRequestModel)
                .when()
                .post("/api/v1/auth/token/")
                .then()
                .spec(incorrectPasswordLoginResponseSpec)
                .extract().as(IncorrectLoginResponseModel.class);

        String incorrectCredsError = "Invalid username or password.";
        String actualCredsError = incorrectLoginResponseModel.getDetail();

        assertThat(incorrectCredsError).isEqualTo(actualCredsError);
    }

    @Test
    public void emptyCredsLoginTest(){
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername(emptyUsername);
        loginRequestModel.setPassword(emptyPassword);

        EmptyCredsLoginResponseModel emptyCredsLoginResponseModel = given()
                .spec(loginRequestSpec)
                .body(loginRequestModel)
                .when()
                .post("/api/v1/auth/token/")
                .then()
                .spec(emptyCredsLoginResponseSpec)
                .extract().as(EmptyCredsLoginResponseModel.class);

        String expectedUsernameError = "This field may not be blank.";
        String expectedPasswordError = "This field may not be blank.";

        assertEquals(expectedUsernameError, emptyCredsLoginResponseModel.getUsername().get(0));
        assertEquals(expectedPasswordError, emptyCredsLoginResponseModel.getPassword().get(0));
    }
}
