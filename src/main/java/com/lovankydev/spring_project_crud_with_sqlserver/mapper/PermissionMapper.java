package com.lovankydev.spring_project_crud_with_sqlserver.mapper;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.PermissionRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.PermissionResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionResponse toPermissionResponse(Permission permission) ;
    Permission toPermission(PermissionRequest permissionRequest) ;
}
