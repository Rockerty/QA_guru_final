package models.club;

import lombok.Data;

@Data
public class UpdateClubRequestModel {
    String bookTitle;
    String bookAuthors;
    Integer publicationYear;
    String description;
    String telegramChatLink;
}