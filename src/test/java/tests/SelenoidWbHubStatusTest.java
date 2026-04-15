package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.is;

public class SelenoidWbHubStatusTest extends SelenoidTestBase {

    @Test
    public void valuePropertiesWdHubStatusTest() {
        given()
                .auth().basic("user1", "1234")
                .log().method()
                .log().uri()
                .log().body()
                .when().
                get("wd/hub/status")
                .then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"))
                .body("value.ready", is(true));
    }

    @Test
    public void unauthorizedWdHubStatusTest() {
        given()
                .log().method()
                .log().uri()
                .log().body()
                .when().
                get("wd/hub/status")
                .then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(401);
    }

    @Test
    public void wdHubStatusSchemaTest() {
        given()
                .auth().basic("user1", "1234")
                .log().method()
                .log().uri()
                .log().body()
                .when().
                get("wd/hub/status")
                .then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(200)
                .body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"))
                .body("value.ready", is(true))
                .body(matchesJsonSchemaInClasspath("schemas/wdHubStatusSchema"));
    }

    @Test
    public void userNameCapsWdHubStatusTest() {
        given()
                .auth().basic("USER1", "1234")
                .log().method()
                .log().uri()
                .log().body()
                .when().
                get("wd/hub/status")
                .then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(401);
    }

    @Test
    public void incorrectCredentialsCapsWdHubStatusTest() {
        given()
                .auth().basic("1234", "user1")
                .log().method()
                .log().uri()
                .log().body()
                .when().
                get("wd/hub/status")
                .then()
                .log().status()
                .log().headers()
                .log().body()
                .statusCode(401);
    }
}
