package com.nurseVarsity.BackEndCore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {
    private String customerEmail;
    private Long amount;

}
