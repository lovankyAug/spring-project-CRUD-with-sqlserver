package com.lovankydev.spring_project_crud_with_sqlserver.controller;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.ApiResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.AuthenticationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.IntrospectRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.AuthenticationResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.IntrospectResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults (level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {


    AuthenticationService authenticationService ;
    @PostMapping("/auth/token")
    public ApiResponse<AuthenticationResponse> login( @RequestBody AuthenticationRequest request) {
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        if(request != null){
            apiResponse.setResult(
                    authenticationService.authenticate(request)
            );
        }
        return apiResponse ;
    }

    @PostMapping("/auth/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();
        try {
            apiResponse.setResult(authenticationService.introspectService(request) );
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return apiResponse;
    }
}
