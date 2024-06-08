package com.nurseVarsity.BackEndCore.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VerifyDto {
    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    private String email;
    @NotNull(message = "otp cannot be null")
    @NotEmpty(message = "otp cannot be empty")
    private String otp;
}
