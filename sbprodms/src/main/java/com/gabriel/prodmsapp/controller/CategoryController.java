package com.gabriel.prodmsapp.controller;

import com.gabriel.prodmsapp.model.Category;
import com.gabriel.prodmsapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/category_data")
    public ResponseEntity<?> listcategory()
    {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;

        try {
            Category[] categories = categoryService.getCategories();
            response =  ResponseEntity.ok().headers(headers).body(categories);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PutMapping("api/category_data")
    public ResponseEntity<?> add(@RequestBody Category category){
        logger.info("Input >> "+  category.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Category newCategory = categoryService.create(category);
            logger.info("created category >> "+  newCategory.toString() );
            response = ResponseEntity.ok(newCategory);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve category with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @PostMapping("api/category")
    public ResponseEntity<?> update(@RequestBody Category category){
        logger.info("Update Input >> "+  category.toString() );
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Category newCategory = categoryService.update(category);
            response = ResponseEntity.ok(category);
        }
        catch( Exception ex)
        {
            logger.error("Failed to retrieve category with id : {}", ex.getMessage(), ex);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @GetMapping("api/category/{id}")
    public ResponseEntity<?> get(@PathVariable final Integer id){
        logger.info("Input category id >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            Category category = categoryService.getCategory(id);
            response = ResponseEntity.ok(category);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }

    @DeleteMapping("api/category/{id}")
    public ResponseEntity<?> delete(@PathVariable final Integer id){
        logger.info("Input >> "+  Integer.toString(id));
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<?> response;
        try {
            categoryService.delete(id);
            response = ResponseEntity.ok(null);
        }
        catch( Exception ex)
        {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
        return response;
    }
}
