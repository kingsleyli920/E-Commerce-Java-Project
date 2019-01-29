package com.kl.ecommerce.service.serviceImpl;

import com.kl.ecommerce.dao.CategoryDao;
import com.kl.ecommerce.dao.daoImpl.CategoryDaoImpl;
import com.kl.ecommerce.domain.Category;
import com.kl.ecommerce.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> getAllCates() throws Exception {
        CategoryDao categoryDao = new CategoryDaoImpl();

        return categoryDao.getAllCates();
    }
}
