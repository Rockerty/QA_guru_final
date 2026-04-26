package models.registration;

import lombok.Data;

@Data
public class SuccessfulRegistrationResponseModel {
    Integer id;
    String username;
    String firstName;
    String lastName;
    String email;
    String remoteAddr;
}
