package com.nurseVarsity.BackEndCore.dto;

import com.nurseVarsity.BackEndCore.enums.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfoDto {
    @NotNull( message = "user type cannot be null")
    @NotEmpty(message = "user type must not be empty")
    private String userType;
    @NotNull( message = "education level cannot be null")
    @NotEmpty(message = "education level  must not be empty")
    private String eduLevel;
    @NotNull( message = "institution cannot be null")
    @NotEmpty(message = "institution  must not be empty")
    private String institution;
    @NotNull( message = "graduation year cannot be null")
    @NotEmpty(message = "graduation year  must not be empty")
    private String graduationYear;
    @NotNull( message = "gender cannot be null")
    @NotEmpty(message = "gender  must not be empty")
    private Gender gender;
    @NotNull( message = "institution cannot be null")
    @NotEmpty(message = "institution  must not be empty")
    private String state;
    @NotNull( message = "country cannot be null")
    @NotEmpty(message = "country  must not be empty")
    private String country;
    @NotNull( message = "username cannot be null")
    @NotEmpty(message = "username  must not be empty")
    private String username;
    @NotNull( message = "email cannot be null")
    @NotEmpty(message = "email  must not be empty")
    private String email;
    @NotNull( message = "profile picture cannot be null")
    @NotEmpty(message = "profile picture  must not be empty")
    private String profilePicture;

}
