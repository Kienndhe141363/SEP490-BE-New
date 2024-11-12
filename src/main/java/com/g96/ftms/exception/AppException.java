package com.g96.ftms.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class AppException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;

    public AppException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }


}
