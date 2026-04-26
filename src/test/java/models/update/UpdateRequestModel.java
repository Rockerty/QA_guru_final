package models.update;

import lombok.Data;

@Data
public class UpdateRequestModel {
    String username;
    String firstName;
    String lastName;
    String email;
}
