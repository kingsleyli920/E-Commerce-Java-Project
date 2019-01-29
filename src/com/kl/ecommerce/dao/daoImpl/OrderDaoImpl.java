package com.kl.ecommerce.dao.daoImpl;

import com.kl.ecommerce.dao.OrderDao;
import com.kl.ecommerce.domain.Order;
import com.kl.ecommerce.domain.OrderItem;
import com.kl.ecommerce.domain.Product;
import com.kl.ecommerce.domain.User;
import com.kl.ecommerce.utils.JDBCUtils;
import javafx.beans.binding.ObjectExpression;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void saveOrderItem(Connection connection, OrderItem item) throws Exception {
        String sql = "insert into orderitem values (?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {
                item.getItemid(),
                item.getQuantity(),
                item.getTotal(),
                item.getProduct().getPid(),
                item.getOrder().getOid()
        };
        queryRunner.update(connection, sql, params);
    }

    @Override
    public void saveOrder(Connection connection, Order order) throws Exception {
        String sql = "insert into orders values (?,?,?,?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {
            order.getOid(),
            order.getOrdertime(),
            order.getTotal(),
            order.getState(),
            order.getAddress(),
            order.getName(),
            order.getTelephone(),
            order.getUser().getUid(),
        };
        queryRunner.update(connection, sql, params);
    }

    @Override
    public int getTotalRecords(User user) throws Exception {

        String sql = "select count(*) from orders where uid=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long) queryRunner.query(sql, new ScalarHandler(), user.getUid());
        return num.intValue();
    }

    @Override
    public List findMyOrderWithPage(User user, int startIndex, int pageSize) throws Exception {
        String sql = "select * from orders where uid=? limit ?, ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        List<Order> list = queryRunner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex, pageSize);

        for (Order order : list) {
            String oid = order.getOid();
            sql="select * from orderitem o, product p where o.pid=p.pid and oid=?";
            List<Map<String, Object>> list1 = queryRunner.query(sql, new MapListHandler(), oid);

            for (Map<String, Object> map : list1) {
                OrderItem orderItem = new OrderItem();
                Product product = new Product();
                DateConverter dateConverter = new DateConverter();
                dateConverter.setPattern("yyyy-MM-dd");
                ConvertUtils.register(dateConverter, Date.class);

                BeanUtils.populate(orderItem, map);
                BeanUtils.populate(product, map);

                orderItem.setProduct(product);

                order.getList().add(orderItem);
            }
        }
        return list;
    }

    @Override
    public Order findOrderByOid(String oid) throws Exception {
        String sql = "select * from orders where oid=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Order order = queryRunner.query(sql, new BeanHandler<Order>(Order.class), oid);

        String sql1 = "select * from orderitem o, product p where o.pid=p.pid and oid=?";
        List<Map<String, Object>> list1 = queryRunner.query(sql1, new MapListHandler(), oid);

        for (Map<String, Object> map : list1) {
            OrderItem orderItem = new OrderItem();
            Product product = new Product();
            DateConverter dateConverter = new DateConverter();
            dateConverter.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dateConverter, Date.class);

            BeanUtils.populate(orderItem, map);
            BeanUtils.populate(product, map);

            orderItem.setProduct(product);

            order.getList().add(orderItem);
        }
        return order;
    }
}
