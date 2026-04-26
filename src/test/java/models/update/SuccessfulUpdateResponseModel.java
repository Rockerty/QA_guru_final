package models.update;

import lombok.Data;

@Data
public class SuccessfulUpdateResponseModel {
    int id;
    String username;
    String firstName;
    String lastName;
    String email;
    String remoteAddr;
}
