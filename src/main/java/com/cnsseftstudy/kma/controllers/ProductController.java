package com.cnsseftstudy.kma.controllers;

import com.cnsseftstudy.kma.models.Brand;
import com.cnsseftstudy.kma.models.Category;
import com.cnsseftstudy.kma.models.Product;
import com.cnsseftstudy.kma.models.ResponseObject;
import com.cnsseftstudy.kma.repositories.BrandRepository;
import com.cnsseftstudy.kma.repositories.CategoryRepository;
import com.cnsseftstudy.kma.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/v1/Products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository repository;
    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllProduct() {
        List<Product> allProduct = repository.findAll();
        if (allProduct.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Fetch data successfully!", allProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Fetch data failed", "")
            );
        }
    }

    @GetMapping("{id}")
    ResponseEntity<ResponseObject> findProductById(@PathVariable Long id) {
        Optional<Product> productItem = repository.findById(id);
        if (productItem.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Query data successful", productItem)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "No record suitable!", "")
            );
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<ResponseObject> postProduct(@RequestBody Product productData) {
        List<Product> checkProductDuplicate = repository.findByProductName(productData.getProductName().trim());
//        Brand brand = new Brand();
//        brand.setId(productData.getBrand().getId());
//        brand.setBrandName(productData.getBrand().getBrandName());
//        brand.setDesc(productData.getBrand().getDesc());
//        brand.setProducts(Collections.singleton(productData));
//        brandRepository.saveAndFlush(brand);
        if (checkProductDuplicate.size() <= 0) {
            repository.save(productData);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Insert product successfully!", productData)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Insert product failed, product is Dupplicated!", "")
            );
        }
    }

    @PutMapping("/{productId}/brand/{brandId}")
    ResponseEntity<ResponseObject> enrollProductToBrand(@PathVariable Long productId, @PathVariable Long brandId) {
        Brand brand = brandRepository.findById(brandId).get();
        Product product = repository.findById(productId).get();

        product.setBrand(brand);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Enroll Brand successful!", repository.save(product))
        );
    }

    @PutMapping("/{productId}/categories/{categoryId}")
    ResponseEntity<ResponseObject> enrollProductWithCategory(@PathVariable Long productId, @PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        Product product = repository.findById(productId).get();

        product.enrolledCategories.add(category);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Enroll Brand successful!", repository.save(product))
        );
    }

//    @PutMapping("/{id}")
//    ResponseEntity<ResponseObject> editProduct(@PathVariable Long id, @RequestBody Product editProductData) {
//
//        Product updateProduct = repository.findById(id).orElse(null);
//
//        if (updateProduct == null) {
//            updateProduct.setProductName(editProductData.getProductName());
//            updateProduct.setPrice(editProductData.getPrice());
//            updateProduct.setYear(editProductData.getYear());
//
//            Brand brand = new Brand();
//            brand.setId(editProductData.getBrand().getId());
//            brand.setBrandName(editProductData.getBrand().getBrandName());
//            brand.setDesc(editProductData.getBrand().getDesc());
//            brand.setProducts(Collections.singleton(editProductData));
//            brandRepository.saveAndFlush(brand);
//        } else {
//            editProductData.setId(id);
//            Brand brand = new Brand();
//            brand.setId(editProductData.getBrand().getId());
//            brand.setBrandName(editProductData.getBrand().getBrandName());
//            brand.setDesc(editProductData.getBrand().getBrandName());
//            brand.setProducts(Collections.singleton(editProductData));
//            brandRepository.saveAndFlush(brand);
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("OK", "Update product successfully", "")
//        );
//    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        Boolean checkDeleteProduct = repository.existsById(id);
        if (checkDeleteProduct) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Delete Product Successfully!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Cannot delete product, try again!", "")
            );
        }
    }
}
