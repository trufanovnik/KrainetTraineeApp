package by.krainet.auth.service;

import by.krainet.UserEvent;
import by.krainet.auth.config.JwtUtil;
import by.krainet.auth.dto.AuthResponse;
import by.krainet.auth.dto.JwtRefreshRequest;
import by.krainet.auth.dto.LoginRequest;
import by.krainet.auth.dto.RegistrationRequest;
import by.krainet.auth.model.RoleType;
import by.krainet.auth.model.UserEntity;
import by.krainet.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserEventSender userEventSender;

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
        if (user.getRoleType().equals(RoleType.USER)) {
            UserEvent event = new UserEvent(
                    "CREATE",
                    registrationRequest.getUsername(),
                    registrationRequest.getPassword(),
                    registrationRequest.getEmail()
            );
            userEventSender.sendUserEvent(event);
        }
        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    @Transactional
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(401)
                    .body(null);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String access = jwtUtil.generateToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(access, refreshToken));
    }

    @Transactional
    public ResponseEntity<AuthResponse> refreshToken(JwtRefreshRequest jwtRefreshRequest) {
        String token = jwtRefreshRequest.getRefreshToken();
        if (!jwtUtil.isRefreshToken(token)) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        }

        String username = jwtUtil.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtUtil.validateToken(token, userDetails)) {
            return ResponseEntity.status(401).body(null);
        }
        String newAccess = jwtUtil.generateToken(userDetails);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(newAccess, newRefreshToken));
    }
}
