package dev.appexpert.demo.ecommerce.dao;

import dev.appexpert.demo.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;


public interface ProductRepository extends JpaRepository<Product, Long> {

    // Query method (spring will generate and execute the query automatically)
    // will be exposed automatically
    // /api/products/search/findByCategoryId?id=2
    Page<Product> findByCategoryId(@Param("id") Long id, Pageable pageable);

    // /api/products/search/findByNameContaining?name="book"
    Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);
}
