package com.lovankydev.spring_project_crud_with_sqlserver.mapper;

import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserUpdateRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.UserResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request );
}
