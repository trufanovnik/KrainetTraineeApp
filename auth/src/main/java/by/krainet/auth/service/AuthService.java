package by.krainet.auth.service;

import by.krainet.auth.config.JwtUtil;
import by.krainet.auth.dto.RegistrationRequest;
import by.krainet.auth.model.RoleType;
import by.krainet.auth.model.UserEntity;
import by.krainet.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public ResponseEntity<String> register(RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Пользователь с таким никнеймом уже существует");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setRoleType(RoleType.USER);
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }
}
