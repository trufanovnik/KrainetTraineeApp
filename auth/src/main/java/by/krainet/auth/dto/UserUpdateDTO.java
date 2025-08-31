package by.krainet.auth.dto;

import by.krainet.auth.model.RoleType;
import lombok.Data;

@Data
public class UserUpdateDTO {

    private String username;
    private String email;
    private String password;
    private RoleType roleType;
}
