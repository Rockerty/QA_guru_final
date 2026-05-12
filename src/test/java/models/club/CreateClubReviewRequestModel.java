package models.club;

import lombok.Data;

@Data
public class CreateClubReviewRequestModel {
    Integer club;
    String review;
    Integer assessment;
    Integer readPages;
}