package tests.bookclub;

import api.club.ClubApiClient;
import api.login.LoginApiClient;
import api.logout.LogoutApiClient;
import api.registration.RegistrationApiClient;
import api.update.UpdateUserApiClient;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import helpers.Attach;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

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
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        Configuration.browserSize = System.getProperty("browserSize");
        Configuration.baseUrl = System.getProperty("baseUrl");;
        Configuration.remote = System.getProperty("selenoidRemoteURL");
        Configuration.headless = Boolean.parseBoolean(System.getProperty("isHeadless"));
        Configuration.browser = System.getProperty("browser");
        Configuration.browserVersion = System.getProperty("browserVersion");
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 10000;

//        Configuration.baseUrl = "https://book-club.qa.guru";
//        Configuration.browserSize = "1920x1080";
//        Configuration.timeout = 10000;
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    @AfterEach
    void addAttachment() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}