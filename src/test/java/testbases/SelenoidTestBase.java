package testbases;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class SelenoidTestBase {

    @BeforeAll
    public static void SetUp(){
        RestAssured.baseURI = "https://selenoid.autotests.cloud";
    }
}
