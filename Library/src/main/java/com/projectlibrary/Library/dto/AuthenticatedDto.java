package com.projectlibrary.Library.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AuthenticatedDto {

    @Size(min = 2, max = 10, message = "Between 2 to 10 symbols")
    @NotEmpty(message = "The full name cannot be empty")
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
