package com.g96.ftms.service.auth;

import com.g96.ftms.dto.JwtResponeDTO;
import com.g96.ftms.dto.LoginDTO;

public interface AuthService {
    JwtResponeDTO authenticateUser(LoginDTO loginDTO);
}
