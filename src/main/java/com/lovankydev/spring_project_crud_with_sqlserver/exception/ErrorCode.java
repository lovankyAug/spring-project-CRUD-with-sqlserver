package com.lovankydev.spring_project_crud_with_sqlserver.exception;

public enum ErrorCode {
    USER_EXISTED(1001, "User has existed already.");

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private int code ;
    private String message ;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
