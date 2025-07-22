package com.lovankydev.spring_project_crud_with_sqlserver.service;


import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserUpdationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.UserResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.enums.Roles;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.AppException;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.ErrorCode;
import com.lovankydev.spring_project_crud_with_sqlserver.mapper.UserMapper;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder = passwordEncoder();

    // This method is used to create a new user.
    public User userCreationService(UserCreationRequest request) {

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);

        // Set roles for the user.
        HashSet<String> roles = new HashSet<>();
        roles.add(Roles.USER.name());
        user.setRoles(roles);

        // Encrypt the password before saving.
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }


    // This method retrieves a list of all users.
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    // This method retrieves a user by their ID.
    @PostAuthorize("returnObject.userName == authentication.name or hasRole('ADMIN')")
    public UserResponse getUserByIdService(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

    }

    // This method updates a user's information.
    public UserResponse updateUserService(String id, UserUpdationRequest request) {

        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        System.out.println(user.getId());
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    // This method deletes a user by their ID.
    public void deleteUserService(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    // This method retrieves a user by their username.
    public UserResponse getUserByUserNameService(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserResponse(user);
    }

    // This method retrieves the currently authenticated user's information.
    public UserResponse getMyInfoService() {
        String userName = SecurityContextHolder
                .getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserResponse(user);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
