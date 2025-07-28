package com.lovankydev.spring_project_crud_with_sqlserver.mapper;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.RoleRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.RoleResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest) ;
    RoleResponse toRoleResponse(Role role);
}
