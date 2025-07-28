package com.lovankydev.spring_project_crud_with_sqlserver.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, unique = true)
    String userName;
    String password;
    String email;
    LocalDate dob;
    String address;
    @ManyToMany
    Set<Role> roles; // Assuming roles is a Set of Strings, adjust as necessary for your use case

}
