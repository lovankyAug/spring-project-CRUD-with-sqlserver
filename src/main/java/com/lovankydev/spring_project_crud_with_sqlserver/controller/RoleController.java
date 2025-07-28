package com.lovankydev.spring_project_crud_with_sqlserver.controller;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.ApiResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.RoleRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.RoleResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> creationRoleController(@RequestBody RoleRequest request) {

        ApiResponse<RoleResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(roleService.creationRoleService(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRoleController() {
        ApiResponse<List<RoleResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(roleService.getAllRoleService());
        return apiResponse;
    }

    @DeleteMapping("/{roleName}")
    ApiResponse<String> deleteRoleController(@PathVariable("roleName") String roleName){
        roleService.deleteRoleService(roleName);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult("Role has removed");
        return apiResponse;
    }
}
