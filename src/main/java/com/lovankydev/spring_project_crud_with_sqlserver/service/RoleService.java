package com.lovankydev.spring_project_crud_with_sqlserver.service;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.RoleRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.RoleResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.Permission;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.Role;
import com.lovankydev.spring_project_crud_with_sqlserver.mapper.RoleMapper;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.PermissionRepository;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    RoleMapper roleMapper;


    public RoleResponse creationRoleService(RoleRequest request){
        Role role =  roleMapper.toRole(request);

        List<Permission> permissions =  permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

         roleRepository.save(role);


        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAllRoleService(){
       return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void deleteRoleService(String roleName){
        roleRepository.deleteById(roleName);
    }

}
