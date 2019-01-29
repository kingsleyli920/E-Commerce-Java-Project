package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.PageModel;
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

@WebServlet(name = "ProductServlet", urlPatterns = "/ProductServlet")
public class ProductServlet extends BaseServlet {

    /**
     * 根据商品ID获取到商品信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pid = request.getParameter("pid");
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProductByPid(pid);

        request.setAttribute("product", product);
        return "/jsp/product_info.jsp";

    }

    /**
     * 点击分类进入分类商品列表
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String cid = request.getParameter("cid");
        int curNum = Integer.parseInt(request.getParameter("num"));
        System.out.println(cid+" : cid");
        ProductService productService = new ProductServiceImpl();
        PageModel pageModel = productService.findProductsByCidWithPage(cid, curNum);
        System.out.println(pageModel);
        request.setAttribute("pageModel", pageModel);
        return "/jsp/product_list.jsp";
    }

}
