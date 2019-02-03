package com.kl.ecommerce.web.filter;

import com.kl.ecommerce.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "PriviledgeFilter")
public class PriviledgeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
       HttpServletRequest myReq =  (HttpServletRequest) req;
       User user = (User)myReq.getSession().getAttribute("loginUser");
       if (user != null) {
           chain.doFilter(req, resp);
       } else {
           myReq.setAttribute("msg", "请登录后再访问");
           myReq.getRequestDispatcher("/jsp/info.jsp").forward(req, resp);
       }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
