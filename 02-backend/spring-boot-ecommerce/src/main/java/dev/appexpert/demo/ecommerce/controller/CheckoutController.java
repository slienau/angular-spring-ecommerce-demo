package dev.appexpert.demo.ecommerce.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import dev.appexpert.demo.ecommerce.dto.PaymentInfo;
import dev.appexpert.demo.ecommerce.dto.Purchase;
import dev.appexpert.demo.ecommerce.dto.PurchaseResponse;
import dev.appexpert.demo.ecommerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private Logger logger = Logger.getLogger(getClass().getName());

    private CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        return checkoutService.placeOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        logger.info("paymentInfo.amount: " + paymentInfo.getAmount());
        
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);

        String paymentIntentJson = paymentIntent.toJson();

        return new ResponseEntity<>(paymentIntentJson, HttpStatus.OK);
    }
}
