package com.lovankydev.spring_project_crud_with_sqlserver.controller;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.ApiResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserUpdationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.UserResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService ;

//Create new user
    @PostMapping("/users")
    ApiResponse<User> userCreationController(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<User>();
        apiResponse.setResult(userService.userCreationService(request));
        return apiResponse;
    }

//    Get user all user
    @GetMapping("/users")
    List<User> getUserListController(){
        return userService.getUserList();
    }

// update user information
    @PutMapping("/users/{userID}")
    UserResponse updateUserController(@PathVariable("userID") String id, @RequestBody UserUpdationRequest request ){
        return userService.updateUserService(id, request);
    }

//    get user by ID
    @GetMapping("/users/{userID}")
    UserResponse getUserByIdController(@PathVariable("userID") String userID){
        return userService.getUserByIdService(userID) ;
    }

//delete user
    @DeleteMapping("/users/{userID}")
    ApiResponse<String> deleteUserController(@PathVariable("userID")String userID){
        userService.deleteUserService(userID);
        return new ApiResponse<>(1000, null, "Delete user successfully") ;
    }
}
