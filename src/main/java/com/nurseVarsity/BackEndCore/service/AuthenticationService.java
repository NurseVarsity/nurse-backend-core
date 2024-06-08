package com.nurseVarsity.BackEndCore.service;

import com.nurseVarsity.BackEndCore.dto.LoginReqDto;
import com.nurseVarsity.BackEndCore.dto.LoginResponseDto;
import com.nurseVarsity.BackEndCore.dto.PersonalInfoDto;
import com.nurseVarsity.BackEndCore.dto.RegUserDto;
import com.nurseVarsity.BackEndCore.entity.PersonalInformation;
import com.nurseVarsity.BackEndCore.entity.Users;

public interface AuthenticationService {
    Users registerUser(RegUserDto regUserDto);
    boolean checkUsername(String username);
    LoginResponseDto loginUser(LoginReqDto loginReqDto);
    boolean verifyUser(String email, String otp);
    PersonalInformation addPersonalInfo(PersonalInfoDto personalInfoDto);
    boolean sendToken(String email);
    boolean forgotPassword(LoginReqDto loginReqDto);
}
