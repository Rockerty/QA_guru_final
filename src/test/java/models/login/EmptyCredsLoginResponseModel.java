package models.login;

import lombok.Data;

import java.util.List;

@Data
public class EmptyCredsLoginResponseModel {
    private List<String> username;
    private List<String> password;
}
