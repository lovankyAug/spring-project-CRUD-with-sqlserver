package com.lovankydev.spring_project_crud_with_sqlserver.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.UserResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.entity.User;
import com.lovankydev.spring_project_crud_with_sqlserver.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceTest {

    @Autowired
    UserService userService;

    User user;
    UserResponse userResponse;
    UserCreationRequest request;
    LocalDate dob;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void initData(){

        dob = LocalDate.of(2005, 8, 15);

        request = UserCreationRequest.builder()
                .userName("lovankydev")
                .password("12345678")
                .email("lovankydev222@gmail.com")
                .address("Nghe An")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("9101c355c7ee")
                .userName("lovankydev")
                .address("Nghe An")
                .email("lovankydev222@gmail.com")
                .dob(dob)
                .build();

        user = User.builder()
                .id("9101c355c7ee")
                .userName("lovankydev")
                .address("Nghe An")
                .email("lovankydev222@gmail.com")
                .dob(dob)
                .build();
    }

    @Test
    public void createUser_validRequest_Success() throws Exception{

        //GIVEN
        Mockito.when(userRepository.existsByUserName(ArgumentMatchers.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);

        UserResponse response = userService.userCreationService(request);

        log.info(response.getId());

        //WHEN
        Assertions.assertThat(response.getId()).isEqualTo("9101c355c7ee");
        Assertions.assertThat(response.getUserName()).isEqualTo("lovankydev");

    }

//    @Test
//    public void createUser_

}
