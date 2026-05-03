package specs.club;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public class ClubSpec {

    public static ResponseSpecification successfulCreateClubResponseSpec = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(201)
            .expectContentType(JSON)
            .build();

    public static ResponseSpecification successfulGetClubResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(JSON)
            .build();

    public static ResponseSpecification successfulUpdateClubResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(JSON)
            .build();

    public static ResponseSpecification successfulDeleteClubResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();
}