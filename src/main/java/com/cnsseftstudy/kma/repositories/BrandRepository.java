package com.cnsseftstudy.kma.repositories;

import com.cnsseftstudy.kma.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByBrandName(String brandName);
}
