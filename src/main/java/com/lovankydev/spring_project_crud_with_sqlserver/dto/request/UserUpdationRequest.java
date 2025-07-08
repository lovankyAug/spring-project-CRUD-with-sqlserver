package com.lovankydev.spring_project_crud_with_sqlserver.dto.request;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserUpdationRequest {

    @Size(min = 3, max = 20, message = "Username requires from 3 to 20 letters for an username. ")
    private String userName;
    
    @Size(min = 8, message = "Password requires at least 8 letters.")
    private String password;
    private LocalDate dob;
    private String address;
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
