package com.lovankydev.spring_project_crud_with_sqlserver.controller;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.ApiRespone;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserUpdationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.UserResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.exception.ErrorCode;
import com.lovankydev.spring_project_crud_with_sqlserver.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserController {


    UserService userService ;

//Create new user
    @PostMapping("/users")
    ApiRespone<User> userCreationController(@RequestBody @Valid UserCreationRequest request){
        ApiRespone<User> apiRespone  = new ApiRespone<User>();
        apiRespone.setResult(userService.userCreationService(request));
        return apiRespone ;
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
    ApiRespone<String> deleteUserController(@PathVariable("userID")String userID){
        userService.deleteUserService(userID);
        return new ApiRespone<>(1000, null, "Delete user successfully") ;
    }
}
