package com.g96.ftms.exception; // Thay đổi theo cấu trúc gói của bạn

import com.g96.ftms.entity.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter

public enum ErrorCode {
    USER_NOT_FOUND("ERR001", "Invalid input provided!"),
    INVALID_CREDENTIALS("ERR002", "Check again your input!"),
    ACCOUNT_ALREADY_EXISTS("ERR003", "Account has already exist"),
    PASSWORD_TOO_WEAK("ERR004", "Password is too week! Try again"),
    INVALID_INPUT("ERR005","Invalid input provided"),
    OLD_PASSWORD_INCORRECT("ERR006", "Wrong password"),
    CONFIRM_PASSWORD_MISMATCH("ERR007", "Confirm password does not match"),
    PASSWORD_CHANGE_ERROR("ERR008","Change password failed"),
    ACCESS_DENIED("ERR009", "You don't have permission to access"),
    OK("ERR010", "Success"),
    CREATED("ERR011", ""),
    BAD_REQUEST("ERR012", "Somethings wrong. Check again!"),
    UNAUTHORIZED("ERR013", "Need to be authorized"),
    FORBIDDEN("ERR014", ""),
    NOT_FOUND("ERR015", ""),
    INVALID_EMAIL("ERR016", ""),
    INVALID_VERIFICATION_CODE("ERR017", "Wrong verification code! Try again"),
    VERIFICATION_CODE_EXPIRED("ERR018", "This verification code has expired! Please resend."),
    EMPTY_INPUT("ERR019", "Input can't not be empty!"),
    SUBJECT_NOT_FOUND("ERR020", "Subject doesn't exist!"),
    CURRICULUM_NOT_FOUND("ERR021", "Curriculum not found"),
    DUPLICATE_CURRICULUM_NAME("ERR022", "Curriculum exist"),
    ROOM_NOT_FOUND("ERR023","Room does not exist"),
    GENERATION_NAME_SETTING_EXIST("ERR024","Generation exist"),
    SETTING_NOTFOUND("ERR025","Setting not found"),
    DUPLICATE_SUBJECT_CODE("ERR026","Code exist"),
    DUPLICATE_SETTING("ERR027","This setting exist"),
    ACCOUNT_INACTIVE("ERR028","This account has been deactivated!"),
    INTERNAL_SERVER_ERROR("ERR029","An unexpected error occurred"),
    GENERATION_NOT_FOUND("ERR030","Generation not found"),
    CLASS_NOT_FOUND("ERR031","Class not found"),
    DUPLICATE_CLASS_CODE("ERR032","Class exist"),

    UNKNOWN_ERROR("ERR999", "An Unknown error has occurred");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
