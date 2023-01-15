package com.cnsseftstudy.kma.controllers;

import com.cnsseftstudy.kma.models.Category;
import com.cnsseftstudy.kma.models.ResponseObject;
import com.cnsseftstudy.kma.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("")
    ResponseEntity<ResponseObject> getAllCategory() {
        List<Category> allCategory = categoryRepository.findAll();
        if (allCategory.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Fetch all category successfully!", allCategory)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Fetch category failed!", "")
            );
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findCategoryById(@PathVariable Long id) {
        Optional<Category> findingCategory = categoryRepository.findById(id);
        if (findingCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Finding Category successfully", findingCategory)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Cannot find any suitable record!", "")
            );
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<ResponseObject> postCategory(@RequestBody Category category) {
        List<Category> duplicateCategory = categoryRepository.findByCategoryName(category.getCategoryName().trim());

        if (duplicateCategory.size() <= 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Insert New Category successfully!", categoryRepository.save(category))
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "This category is Duplicate!", "")
            );
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> editCategory(@RequestBody Category categoryEdit, @PathVariable Long id) {
        Category findingCategory = categoryRepository.findById(id).map(category -> {
            category.setCategoryName(categoryEdit.getCategoryName());
            return categoryRepository.save(category);
        }).orElseGet(() -> {
            categoryEdit.setId(id);
            return categoryRepository.save(categoryEdit);
        });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK","Updated category infomation!",findingCategory)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteCategory(@PathVariable Long id) {
        Optional<Category> findingCategory = categoryRepository.findById(id);

        if(findingCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK","Delete Category successfully!",findingCategory)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed","Delete Category failed, this category doesnt exist","")
            );
        }
    }
}
