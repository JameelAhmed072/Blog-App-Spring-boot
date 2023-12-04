package com.mainpackage.blogappapis.controllers;


import com.mainpackage.blogappapis.payloads.ApiResponse;
import com.mainpackage.blogappapis.payloads.CategoryDto;
import com.mainpackage.blogappapis.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){

        CategoryDto categoryCreated = categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);

    }
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> allCategory(){

        List<CategoryDto> allCategory = categoryService.getAllCategory();

        return ResponseEntity.ok(allCategory);

    }
    @GetMapping("/category/{id}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer id){

        CategoryDto allCategory = categoryService.getCategoryById(id);
        return ResponseEntity.ok(allCategory);
    }
    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer id){

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,id);
        return ResponseEntity.ok(updatedCategory);
    }
    @DeleteMapping("/category/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse("Category deleted Successfully",true),HttpStatus.OK);
    }
}
