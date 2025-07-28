package com.lovankydev.spring_project_crud_with_sqlserver.dto.request;


import com.lovankydev.spring_project_crud_with_sqlserver.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RoleRequest {

    String name;
    String description ;
    Set<String> permissions;

}
