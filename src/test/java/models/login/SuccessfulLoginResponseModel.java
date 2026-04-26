package models.login;

import lombok.Data;

@Data
public class SuccessfulLoginResponseModel {
    String refresh;
    String access;
}
