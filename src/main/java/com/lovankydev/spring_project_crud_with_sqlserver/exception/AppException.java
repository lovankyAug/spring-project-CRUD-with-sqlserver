package com.lovankydev.spring_project_crud_with_sqlserver.exception;

public class AppException extends RuntimeException {
    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode){
        super(errorCode.getMessage()) ;
        this.errorCode = errorCode ;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
