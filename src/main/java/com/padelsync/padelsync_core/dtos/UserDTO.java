package com.padelsync.padelsync_core.dtos;

import java.time.LocalDate;
import java.util.List;

public class UserDTO {
    
    private Long id;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String countryCode;
    private LocalDate birthdate;
    private String gender;
    private List<String> roles;

    public UserDTO(Long id, String username, String name, String email, String phone, String countryCode,
            LocalDate birthdate, String gender, List<String> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.countryCode = countryCode;
        this.birthdate = birthdate;
        this.gender = gender;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getGender() {
        return gender;
    }

    public List<String> getRoles() {
        return roles;
    }

    

    




}
