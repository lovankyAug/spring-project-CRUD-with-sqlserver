package com.lovankydev.spring_project_crud_with_sqlserver.service;


import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserUpdationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.UserResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.AppException;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.ErrorCode;
import com.lovankydev.spring_project_crud_with_sqlserver.mapper.UserMapper;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public User userCreationService(UserCreationRequest request) {

        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public UserResponse getUserByIdService(String id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));

    }

    public UserResponse updateUserService(String id, UserUpdationRequest request) {

        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        System.out.println(user.getId());
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUserService(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }
    public UserResponse getUserByUserNameService(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        return userMapper.toUserResponse(user);
    }
}
