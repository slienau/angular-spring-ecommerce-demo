package dev.appexpert.demo.ecommerce.service;

import dev.appexpert.demo.ecommerce.dto.Purchase;
import dev.appexpert.demo.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
