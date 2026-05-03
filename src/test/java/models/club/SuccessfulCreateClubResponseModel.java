package models.club;

import lombok.Data;

import java.util.List;

@Data
public class SuccessfulCreateClubResponseModel {
    Integer id;
    String bookTitle;
    String bookAuthors;
    Integer publicationYear;
    String description;
    String telegramChatLink;
    Integer owner;
    List<Integer> members;
    List<Object> reviews;
    String created;
    String modified;
}