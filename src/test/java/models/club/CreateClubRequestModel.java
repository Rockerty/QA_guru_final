package models.club;

import lombok.Data;

@Data
public class CreateClubRequestModel {
    String bookTitle;
    String bookAuthors;
    Integer publicationYear;
    String description;
    String telegramChatLink;
}