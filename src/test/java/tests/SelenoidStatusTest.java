package tests;

import org.junit.jupiter.api.Test;
import testbases.SelenoidTestBase;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;

public class SelenoidStatusTest extends SelenoidTestBase {

    @Test
    public void totalAmountTest() {
        given()
                .log().method()
                .log().uri()
                .log().body()
        .when().
            get("/status")
        .then()
                .log().status()
                .log().headers()
                .log().body()
                .body("total", is(5))
                .statusCode(200)
                .body("", hasKey("queued"))
                .body("browsers", hasKey("chrome"))
                .body("browsers.chrome", hasKey("127.0"));
    }
}
