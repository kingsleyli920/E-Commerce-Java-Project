package com.kl.ecommerce.service.serviceImpl;

import com.kl.ecommerce.dao.CategoryDao;
import com.kl.ecommerce.dao.daoImpl.CategoryDaoImpl;
import com.kl.ecommerce.domain.Category;
import com.kl.ecommerce.service.CategoryService;
import com.kl.ecommerce.utils.BeanFactory;
import com.kl.ecommerce.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements CategoryService{
//    CategoryDao categoryDao = (CategoryDao) new BeanFactory().createDaoInstance("CategoryDao");
    CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> getAllCates() throws Exception {


        return categoryDao.getAllCates();
    }

    @Override
    public void addCate(Category category) throws Exception {
        categoryDao.addCate(category);

        Jedis jedis = JedisUtils.getJedis();
        jedis.del("allCates");
        JedisUtils.closeJedis(jedis);

    }
}
