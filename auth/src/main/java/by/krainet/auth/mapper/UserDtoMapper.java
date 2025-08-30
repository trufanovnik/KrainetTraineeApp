package by.krainet.auth.mapper;

import by.krainet.auth.dto.UserDTO;
import by.krainet.auth.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    public UserDTO toDto(UserEntity user) {
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRoleType()
        );
    }
}
