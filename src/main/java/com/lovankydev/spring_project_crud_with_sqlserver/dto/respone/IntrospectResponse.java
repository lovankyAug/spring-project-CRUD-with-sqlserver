package com.lovankydev.spring_project_crud_with_sqlserver.dto.respone;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntrospectResponse {
    boolean valid;
}
