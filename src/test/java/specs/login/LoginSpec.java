package specs.login;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginSpec {

    public static RequestSpecification loginRequestSpec = with()
            .log().method()
            .log().uri()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification successfulLoginResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.HEADERS)
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath("schemas/login/loginResponseSchema"))
            .expectBody("refresh", notNullValue())
            .expectBody("access", notNullValue())
            .build();

    public static ResponseSpecification incorrectPasswordLoginResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.HEADERS)
            .log(LogDetail.BODY)
            .expectStatusCode(401)
            .expectBody("detail", notNullValue())
            .build();

    public static ResponseSpecification emptyCredsLoginResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .log(LogDetail.HEADERS)
            .log(LogDetail.BODY)
            .expectStatusCode(400)
            .build();

}
