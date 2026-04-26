package models.logout;

import lombok.Data;

import java.util.List;

@Data
public class IncorrectRefreshLogoutResponseModel {
    private String detail;
    private String code;
}
