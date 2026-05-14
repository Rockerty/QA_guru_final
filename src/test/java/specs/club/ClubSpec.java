package specs.club;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static allure.CustomAllureListener.withCustomTemplate;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.IsNull.notNullValue;

public class ClubSpec {

    public static RequestSpecification clubRequestSpec = with()
            .filter(withCustomTemplate())
            .log().method()
            .log().uri()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification successfulCreateClubResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification successfulGetClubResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification successfulUpdateClubResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification successfulDeleteClubResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification joinClubResponseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(204)
            .build();

    public static RequestSpecification successfulCreateReviewRequestSpec = with()
            .filter(withCustomTemplate())
            .log().method()
            .log().uri()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification successfulCreateReviewResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(201)
            .build();
    public static ResponseSpecification successfulDeleteReviewResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification invalidDeleteReviewResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(403)
            .expectBody("detail", notNullValue())
            .build();

    public static ResponseSpecification successfulGetReviewResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(200)
            .expectBody("id", notNullValue())
            .expectBody("club", notNullValue())
            .expectBody("review", notNullValue())
            .expectBody("assessment", notNullValue())
            .expectBody("readPages", notNullValue())
            .build();

    public static RequestSpecification defaultReviewRequestSpec = with()
            .filter(withCustomTemplate())
            .log().method()
            .log().uri()
            .log().body()
            .contentType(JSON);
}