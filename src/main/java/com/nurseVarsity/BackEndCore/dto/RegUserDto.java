package com.nurseVarsity.BackEndCore.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegUserDto {
    @NotNull(message = "email cannot be empty")
    private String email;
    @NotNull(message = "first name cannot be empty")
    private String firstName;
    @NotNull(message = "last name cannot be empty")
    private String lastName;
    @NotNull(message = "password cannot be empty")
    private String password;
    @NotNull(message = "phone number cannot be empty")
    private String phoneNumber;
}
