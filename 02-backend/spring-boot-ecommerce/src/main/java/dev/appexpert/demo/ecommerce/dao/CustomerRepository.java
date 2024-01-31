package dev.appexpert.demo.ecommerce.dao;

import dev.appexpert.demo.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}
