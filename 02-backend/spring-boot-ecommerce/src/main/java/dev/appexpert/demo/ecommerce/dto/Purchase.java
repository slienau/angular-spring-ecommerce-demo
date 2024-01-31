package dev.appexpert.demo.ecommerce.dto;

import dev.appexpert.demo.ecommerce.entity.Address;
import dev.appexpert.demo.ecommerce.entity.Customer;
import dev.appexpert.demo.ecommerce.entity.Order;
import dev.appexpert.demo.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
