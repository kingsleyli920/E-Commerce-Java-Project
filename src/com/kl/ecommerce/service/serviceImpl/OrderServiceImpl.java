package com.kl.ecommerce.service.serviceImpl;

import com.kl.ecommerce.dao.OrderDao;
import com.kl.ecommerce.dao.daoImpl.OrderDaoImpl;
import com.kl.ecommerce.domain.Order;
import com.kl.ecommerce.domain.OrderItem;
import com.kl.ecommerce.domain.PageModel;
import com.kl.ecommerce.domain.User;
import com.kl.ecommerce.service.OrderService;
import com.kl.ecommerce.utils.BeanFactory;
import com.kl.ecommerce.utils.JDBCUtils;

import java.sql.Connection;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = (OrderDao) BeanFactory.createDaoInstance("OrderDao");
    @Override
    public void saveOrder(Order order) throws Exception {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);

            orderDao = new OrderDaoImpl();
            orderDao.saveOrder(connection, order);
            for (OrderItem item : order.getList()) {
                orderDao.saveOrderItem(connection, item);
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }
    }

    @Override
    public PageModel findMyOrderWithPage(User user, int curNum) throws Exception {
        int totalRecord = orderDao.getTotalRecords(user);
        PageModel pageModel = new PageModel(curNum, totalRecord, 1);
        List list = orderDao.findMyOrderWithPage(user, pageModel.getStartIndex(), pageModel.getPageSize());
        System.out.println(list);
        pageModel.setList(list);
        pageModel.setUrl("OrderServlet?method=findMyOrdersWithPage");
        return pageModel;
    }

    @Override
    public Order findOrderByOid(String oid) throws Exception {

        return orderDao.findOrderByOid(oid);
    }

    @Override
    public void updateOrder(Order order) throws Exception {
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> findAllOrders() throws Exception {
        return orderDao.findAllOrders();
    }

    @Override
    public List<Order> findAllOrders(String state) throws Exception {
        return orderDao.findAllOrders(state);
    }
}
