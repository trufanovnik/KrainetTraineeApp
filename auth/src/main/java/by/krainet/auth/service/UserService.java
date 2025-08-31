package by.krainet.auth.service;

import by.krainet.auth.dto.UserDTO;
import by.krainet.auth.dto.UserUpdateDTO;
import by.krainet.auth.exception.UserNotFoundException;
import by.krainet.auth.mapper.UserDtoMapper;
import by.krainet.auth.model.UserEntity;
import by.krainet.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        return mapper.toDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Пользователь с id: " + id + " не найден");
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDTO updateUser(Long id, UserUpdateDTO update) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        if (update.getUsername() != null) {
            user.setUsername(update.getUsername());
        }
        if (update.getEmail() != null) {
            user.setEmail(update.getEmail());
        }
        if (update.getPassword() != null && !update.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(update.getPassword()));
        }
        if (update.getRoleType() != null) {
            user.setRoleType(update.getRoleType());
        }
        UserEntity updatedUser = userRepository.save(user);
        return mapper.toDto(updatedUser);
    }
}
