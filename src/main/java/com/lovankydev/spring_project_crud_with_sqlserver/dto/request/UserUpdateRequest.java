package com.lovankydev.spring_project_crud_with_sqlserver.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Size(min = 3, max = 20, message = "Username requires from 3 to 20 letters for an username. ")
    String userName;

    @Size(min = 8, message = "Password requires at least 8 letters.")
    String password;
    LocalDate dob;
    String address;
    String email;

    List<String> roles;

}
