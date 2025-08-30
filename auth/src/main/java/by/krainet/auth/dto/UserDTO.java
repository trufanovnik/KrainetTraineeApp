package by.krainet.auth.dto;

import by.krainet.auth.model.RoleType;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private RoleType roleType;

    public UserDTO(String username, String email, RoleType roleType) {
        this.username = username;
        this.email = email;
        this.roleType = roleType;
    }
}
