package dev.appexpert.demo.ecommerce.dao;

import dev.appexpert.demo.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
