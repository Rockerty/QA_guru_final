package models.club;

import lombok.Data;

@Data
public class SuccessfulCreateReviewResponseModel {
    private Integer id;
    private Integer club;
    private User user;
    private String review;
    private Integer assessment;
    private Integer readPages;
    private String created;
    private String modified;

    @Data
    public static class User {
        private Integer id;
        private String username;
    }
}