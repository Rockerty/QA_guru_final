package models.club;

import lombok.Data;

@Data
public class UpdateReviewRequestModel {
    private Integer club;
    private String review;
    private Integer assessment;
    private Integer readPages;
}