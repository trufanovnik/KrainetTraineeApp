package by.krainet.auth.config;

import by.krainet.auth.model.UserEntity;
import by.krainet.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {

    @Autowired
    private UserRepository userRepository;

    public boolean isOwnerOrAdmin(Long id, Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if ("ROLEADMIN".equals(role)) {
            return true;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            UserEntity user = userRepository.findByUsername(username).orElseThrow(
                    () -> new EntityNotFoundException("Пользователь не найден"));
            if (user != null) {
                return user.getId().equals(id);
            }
        }
        return false;
    }
}
