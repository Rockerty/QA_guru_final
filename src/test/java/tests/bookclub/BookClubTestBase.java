package tests.bookclub;

import api.club.ClubApiClient;
import api.login.LoginApiClient;
import api.logout.LogoutApiClient;
import api.registration.RegistrationApiClient;
import api.update.UpdateUserApiClient;
import com.codeborne.selenide.Configuration;
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
        // Настройка RestAssured для API
        RestAssured.baseURI = "https://book-club.qa.guru";
        RestAssured.basePath = "/api/v1";

        // Настройка Selenide для UI
        Configuration.baseUrl = "https://book-club.qa.guru";
        Configuration.browserSize = "1920x1080";
    }
}