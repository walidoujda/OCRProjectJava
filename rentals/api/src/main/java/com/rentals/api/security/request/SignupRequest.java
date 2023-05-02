package com.rentals.api.security.request;

import jakarta.validation.constraints.*;

public class SignupRequest {

    @NotBlank
    @Size(max = 249)
    @Email
    private String email;

    private String name;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setRole(String name) {
        this.name = name;
    }
}
