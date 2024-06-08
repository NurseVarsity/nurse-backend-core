package com.nurseVarsity.BackEndCore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Response<T> {
    private String responseCode;
    private String responseDescription;
    private boolean status;
    private T data;
}
