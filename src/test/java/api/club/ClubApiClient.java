package api.club;

import models.club.CreateClubRequestModel;
import models.club.GetClubResponseModel;
import models.club.SuccessfulCreateClubResponseModel;
import models.club.SuccessfulUpdateClubResponseModel;
import models.club.UpdateClubRequestModel;

import static io.restassured.RestAssured.given;
import static specs.club.ClubSpec.clubRequestSpec;
import static specs.club.ClubSpec.successfulCreateClubResponseSpec;
import static specs.club.ClubSpec.successfulDeleteClubResponseSpec;
import static specs.club.ClubSpec.successfulGetClubResponseSpec;
import static specs.club.ClubSpec.successfulUpdateClubResponseSpec;

public class ClubApiClient {

    public SuccessfulCreateClubResponseModel successfulCreateClub(String accessToken, CreateClubRequestModel createClubRequestModel) {
        return given()
                .spec(clubRequestSpec)
                .auth().oauth2(accessToken)
                .body(createClubRequestModel)
                .when()
                .post("/clubs/")
                .then()
                .spec(successfulCreateClubResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
    }

    public GetClubResponseModel successfulGetClub(Integer clubId) {
        return given()
                .spec(clubRequestSpec)
                .when()
                .get("/clubs/" + clubId + "/")
                .then()
                .spec(successfulGetClubResponseSpec)
                .extract()
                .as(GetClubResponseModel.class);
    }

    public SuccessfulUpdateClubResponseModel successfulUpdateClub(String accessToken, Integer clubId, UpdateClubRequestModel updateClubRequestModel) {
        return given()
                .spec(clubRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateClubRequestModel)
                .when()
                .put("/clubs/" + clubId + "/")
                .then()
                .spec(successfulUpdateClubResponseSpec)
                .extract()
                .as(SuccessfulUpdateClubResponseModel.class);
    }

    public void successfulDeleteClub(String accessToken, Integer clubId) {
        given()
                .spec(clubRequestSpec)
                .auth().oauth2(accessToken)
                .when()
                .delete("/clubs/" + clubId + "/")
                .then()
                .spec(successfulDeleteClubResponseSpec);
    }
}