package com.g96.ftms.service.email;

import com.g96.ftms.dto.request.EmailRequest;
import com.g96.ftms.dto.request.ResetPasswordRequest;
import com.g96.ftms.dto.response.ApiResponse;

public interface EmailService {

    ApiResponse sendVerificationEmail(String email);

    ApiResponse resetPassword(ResetPasswordRequest request);

    boolean isValidEmail(String email);

    boolean isPasswordValid(String password);
}
