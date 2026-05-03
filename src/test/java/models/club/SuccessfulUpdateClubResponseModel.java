package models.club;

import lombok.Data;

import java.util.List;

@Data
public class SuccessfulUpdateClubResponseModel {
    Integer id;
    String bookTitle;
    String bookAuthors;
    Integer publicationYear;
    String description;
    String telegramChatLink;
    Integer owner;
    List<Integer> members;
    List<Review> reviews;
    String created;
    String modified;

    @Data
    public static class Review {
        Integer id;
        Integer club;
        User user;
        String review;
        Integer assessment;
        Integer readPages;
        String created;
        String modified;
    }

    @Data
    public static class User {
        Integer id;
        String username;
    }
}