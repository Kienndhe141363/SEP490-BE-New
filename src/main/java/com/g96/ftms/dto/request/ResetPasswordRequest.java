package com.g96.ftms.dto.request;

import lombok.Data;


@Data
public class ResetPasswordRequest {
    private String email;
    private String verificationCode;
    private String newPassword;
    private String confirmPassword;
}
