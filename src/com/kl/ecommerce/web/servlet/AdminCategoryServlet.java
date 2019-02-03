package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.Category;
import com.kl.ecommerce.service.CategoryService;
import com.kl.ecommerce.service.serviceImpl.CategoryServiceImpl;
import com.kl.ecommerce.utils.UUIDUtils;
import com.kl.ecommerce.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminCategoryServlet", urlPatterns = "/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {

    /**
     * 获取到所有的分类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findAllCates(HttpServletRequest request, HttpServletResponse response) throws Exception {

        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> list = categoryService.getAllCates();
        request.setAttribute("allCates", list);
        return "/admin/category/list.jsp";
    }

    /**
     * 跳转到添加分类页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addCateUI(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return "/admin/category/add.jsp";
    }

    /**
     * 添加新分类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addCate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String cname = request.getParameter("cname");
        String cid = UUIDUtils.getId();
        Category category =  new Category(cname, cid);

        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.addCate(category);
        response.sendRedirect("/ECommerce/AdminCategoryServlet?method=findAllCates");
        return null;
    }
}
