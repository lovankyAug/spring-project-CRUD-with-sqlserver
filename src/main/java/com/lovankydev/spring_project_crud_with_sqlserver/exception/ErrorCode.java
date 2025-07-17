package com.lovankydev.spring_project_crud_with_sqlserver.exception;

public enum ErrorCode {
    USER_EXISTED(1001, "User has existed already. Please choose another user name."),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
    USER_NOT_FOUND(1002, "User is not found. Please check your ID again"),
    PASSWORD_INVALID(1004, "Pass word must be at least 8 letters. Example: 12345abc#");



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
