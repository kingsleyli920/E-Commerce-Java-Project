package com.kl.ecommerce.service;

import com.kl.ecommerce.domain.Order;
import com.kl.ecommerce.domain.PageModel;
import com.kl.ecommerce.domain.User;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order) throws Exception;

    PageModel findMyOrderWithPage(User user, int curNum) throws Exception;

    Order findOrderByOid(String oid) throws Exception;

    void updateOrder(Order order) throws Exception;

    List<Order> findAllOrders() throws Exception;

    List<Order> findAllOrders(String state) throws Exception;
}
