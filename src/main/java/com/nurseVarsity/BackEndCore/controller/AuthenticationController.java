package com.nurseVarsity.BackEndCore.controller;

import com.nurseVarsity.BackEndCore.dto.*;
import com.nurseVarsity.BackEndCore.entity.Users;
import com.nurseVarsity.BackEndCore.exceptions.BadRequestException;
import com.nurseVarsity.BackEndCore.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<Response<Users>> register(
            @Valid @RequestBody RegUserDto regUserDto
            ){
        this.authenticationService.registerUser(regUserDto);
        return new ResponseEntity<>(
                new Response<>("00", "user registered successfully",
                        true, null), HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponseDto>> login(
            @Valid @RequestBody LoginReqDto loginReqDto
            ){
        LoginResponseDto loginResponseDto = this.authenticationService.loginUser(loginReqDto);
        return new ResponseEntity<>(
                new Response<>("00", "login successful",
                        true, loginResponseDto), HttpStatus.OK
        );
    }

    @GetMapping("/checkUsername")
    public ResponseEntity<Response<String>> checkUsername(
            @RequestParam("username") Optional<String> username
    ){
        String user = username.orElseThrow(
                () -> new BadRequestException("username is required")
        );
        this.authenticationService.checkUsername(user);
        return new ResponseEntity<>(
                new Response<>("00", "username is available",
                        true, null), HttpStatus.OK
        );
    }

    @PostMapping("/verifyUser")
    public ResponseEntity<Response<String>> verifyUsername(
            @Valid @RequestBody VerifyDto verifyDto
    ){
        this.authenticationService.verifyUser(verifyDto.getEmail(), verifyDto.getOtp());
        return new ResponseEntity<>(
                new Response<>("00", "verified successfully",
                        true, null), HttpStatus.OK
        );
    }

    @PostMapping("/personalInfo")
    public ResponseEntity<Response<String>> personalInfo(
            @Valid @RequestBody PersonalInfoDto personalInfoDto
    ){
        this.authenticationService.addPersonalInfo(personalInfoDto);
        return new ResponseEntity<>(
                        new Response<>("00", "personal details updated",
                                true, null), HttpStatus.CREATED
        );
    }

    @GetMapping("/sendToken")
    public ResponseEntity<Response<String>> sendToken(
            @RequestParam("email") Optional<String> enailParam
    ){
        String email = enailParam.orElseThrow(
                () -> new BadRequestException("provide user email")
        );
        this.authenticationService.sendToken(email);
        return new ResponseEntity<>(
                new Response<>("00", "token sent to user email",
                        true, null), HttpStatus.CREATED
        );
    }
}
