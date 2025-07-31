package com.lovankydev.spring_project_crud_with_sqlserver.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User has existed already. Please choose another user name.", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.BAD_REQUEST),
    AGE_INVALID(1007, "Your must be at least {min}", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "User is not found. Please check your ID again", HttpStatus.NOT_FOUND),
    PASSWORD_INVALID(1004, "Password must be at least {min} letters. Example: 12345abc#", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1005, "You are not authenticated. Please login again", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006, "You don't have permission to access this resource", HttpStatus.FORBIDDEN),
   ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    private final HttpStatusCode httpStatusCode;
    private final int code;
    private final String message;
}
