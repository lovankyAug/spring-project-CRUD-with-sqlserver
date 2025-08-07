package com.lovankydev.spring_project_crud_with_sqlserver.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.request.UserCreationRequest;
import com.lovankydev.spring_project_crud_with_sqlserver.dto.respone.UserResponse;
import com.lovankydev.spring_project_crud_with_sqlserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
//@TestPropertySource("/test.properties")
@AutoConfigureMockMvc
public class UserControllerTest {


    UserCreationRequest request;
    UserResponse userResponse;
    LocalDate date;

    @Autowired
   private MockMvc mockMvc;

    @MockBean
   private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void initData(){

        date = LocalDate.of(2002, 9, 21);

        request = UserCreationRequest.builder()
                .userName("jame")
                .password("1234567890")
                .email("ngoanxinhtrai@gmail.com")
                .address("nghean")
                .dob(date)
                .build();

        userResponse = UserResponse.builder()
                .id("abc")
                .userName("jame")
                .address("nghean")
                .email("ngoanxinhtrai@gmail.com")
                .dob(date)
                .build();
    }

    @Test
    public void createUser_validRequest_Success() throws Exception {

        //GIVEN

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.userCreationService(ArgumentMatchers.any())).thenReturn(userResponse);
        //WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.userName").value("jame"));


    }

    @Test
    public void createUser_userNameInvalid_Fail() throws Exception {
        //GIVEN
        request.setUserName("ja");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        //WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1009))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username requires from 4 to 20 letters for an username."));
    }


}
