package com.kl.ecommerce.dao.daoImpl;

import com.kl.ecommerce.dao.ProductDao;
import com.kl.ecommerce.domain.PageModel;
import com.kl.ecommerce.domain.Product;
import com.kl.ecommerce.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public List<Product> findHots() throws Exception {
        String sql = "select * from product where pflag=0 and is_hot=1 order by pdate desc limit 0, 9";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
    }

    @Override
    public List<Product> findNews() throws Exception {
        String sql = "select * from product where pflag=0 order by pdate desc limit 0, 9";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
    }

    @Override
    public Product findProductByPid(String pid) throws Exception {
        String sql = "select * from product where pid=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
    }

    @Override
    public int findTotalProductsByCid(String cid) throws Exception {
        String sql = "select count(*) from product where cid=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long) queryRunner.query(sql, new ScalarHandler(), cid);
        return num.intValue();
    }

    @Override
    public List findProductByCidWithPage(String cid, int startIndex, int pageSize) throws Exception {
        String sql = "select * from product where cid=? limit ?, ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid, startIndex, pageSize);
    }
}
