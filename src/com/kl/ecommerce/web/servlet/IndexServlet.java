package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.Category;
import com.kl.ecommerce.domain.Product;
import com.kl.ecommerce.domain.User;
import com.kl.ecommerce.service.CategoryService;
import com.kl.ecommerce.service.ProductService;
import com.kl.ecommerce.service.UserService;
import com.kl.ecommerce.service.serviceImpl.CategoryServiceImpl;
import com.kl.ecommerce.service.serviceImpl.ProductServiceImpl;
import com.kl.ecommerce.service.serviceImpl.UserServiceImpl;
import com.kl.ecommerce.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", urlPatterns = "/IndexServlet")
public class IndexServlet extends BaseServlet {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ProductService productService = new ProductServiceImpl();
        List<Product> hotProductList = productService.findHots();
        List<Product> newProductList = productService.findNews();

        //获取cookie中username和password
        Cookie cookies[] = request.getCookies();
        User user = new User();
        UserService userService = new UserServiceImpl();
        for (Cookie cookie: cookies
             ) {
            if (cookie.getName().equals("username")) {

                user.setUsername(cookie.getValue());
            }

            if (cookie.getName().equals("password")) {
                user.setPassword(cookie.getValue());
            }
        }
        System.out.println(user);
        if (user.getUsername() != null && user.getPassword() != null) {
            user = userService.userLogin(user);
            //登录成功
            request.getSession().setAttribute("loginUser", user);
        }
        request.setAttribute("hots", hotProductList);
        request.setAttribute("news", newProductList);
        return "/jsp/index.jsp";
    }
}
