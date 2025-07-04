package com.lovankydev.spring_project_crud_with_sqlserver.service;


import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserUpdationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User userCreationService(UserCreationRequest request) {
        User user = new User();

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
        return userRepository.findById(id).orElse(null);
    }

    public User updateUserService(String id, UserUpdationRequest request) {
        User user = new User();

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
