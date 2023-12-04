package com.mainpackage.blogappapis.services.impl;

import com.mainpackage.blogappapis.entities.Category;
import com.mainpackage.blogappapis.entities.User;
import com.mainpackage.blogappapis.exceptions.ResourceNotFoundException;
import com.mainpackage.blogappapis.payloads.CategoryDto;
import com.mainpackage.blogappapis.payloads.UserDto;
import com.mainpackage.blogappapis.repositories.CategoryRepo;
import com.mainpackage.blogappapis.repositories.UserRepo;
import com.mainpackage.blogappapis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto,Category.class);

        Category savedCat = categoryRepo.save(category);
        return this.modelMapper.map(savedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", " id ",categoryId));
        cat.setCatergoryTitle(categoryDto.getCatergoryTitle());
        cat.setCatergoryDiscription(categoryDto.getCatergoryDiscription());
        Category savedCategory = categoryRepo.save(cat);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category", " id ",id));

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> all = categoryRepo.findAll();

        List<CategoryDto> categoryDtos = all.stream()
                .map((cat) -> this.modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", " id ",categoryId));

        categoryRepo.delete(category);
    }
}
