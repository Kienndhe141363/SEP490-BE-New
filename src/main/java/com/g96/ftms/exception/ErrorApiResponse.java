package com.g96.ftms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorApiResponse {
    private String errorCode;
    private String message;
}
