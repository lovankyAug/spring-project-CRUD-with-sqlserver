package com.lovankydev.spring_project_crud_with_sqlserver.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lovankydev.spring_project_crud_with_sqlserver.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String email;

    @Size(min = 4, max = 20, message = "USERNAME_INVALID")
    String userName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DobConstraint(min = 13, message = "AGE_INVALID")
    LocalDate dob;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String address;


}
