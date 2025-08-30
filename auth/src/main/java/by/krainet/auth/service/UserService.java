package by.krainet.auth.service;

import by.krainet.auth.dto.UserDTO;
import by.krainet.auth.mapper.UserDtoMapper;
import by.krainet.auth.model.UserEntity;
import by.krainet.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper mapper;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return mapper.toDto(user);
    }
}
