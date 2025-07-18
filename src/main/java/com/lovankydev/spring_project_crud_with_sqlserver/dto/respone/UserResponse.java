package com.lovankydev.spring_project_crud_with_sqlserver.dto.respone;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserResponse{

    String userName;
    String password;
    String email;
    LocalDate dob;
    String address;


}
