package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.*;
import com.kl.ecommerce.service.OrderService;
import com.kl.ecommerce.service.serviceImpl.OrderServiceImpl;
import com.kl.ecommerce.utils.PaymentUtil;
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

    public String payOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oid = request.getParameter("oid");
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        String pd_FrpId = request.getParameter("pd_FrpId");

        OrderService orderService = new OrderServiceImpl();
        Order order = orderService.findOrderByOid(oid);
        order.setName(name);
        order.setAddress(address);
        order.setTelephone(telephone);

        orderService.updateOrder(order);
        String p0_Cmd = "Buy";
        String p1_MerId = "10001126856";
        String p2_Order = oid;
        String p3_Amt = "0.01";
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        String p8_Url = "http://localhost:8080/ECommerce/OrderServlet?method=callBack";
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

        StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
        sb.append("p0_Cmd=").append(p0_Cmd).append("&");
        sb.append("p1_MerId=").append(p1_MerId).append("&");
        sb.append("p2_Order=").append(p2_Order).append("&");
        sb.append("p3_Amt=").append(p3_Amt).append("&");
        sb.append("p4_Cur=").append(p4_Cur).append("&");
        sb.append("p5_Pid=").append(p5_Pid).append("&");
        sb.append("p6_Pcat=").append(p6_Pcat).append("&");
        sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
        sb.append("p8_Url=").append(p8_Url).append("&");
        sb.append("p9_SAF=").append(p9_SAF).append("&");
        sb.append("pa_MP=").append(pa_MP).append("&");
        sb.append("pd_FrpId=").append(pd_FrpId).append("&");
        sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
        sb.append("hmac=").append(hmac);

        System.out.println(sb.toString());
        // 使用重定向：
        response.sendRedirect(sb.toString());

//        Order order = orderService.payOrder(oid);
        return null;
    }
    public String callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String rb_BankId = request.getParameter("rb_BankId");
        String ro_BankOrderId = request.getParameter("ro_BankOrderId");
        String rp_PayDate = request.getParameter("rp_PayDate");
        String rq_CardNo = request.getParameter("rq_CardNo");
        String ru_Trxtime = request.getParameter("ru_Trxtime");

        // hmac
        String hmac = request.getParameter("hmac");
        // 利用本地密钥和加密算法 加密数据
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
        boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);
        if (isValid) {
            // 有效
            if (r9_BType.equals("1")) {

                OrderService orderService = new OrderServiceImpl();
                Order order = orderService.findOrderByOid(r6_Order);
                order.setState(2);
                orderService.updateOrder(order);

                request.setAttribute("msg",  "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
                return "/jsp/info.jsp";
            } else if (r9_BType.equals("2")) {
                // 修改订单状态:
                // 服务器点对点，来自于易宝的通知
                System.out.println("收到易宝通知，修改订单状态！");//
                // 回复给易宝success，如果不回复，易宝会一直通知
                response.getWriter().print("success");
            }

        } else {
            throw new RuntimeException("数据被篡改！");
        }
        return "/jsp/info.jsp";
    }

}
