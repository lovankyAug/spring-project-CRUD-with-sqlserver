package com.lovankydev.spring_project_crud_with_sqlserver.dto.request;

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

    @Size(min = 3, max = 20, message = "Username requires from 3 to 20 letters for an username. ")
    String userName;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    LocalDate dob;
    String address;

}
