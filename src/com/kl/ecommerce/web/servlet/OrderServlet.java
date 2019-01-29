package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.*;
import com.kl.ecommerce.service.OrderService;
import com.kl.ecommerce.service.serviceImpl.OrderServiceImpl;
import com.kl.ecommerce.utils.UUIDUtils;
import com.kl.ecommerce.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "OrderServlet", urlPatterns = "/OrderServlet")
public class OrderServlet extends BaseServlet {

    /**
     * 保存订单
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user == null) {
            request.setAttribute("msg", "请登录后再下单");
            return "/jsp/info.jsp";
        }

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Order order = new Order();
        order.setOid(UUIDUtils.getCode());
        order.setOrdertime(new Date(new java.util.Date().getTime()));
        order.setTotal(cart.getTotal());
        order.setState(1);
        order.setUser(user);

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemid(UUIDUtils.getCode());
            orderItem.setOrder(order);
            orderItem.setQuantity(item.getNum());
            orderItem.setTotal(item.getSubTotal());
            orderItem.setProduct(item.getProduct());

            order.getList().add(orderItem);
        }
        OrderService orderService = new OrderServiceImpl();
        orderService.saveOrder(order);

        cart.clearCart();

        request.setAttribute("order", order);
        return "/jsp/order_info.jsp";
    }

    /**
     * 进入订单列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findMyOrdersWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("loginUser");
        int curNum = Integer.parseInt(request.getParameter("num"));
        OrderService orderService = new OrderServiceImpl();
        PageModel pageModel = orderService.findMyOrderWithPage(user, curNum);

        request.setAttribute("pageModel", pageModel);
        return "/jsp/order_list.jsp";
    }

    /**
     * 通过订单号查找订单 进入订单详情
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oid = request.getParameter("oid");

        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.findOrderByOid(oid);
        request.setAttribute("order", order);
        return "/jsp/order_info.jsp";
    }
}
