package com.lovankydev.spring_project_crud_with_sqlserver.dto.respone;

import com.lovankydev.spring_project_crud_with_sqlserver.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {

    String name;
    String description;
    Set<Permission> permissions;
}
