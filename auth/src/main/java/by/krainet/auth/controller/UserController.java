package by.krainet.auth.controller;

import by.krainet.auth.dto.UserDTO;
import by.krainet.auth.dto.UserUpdateDTO;
import by.krainet.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable Long id, @RequestBody UserUpdateDTO updateDTO) {
        return ResponseEntity.ok(userService.updateUser(id, updateDTO));
    }
}
