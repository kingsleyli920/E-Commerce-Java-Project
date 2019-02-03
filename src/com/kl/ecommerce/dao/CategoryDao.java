package com.kl.ecommerce.dao;

import com.kl.ecommerce.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCates() throws Exception;
    void addCate(Category category) throws Exception;
}
