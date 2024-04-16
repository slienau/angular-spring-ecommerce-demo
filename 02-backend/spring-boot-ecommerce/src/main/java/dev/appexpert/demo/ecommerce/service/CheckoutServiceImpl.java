package dev.appexpert.demo.ecommerce.service;

import dev.appexpert.demo.ecommerce.dao.CustomerRepository;
import dev.appexpert.demo.ecommerce.dto.Purchase;
import dev.appexpert.demo.ecommerce.dto.PurchaseResponse;
import dev.appexpert.demo.ecommerce.entity.Customer;
import dev.appexpert.demo.ecommerce.entity.Order;
import dev.appexpert.demo.ecommerce.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve the order info from DTO
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::addItem);

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();

        // check if this is an existing customer
        Customer customerFromDb = customerRepository.findByEmail(customer.getEmail());

        if (customerFromDb != null) {
            customer = customerFromDb;
        }

        customer.addOrder(order);

        // save to DB
        customerRepository.save(customer);

        // return response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
