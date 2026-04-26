package models.registration;

import lombok.Data;

import java.util.List;

@Data
public class NoUsernameRegistrationResponseModel {
    private List<String> username;
}
