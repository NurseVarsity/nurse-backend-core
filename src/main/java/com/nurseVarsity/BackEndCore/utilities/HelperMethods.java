package com.nurseVarsity.BackEndCore.utilities;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.Random;
import java.util.UUID;

public class HelperMethods {

    private JwtDecoder jwtDecoder;

    private HelperMethods(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public static String generateOTP() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String generateOtpTest() {
        return "0000";
    }

    public static String generateUniqueReference() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String getPrincipal(String authorizationHeader) {
        String jwtToken = authorizationHeader.substring("Bearer ".length());
        Jwt principal = jwtDecoder.decode(jwtToken);
        return principal.getSubject();
    }

    public static String helperSingleton(JwtDecoder jwtDecoder, String authorizationHeader){
        HelperMethods helper = new HelperMethods(jwtDecoder);
        return helper.getPrincipal(authorizationHeader);
    }
}