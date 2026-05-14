package api.club;

import models.club.*;

import static io.restassured.RestAssured.given;
import static specs.club.ClubSpec.*;

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

    public void successfulJoinClub (String accessToken, Integer clubId) {
        given()
                .spec(clubRequestSpec)
                .auth().oauth2(accessToken)
                .when()
                .post("/clubs/" + clubId + "/members/me/")
                .then()
                .spec(joinClubResponseSpecification);
    }

    public SuccessfulCreateReviewResponseModel successfulCreateReview(String accessToken, CreateClubReviewRequestModel createClubReviewRequestModel) {
        return given()
                .spec(successfulCreateReviewRequestSpec)
                .auth().oauth2(accessToken)
                .body(createClubReviewRequestModel)
                .when()
                .post("/clubs/reviews/")
                .then()
                .spec(successfulCreateReviewResponseSpec)
                .extract()
                .as(SuccessfulCreateReviewResponseModel.class);
    }

    public GetReviewResponseModel successfulGetReview(Integer reviewId) {
        return given()
                .spec(defaultReviewRequestSpec)
                .when()
                .get("/clubs/reviews/" + reviewId + "/")
                .then()
                .spec(successfulGetReviewResponseSpec)
                .extract()
                .as(GetReviewResponseModel.class);
    }

    public void SuccessfulUpdateClubReviewRequestModel (String accessToken, Integer reviewId) {
        given()
                .spec(clubRequestSpec)
                .auth().oauth2(accessToken)
                .when()
                .put("/clubs/reviews" + reviewId + "/")
                .then()
                .spec(joinClubResponseSpecification);
    }

    public SuccessfulUpdateReviewResponseModel successfulUpdateReviewResponseModel(String accessToken, Integer reviewId, UpdateReviewRequestModel updateReviewRequestModel) {
        return given()
                .spec(defaultReviewRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateReviewRequestModel)
                .when()
                .put("/clubs/reviews/" + reviewId + "/")
                .then()
                .spec(successfulGetReviewResponseSpec)
                .extract()
                .as(SuccessfulUpdateReviewResponseModel.class);
    }

    public void SuccessfulDeleteReviewClub(String accessToken, Integer reviewId) {
        given()
            .spec(defaultReviewRequestSpec)
            .auth().oauth2(accessToken)
            .when()
            .delete("/clubs/reviews/" + reviewId + "/")
            .then()
            .spec(successfulDeleteReviewResponseSpec);
    }

    public InvalidDeleteReviewResponseModel invalidDeleteReviewClub(String accessToken, Integer reviewId) {
        return given()
                .spec(defaultReviewRequestSpec)
                .auth().oauth2(accessToken)
                .when()
                .delete("/clubs/reviews/" + reviewId + "/")
                .then()
                .spec(invalidDeleteReviewResponseSpec)
                .extract()
                .as(InvalidDeleteReviewResponseModel.class);
    }
}