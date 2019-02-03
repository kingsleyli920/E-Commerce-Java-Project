package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.Order;
import com.kl.ecommerce.service.OrderService;
import com.kl.ecommerce.service.serviceImpl.OrderServiceImpl;
import com.kl.ecommerce.web.base.BaseServlet;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.util.CycleDetectionStrategy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AdminOrderServlet", urlPatterns = "/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {

    /**
     * 获取订单列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OrderService orderService = new OrderServiceImpl();
        String state = request.getParameter("state");
        List<Order> list = null;
        if (null == state || state.equals("")) {
            list = orderService.findAllOrders();
        } else {
            list = orderService.findAllOrders(state);
        }

        request.setAttribute("allOrders", list);
        return "/admin/order/list.jsp";
    }

    /**
     * 异步请求获取订单详情
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findOrderByOidWithAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oid = request.getParameter("id");
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.findOrderByOid(oid);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

//resolve  java.lang.IllegalArgumentException java.sql.Date.getHours(Date.java:177)
        jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, new DateJsonValueProcessor());

//resolve  java.sql.SQLException: Positioned Update not supported.
        jsonConfig.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
        String orderStr = JSONArray.fromObject(order.getList(), jsonConfig).toString();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(orderStr);
        return null;
    }

    /**
     * 异步请求获取订单详情
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String updateOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oid = request.getParameter("oid");
        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.findOrderByOid(oid);
        order.setState(3);
        orderService.updateOrder(order);

        response.sendRedirect("/ECommerce/AdminOrderServlet?method=findOrders&state=3");
        return null;
    }



    //重载类型
    class DateJsonValueProcessor implements JsonBeanProcessor {

        public JSONObject processBean(Object bean, JsonConfig arg1) {
            JSONObject jsonObject = null;
            if( bean instanceof java.sql.Date ){
                bean = new Date( ((java.sql.Date) bean).getTime() );
            }
            if( bean instanceof java.sql.Timestamp ){
                bean = new Date( ((java.sql.Timestamp) bean).getTime() );
            }
            if( bean instanceof Date ){
                jsonObject = new JSONObject();
                jsonObject.element("time", ( (Date) bean ).getTime());
            }else{
                jsonObject = new JSONObject( true );
            }
            return jsonObject;
        }
    }

}
