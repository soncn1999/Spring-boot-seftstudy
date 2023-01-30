package com.cnsseftstudy.kma.controllers;

import com.cnsseftstudy.kma.models.Brand;
import com.cnsseftstudy.kma.models.ResponseObject;
import com.cnsseftstudy.kma.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Brands")
public class BrandController {
    @Autowired
    BrandRepository brandRepository;

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllBrand() {
        List<Brand> allBrand = brandRepository.findAll();
        if (allBrand.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Fetch All Brand successful!", allBrand)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Fetch All Brand Failed!", "")
            );
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findBrands(@PathVariable Long id) {
        Brand findingBrand = brandRepository.findById(id).orElse(null);
        if (findingBrand != null) {
            brandRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Find Brand successful!", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Find Brand Failed", "")
            );
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<ResponseObject> postBrand(@RequestBody Brand brand) {
        List<Brand> findingBrand = brandRepository.findByBrandName(brand.getBrandName().trim());
        if (findingBrand.size() <= 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Insert Brand successfully!", brandRepository.save(brand))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Insert Brand failed!", "")
            );
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> editBrand(@RequestBody Brand editBrand, @PathVariable Long id) {
        Brand updateBrand = brandRepository.findById(id).map(brand -> {
            brand.setBrandName(editBrand.getBrandName());
            brand.setDesc(editBrand.getDesc());
            return brandRepository.save(brand);
        }).orElseGet(() -> {
            editBrand.setId(id);
            return brandRepository.save(editBrand);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK","Update Brand successfully!",updateBrand)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteBrand(@PathVariable Long id) {
        Optional<Brand> findingBrand = brandRepository.findById(id);
        if(findingBrand.isPresent()) {
            brandRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK","Delete Brand successful!",findingBrand)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed","Delete Brand Failed!","")
            );
        }
    }
}