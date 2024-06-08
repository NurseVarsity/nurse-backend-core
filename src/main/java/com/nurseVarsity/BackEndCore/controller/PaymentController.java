package com.nurseVarsity.BackEndCore.controller;

import com.nurseVarsity.BackEndCore.dto.Response;
import com.nurseVarsity.BackEndCore.exceptions.BadRequestException;
import com.nurseVarsity.BackEndCore.service.PaymentService;
import com.nurseVarsity.BackEndCore.utilities.HelperMethods;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin("*")
public class PaymentController {
    private final PaymentService paymentService;
    private final JwtDecoder jwtDecoder;

    public PaymentController(PaymentService paymentService, JwtDecoder jwtDecoder) {
        this.paymentService = paymentService;
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping
    public ResponseEntity<Response<String>> makePayment(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @RequestParam("plan") Optional<String> plan
    ) throws StripeException {
        String principal = HelperMethods.helperSingleton(this.jwtDecoder, authorizationHeader);
        if(plan.isEmpty()){
            throw new BadRequestException("amount not provided");
        }
        String url = this.paymentService.makePayment(principal, plan.get());
        return new ResponseEntity<>(
                new Response<>(
                        "00", "proceed to checkout", true, url
                ), HttpStatus.CREATED
        );
    }
}
