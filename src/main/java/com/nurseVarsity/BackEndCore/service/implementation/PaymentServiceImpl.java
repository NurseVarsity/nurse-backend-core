package com.nurseVarsity.BackEndCore.service.implementation;

import com.nurseVarsity.BackEndCore.service.PaymentService;
import com.nurseVarsity.BackEndCore.utilities.HelperMethods;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Value("${plans.enhanced.productId}")
    private String enhanced;
    @Value("${plans.comprehensive.productId}")
    private String comprehensive;
    @Value("${plans.enhanced.price}")
    private String enhancedPrice;
    @Value("${plans.comprehensive.price}")
    private String comprehensivePrice;
    @Value("${external.stripe.apiKey}")
    private String stripeApiKey;
    @Value("${external.stripe.returnLink}")
    private String returnLink;
    @Value("${external.stripe.refreshLink}")
    private String refreshLink;

    @Override
    public String makePayment(String email, String plan) throws StripeException {
        String productId = null;
        Long amount = null;
        Stripe.apiKey = this.stripeApiKey;
        if("ENHANCED".equals(plan)) {
            productId = this.enhanced;
            amount = Long.parseLong(this.enhancedPrice);

        } else if ("COMPREHENSIVE".equals(plan)) {
            productId = this.comprehensive;
            amount = Long.parseLong(this.comprehensivePrice);
        }
        SessionCreateParams params = SessionCreateParams.builder()
                .setCustomerEmail(email)
                .setClientReferenceId(HelperMethods.generateUniqueReference())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amount)
                                                .setProduct(productId)
                                                .build()
                                )
                                .setQuantity(1L)
                                .build()
                )
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(this.returnLink)
                .setCancelUrl(this.returnLink)
                .build();
        Session session = Session.create(params);
        return session.getUrl();
    }


}
