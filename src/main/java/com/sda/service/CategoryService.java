package com.sda.service;

import com.sda.entity.Category;
import com.sda.entity.Role;
import com.sda.entity.User;
import com.sda.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Page<Category> findAllPagination(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }


}
