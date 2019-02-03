package com.kl.ecommerce.dao;

import com.kl.ecommerce.domain.Order;
import com.kl.ecommerce.domain.OrderItem;
import com.kl.ecommerce.domain.User;

import java.sql.Connection;
import java.util.List;

public interface OrderDao {
    void saveOrderItem(Connection connection, OrderItem item) throws Exception;

    void saveOrder(Connection connection, Order order) throws Exception;

    int getTotalRecords(User user) throws Exception;

    List findMyOrderWithPage(User user, int startIndex, int pageSize) throws Exception;

    Order findOrderByOid(String oid) throws Exception;

    void updateOrder(Order order) throws Exception;

    List<Order> findAllOrders() throws Exception;

    List<Order> findAllOrders(String state)throws Exception;;
}
