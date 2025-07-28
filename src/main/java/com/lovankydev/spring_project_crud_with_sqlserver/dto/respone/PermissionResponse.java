package com.lovankydev.spring_project_crud_with_sqlserver.dto.respone;

import jakarta.persistence.NamedAttributeNode;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PermissionResponse {

    String name;
    String description ;

}
