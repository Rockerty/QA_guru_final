package tests.bookclub;

import api.club.ClubApiClient;
import api.login.LoginApiClient;
import api.logout.LogoutApiClient;
import api.registration.RegistrationApiClient;
import api.update.UpdateUserApiClient;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BookClubTestBase {

    protected LoginApiClient loginApiClient = new LoginApiClient();
    protected LogoutApiClient logoutApiClient = new LogoutApiClient();
    protected RegistrationApiClient registrationApiClient = new RegistrationApiClient();
    protected UpdateUserApiClient updateUserApiClient = new UpdateUserApiClient();
    protected ClubApiClient clubApiClient = new ClubApiClient();

    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = "https://book-club.qa.guru";
        RestAssured.basePath = "/api/v1";
    }
}