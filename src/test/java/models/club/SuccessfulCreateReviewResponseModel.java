package models.club;

import lombok.Data;

@Data
public class SuccessfulCreateReviewResponseModel {
    private Integer id;
    private Integer club;
    private Integer userId;
    private String username;
    private String review;
    private Integer assessment;
    private Integer readPages;
    private String created;
    private String modified;
}