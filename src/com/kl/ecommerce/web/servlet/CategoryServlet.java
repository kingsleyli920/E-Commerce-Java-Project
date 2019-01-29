package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.Category;
import com.kl.ecommerce.service.CategoryService;
import com.kl.ecommerce.service.serviceImpl.CategoryServiceImpl;
import com.kl.ecommerce.utils.JedisUtils;
import com.kl.ecommerce.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {

    /**
     * 获取全部分类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String  getAllCates(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Jedis jedis = JedisUtils.getJedis();
        String jsonStr = jedis.get("allCates");
        if (jsonStr == null || jsonStr.equals("")) {
            CategoryService categoryService = new CategoryServiceImpl();
            List<Category> categoryList = categoryService.getAllCates();
            jsonStr = JSONArray.fromObject(categoryList).toString();
            jedis.append("allCates", jsonStr);
            System.out.println("redis no data");
        }

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(jsonStr);

        JedisUtils.closeJedis(jedis);
        return null;
    }
}
