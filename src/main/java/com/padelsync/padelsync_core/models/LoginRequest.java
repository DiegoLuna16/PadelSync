package com.padelsync.padelsync_core.models;

public class LoginRequest {
    private String email;
    private String password;

    // Getters y Setters


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

