package com.g96.ftms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VerificationCodeInfo {
    private String code;
    private LocalDateTime expiryTime;

    public VerificationCodeInfo(String code, LocalDateTime expiryTime) {
        this.code = code;
        this.expiryTime = expiryTime;
    }
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }
}
