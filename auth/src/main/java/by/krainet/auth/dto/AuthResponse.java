package by.krainet.auth.dto;

import lombok.Data;

@Data
public class AuthResponse {

    public AuthResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    private String token;
    private String refreshToken;
}
