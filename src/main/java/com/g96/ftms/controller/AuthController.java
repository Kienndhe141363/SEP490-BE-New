package com.g96.ftms.controller;

import com.g96.ftms.dto.ChangePasswordDTO;
import com.g96.ftms.dto.JwtResponeDTO;
import com.g96.ftms.dto.LoginDTO;
import com.g96.ftms.exception.AppException;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.dto.request.EmailRequest;
import com.g96.ftms.dto.request.ResetPasswordRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.service.email.EmailService;
import com.g96.ftms.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import com.g96.ftms.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponeDTO> login(@RequestBody LoginDTO loginDTO) {
        JwtResponeDTO jwtResponse = authService.authenticateUser(loginDTO);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/verify")
    public ApiResponse resendVerificationEmail(@RequestBody EmailRequest emailRequest) {
        return emailService.sendVerificationEmail(emailRequest.getEmail());
    }


    @PostMapping("/reset-password")
    public ApiResponse resetPassword(@RequestBody ResetPasswordRequest request) {
        return emailService.resetPassword(request);
    }


    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordDTO changePasswordDTO,
            Authentication authentication) {

        return userService.changeUserPassword(changePasswordDTO, authentication);
    }

}
