package com.g96.ftms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponeDTO {
    private String token;        // Token JWT
    private String refreshToken; // Refresh Token
    private String username;     // Tên người dùng
    private String role;         // Vai trò của người dùng
    private Long expire;         // Thời gian hết hạn của token
    private Set<String> roles;

}
