package models.logout;

import lombok.Data;
import java.util.List;

@Data
public class NoRefreshLogoutResponseModel {
    private List<String> refresh;
}
