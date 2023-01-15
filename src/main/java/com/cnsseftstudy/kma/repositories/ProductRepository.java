package com.cnsseftstudy.kma.repositories;

import com.cnsseftstudy.kma.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
}
