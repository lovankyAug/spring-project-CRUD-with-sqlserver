package com.lovankydev.spring_project_crud_with_sqlserver.configuration;

import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.enums.Roles;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;


@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)

public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            // This method is used to initialize the application with default data.
            // You can add any initialization logic here, such as creating default users or roles.
            // For example, you might want to create an admin user if it doesn't exist.

            if (!userRepository.existsByUserName("admin")) {

                HashSet<String> roles = new HashSet<>();
                roles.add(Roles.ADMIN.name());

//                User adminUser = User.builder()
//                        .userName("admin")
//                        .password(passwordEncoder.encode("admin123"))
//                        .roles(roles)
//                        .build();
//                userRepository.save(adminUser);
                log.warn("Admin user created with username: admin and password: admin123");
            }
        };
    }
}
