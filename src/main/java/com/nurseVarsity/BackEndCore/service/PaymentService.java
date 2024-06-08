package com.nurseVarsity.BackEndCore.service;

import com.nurseVarsity.BackEndCore.dto.CheckoutDto;
import com.stripe.exception.StripeException;

public interface PaymentService {
    String makePayment(String email, String plan) throws StripeException;
}
