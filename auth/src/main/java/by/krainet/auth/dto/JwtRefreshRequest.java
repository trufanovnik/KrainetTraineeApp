package by.krainet.auth.dto;

import lombok.Data;

@Data
public class JwtRefreshRequest {
    private String refreshToken;
}
