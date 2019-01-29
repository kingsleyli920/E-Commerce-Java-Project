package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.Cart;
import com.kl.ecommerce.domain.CartItem;
import com.kl.ecommerce.domain.Product;
import com.kl.ecommerce.service.ProductService;
import com.kl.ecommerce.service.serviceImpl.ProductServiceImpl;
import com.kl.ecommerce.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CartServlet", urlPatterns = "/CartServlet")
public class CartServlet extends BaseServlet {

    /**
     * 添加物品到购物车
     *
     * @param request
     * @param response
     * @return
     */
    public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        String pid = request.getParameter("pid");
        int num = Integer.parseInt(request.getParameter("quantity"));

        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProductByPid(pid);

        CartItem cartItem = new CartItem(product, num);

        cart.addCartItem(cartItem);

        response.sendRedirect("/ECommerce/jsp/cart.jsp");
        return null;
    }

    /**
     * 删除购物车某个商品
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String removeCartItemById(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String pid = request.getParameter("pid");
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.removeCartItem(pid);
        response.sendRedirect("/ECommerce/jsp/cart.jsp");
        return null;
    }

    public String clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.clearCart();
        response.sendRedirect("/ECommerce/jsp/cart.jsp");
        return null;
    }
}
