package com.kl.ecommerce.service;

import com.kl.ecommerce.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCates() throws Exception;

    void addCate(Category category) throws Exception;

}
