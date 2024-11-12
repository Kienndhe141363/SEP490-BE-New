package com.g96.ftms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorApiResponse> handleAppException(AppException ex) {
        // Lấy thông tin từ ErrorCode và tạo ErrorApiResponse
        ErrorApiResponse errorResponse = new ErrorApiResponse(
                ex.getErrorCode().getCode(),
                ex.getErrorCode().getMessage()
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorApiResponse> handleAuthenticationException(AuthenticationException ex) {
        ErrorApiResponse errorResponse = new ErrorApiResponse("ERR_AUTH", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApiResponse> handleGenericException(Exception ex) {
        ErrorApiResponse errorResponse = new ErrorApiResponse("ERR_UNKNOWN", "An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
