package com.g96.ftms.service.email;

import com.g96.ftms.dto.request.ResetPasswordRequest;
import com.g96.ftms.dto.response.ApiResponse;
import com.g96.ftms.entity.User;
import com.g96.ftms.exception.AppException;
import com.g96.ftms.exception.ErrorCode;
import com.g96.ftms.repository.UserRepository;
import com.g96.ftms.util.EmailTemplate;
import com.g96.ftms.util.VerificationCodeUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationCodeUtil verificationCodeUtil;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationCodeUtil verificationCodeUtil) {
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationCodeUtil = verificationCodeUtil;
    }

    @Override
    public ApiResponse sendVerificationEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.EMPTY_INPUT);
        }
        if (!isValidEmail(email)) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_EMAIL);
        }

        String verificationCode = verificationCodeUtil.generateVerificationCode();
        verificationCodeUtil.storeVerificationCode(email, verificationCode);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Your Verification Code");

            String htmlContent = EmailTemplate.generateVerificationEmailTemplate(verificationCode, email);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            return new ApiResponse(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        } catch (MessagingException e) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST);
        }
    }

    @Override
    public ApiResponse resetPassword(ResetPasswordRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.EMPTY_INPUT);
        }
        if (!isValidEmail(request.getEmail())) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_EMAIL);
        }
        if (request.getNewPassword() == null || request.getConfirmPassword() == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.EMPTY_INPUT);
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.CONFIRM_PASSWORD_MISMATCH);
        }
        if (!isPasswordValid(request.getNewPassword())) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.PASSWORD_TOO_WEAK);
        }

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            throw new AppException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND);
        }

        String storedVerificationCode = verificationCodeUtil.getVerificationCode(request.getEmail());
        if (storedVerificationCode == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_VERIFICATION_CODE);
        }
        if (!storedVerificationCode.equals(request.getVerificationCode())) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_VERIFICATION_CODE);
        }

        optionalUser.ifPresent(user -> {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        });
        verificationCodeUtil.removeVerificationCode(request.getEmail());

        return new ApiResponse(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
    }

    @Override
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    @Override
    public boolean isPasswordValid(String password) {
        return password != null && password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[^a-zA-Z0-9].*");
    }
}
