package com.lovankydev.spring_project_crud_with_sqlserver.dto.respone;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserResponse{

    String userName;
    String email;
    LocalDate dob;
    String address;
    Set<String> roles; // Assuming roles is a Set of Strings, adjust as necessary for your use case

}
