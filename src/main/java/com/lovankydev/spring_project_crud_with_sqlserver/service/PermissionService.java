package com.lovankydev.spring_project_crud_with_sqlserver.service;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.PermissionRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.PermissionResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.Permission;
import com.lovankydev.spring_project_crud_with_sqlserver.mapper.PermissionMapper;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermissionService(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermissionService() {
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void deletePermissionService(String permissionName){
        permissionRepository.deleteById(permissionName);
    }
}
