package com.padelsync.padelsync_core.dtos;

public class UserDTO {
    
    private String token;
    private String username;
    private String message;

    public UserDTO(String token, String username, String message) {
        this.token = token;
        this.username = username;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
