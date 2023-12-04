package com.mainpackage.blogappapis.services;


import com.mainpackage.blogappapis.payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    CategoryDto getCategoryById(Integer id);
    List<CategoryDto> getAllCategory();
    void deleteCategory(Integer categoryId);
}
