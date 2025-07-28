package com.lovankydev.spring_project_crud_with_sqlserver.controller;


import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.ApiResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.PermissionRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.PermissionResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> creationPermissionController(@RequestBody PermissionRequest request) {
        ApiResponse<PermissionResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(permissionService.createPermissionService(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermissionController() {
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<>();

        List<PermissionResponse> permissionResponseList = permissionService.getAllPermissionService();

        apiResponse.setResult(permissionResponseList);

        return apiResponse;
    }

    @DeleteMapping("/{permissionName}")
    ApiResponse<String> deletePermissionController(@PathVariable("permissionName") String permissionName) {
        permissionService.deletePermissionService(permissionName);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult("Permission has removed.");
        return apiResponse;
    }

}
