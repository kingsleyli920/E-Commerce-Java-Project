package com.kl.ecommerce.service;

import com.kl.ecommerce.domain.Order;
import com.kl.ecommerce.domain.PageModel;
import com.kl.ecommerce.domain.User;

public interface OrderService {
    void saveOrder(Order order) throws Exception;

    PageModel findMyOrderWithPage(User user, int curNum) throws Exception;

    Order findOrderByOid(String oid)throws Exception;
}
