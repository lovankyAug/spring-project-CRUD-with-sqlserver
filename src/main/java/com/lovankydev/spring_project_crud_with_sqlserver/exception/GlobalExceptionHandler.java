package com.lovankydev.spring_project_crud_with_sqlserver.exception;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.ApiRespone;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//    Handling RuntimeException for request
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiRespone> handlingRuntimeException(RuntimeException exception){
        ApiRespone apiRespone = new ApiRespone() ;
        apiRespone.setCode(1001);
        apiRespone.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiRespone) ;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class )
    ResponseEntity<String> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        return ResponseEntity.badRequest().body(exception.getFieldError().getDefaultMessage()) ;
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRespone> handlingAppException(AppException exception){
        ApiRespone apiRespone = new ApiRespone() ;
        apiRespone.setCode(exception.getErrorCode().getCode());
        apiRespone.setMessage(exception.getErrorCode().getMessage());
        return ResponseEntity.badRequest().body(apiRespone) ;
    }
}
