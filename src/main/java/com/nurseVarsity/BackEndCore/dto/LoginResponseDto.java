package com.nurseVarsity.BackEndCore.dto;

import com.nurseVarsity.BackEndCore.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String email;
    private Long userId;
    private String token;
}
