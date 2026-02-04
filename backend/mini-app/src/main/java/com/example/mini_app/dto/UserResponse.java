package com.example.mini_app.dto;

public class UserResponse {
    
    private UserDTO user;

    // Constructors
    public UserResponse() {
    }

    public UserResponse(UserDTO user) {
        this.user = user;
    }

    // Getters and Setters
    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
