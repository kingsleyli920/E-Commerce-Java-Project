package com.kl.ecommerce.dao.daoImpl;

import com.kl.ecommerce.dao.CategoryDao;
import com.kl.ecommerce.domain.Category;
import com.kl.ecommerce.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    /**
     * 获取全部分类
     * @return
     * @throws Exception
     */
    @Override
    public List<Category> getAllCates() throws Exception {
        String sql = "select * from category";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

        return  queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
    }
}
