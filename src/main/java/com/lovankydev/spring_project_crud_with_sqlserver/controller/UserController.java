package com.lovankydev.spring_project_crud_with_sqlserver.controller;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserUpdationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService ;


//Create new user
    @PostMapping("/users")
    User userCreationController(@RequestBody UserCreationRequest request){
        return userService.userCreationService(request) ;
    }

//    Get user all user
    @GetMapping("/users")
    List<User> getUserListController(){
        return userService.getUserList();
    }

// update user information
    @PostMapping("/users/{userID}")
    User updateUserController(@PathVariable("userID") String id, @RequestBody UserUpdationRequest request ){
        return userService.updateUserService(id, request);
    }

//    get user by ID
    @GetMapping("/users/{userID}")
    User getUserByIdController(@PathVariable("userID") String userID){
        return userService.getUserByIdService(userID) ;
    }

//delete user
    @DeleteMapping("/users/{userID}")
    String deleteUserController(@PathVariable("userID")String userID){
        userService.deleteUserService(userID);
        return "User has deleted.";
    }
}
