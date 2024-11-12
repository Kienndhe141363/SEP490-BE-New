package com.g96.ftms.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class VerificationCodeUtil {

    private final Cache<String, String> verificationCodeCache;

    public VerificationCodeUtil() {
        this.verificationCodeCache = Caffeine.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .build();
    }

    public String generateVerificationCode() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    public void storeVerificationCode(String email, String verificationCode) {
        verificationCodeCache.put(email, verificationCode);
    }

    public String getVerificationCode(String email) {
        return verificationCodeCache.getIfPresent(email);
    }

    public void removeVerificationCode(String email) {
        verificationCodeCache.invalidate(email);
    }
}
