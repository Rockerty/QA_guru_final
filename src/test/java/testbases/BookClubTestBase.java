package testbases;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BookClubTestBase {

    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = "https://book-club.qa.guru";
        RestAssured.basePath = "/api/v1";
    }
}
