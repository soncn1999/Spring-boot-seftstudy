package com.cnsseftstudy.kma.controllers;

import com.cnsseftstudy.kma.models.Product;
import com.cnsseftstudy.kma.models.ResponseObject;
import com.cnsseftstudy.kma.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

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
        System.out.println(productData);
        List<Product> checkProductDuplicate = repository.findByProductName(productData.getProductName().trim());

        if (checkProductDuplicate.size() <= 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Insert product successfully!", repository.save(productData))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Insert product failed, product is Dupplicated!", "")
            );
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> editProduct(@PathVariable Long id, @RequestBody Product editProductData) {
        Product updateProduct = repository.findById(id).map(product -> {
            product.setProductName(editProductData.getProductName());
            product.setPrice(editProductData.getPrice());
            product.setYear(editProductData.getYear());
            return repository.save(product);
        }).orElseGet(() -> {
            editProductData.setId(id);
            return repository.save(editProductData);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "Update product successfully", updateProduct)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct (@PathVariable Long id) {
        Boolean checkDeleteProduct = repository.existsById(id);
        if(checkDeleteProduct) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK","Delete Product Successfully!","")
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed","Cannot delete product, try again!","")
            );
        }
    }
}
