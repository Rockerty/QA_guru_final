package api.club;

import io.qameta.allure.Step;
import models.club.*;

import static io.restassured.RestAssured.given;
import static specs.club.ClubSpec.*;

public class ClubApiClient {

    @Step("Создание книжного клуба")
    public SuccessfulCreateClubResponseModel successfulCreateClub(String accessToken, CreateClubRequestModel createClubRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(createClubRequestModel)
                .when()
                .post("/clubs/")
                .then()
                .spec(successfulCreateClubResponseSpec)
                .extract()
                .as(SuccessfulCreateClubResponseModel.class);
    }

    @Step("Получение книжного клуба")
    public GetClubResponseModel successfulGetClub(Integer clubId) {
        return given()
                .spec(defaultRequestSpec)
                .when()
                .get("/clubs/" + clubId + "/")
                .then()
                .spec(successfulGetClubResponseSpec)
                .extract()
                .as(GetClubResponseModel.class);
    }

    @Step("Редактирование книжного клуба")
    public SuccessfulUpdateClubResponseModel successfulUpdateClub(String accessToken, Integer clubId, UpdateClubRequestModel updateClubRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateClubRequestModel)
                .when()
                .put("/clubs/" + clubId + "/")
                .then()
                .spec(successfulUpdateClubResponseSpec)
                .extract()
                .as(SuccessfulUpdateClubResponseModel.class);
    }

    @Step("Удаление книжного клуба")
    public void successfulDeleteClub(String accessToken, Integer clubId) {
        given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .when()
                .delete("/clubs/" + clubId + "/")
                .then()
                .spec(successfulDeleteClubResponseSpec);
    }

    @Step("Вступление в книжный клуб")
    public void successfulJoinClub(String accessToken, Integer clubId) {
        given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .when()
                .post("/clubs/" + clubId + "/members/me/")
                .then()
                .spec(joinClubResponseSpecification);
    }

    @Step("Создание отзыва о клубе")
    public SuccessfulCreateReviewResponseModel successfulCreateReview(String accessToken, CreateClubReviewRequestModel createClubReviewRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(createClubReviewRequestModel)
                .when()
                .post("/clubs/reviews/")
                .then()
                .spec(successfulCreateReviewResponseSpec)
                .extract()
                .as(SuccessfulCreateReviewResponseModel.class);
    }

    @Step("Чтение отзыва о клубе")
    public GetReviewResponseModel successfulGetReview(Integer reviewId) {
        return given()
                .spec(defaultRequestSpec)
                .when()
                .get("/clubs/reviews/" + reviewId + "/")
                .then()
                .spec(successfulGetReviewResponseSpec)
                .extract()
                .as(GetReviewResponseModel.class);
    }

    @Step("Редактирование отзыва о клубе")
    public SuccessfulUpdateReviewResponseModel successfulUpdateReview(String accessToken, Integer reviewId, UpdateReviewRequestModel updateReviewRequestModel) {
        return given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .body(updateReviewRequestModel)
                .when()
                .put("/clubs/reviews/" + reviewId + "/")
                .then()
                .spec(successfulGetReviewResponseSpec)
                .extract()
                .as(SuccessfulUpdateReviewResponseModel.class);
    }

    @Step("Удаление отзыва о клубе")
    public void SuccessfulDeleteReviewClub(String accessToken, Integer reviewId) {
        given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .when()
                .delete("/clubs/reviews/" + reviewId + "/")
                .then()
                .spec(successfulDeleteReviewResponseSpec);
    }

    @Step("Попытка удалить чужой отзыв о клубе")
    public InvalidDeleteReviewResponseModel invalidDeleteReviewClub(String accessToken, Integer reviewId) {
        return given()
                .spec(defaultRequestSpec)
                .auth().oauth2(accessToken)
                .when()
                .delete("/clubs/reviews/" + reviewId + "/")
                .then()
                .spec(invalidDeleteReviewResponseSpec)
                .extract()
                .as(InvalidDeleteReviewResponseModel.class);
    }
}
