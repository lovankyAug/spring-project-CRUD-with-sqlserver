package com.lovankydev.spring_project_crud_with_sqlserver.service;


import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserUpdationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.AppException;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.ErrorCode;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User userCreationService(UserCreationRequest request) {
        User user = new User();
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUserByIdService(String id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

    }

    public User updateUserService(String id, UserUpdationRequest request) {
        User user = new User();

        if (!userRepository.existsById(id)) {
            throw  new AppException(ErrorCode.USER_NOT_FOUND);
        }
        user.setId(id);
        user.setUserName(request.getUserName());
        user.setAddress(request.getPassword());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setDob(request.getDob());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    public void deleteUserService(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }
}
