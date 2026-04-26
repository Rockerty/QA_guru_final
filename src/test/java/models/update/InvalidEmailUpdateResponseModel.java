package models.update;

import lombok.Data;

import java.util.List;

@Data
public class InvalidEmailUpdateResponseModel {
    private List<String> email;
}
