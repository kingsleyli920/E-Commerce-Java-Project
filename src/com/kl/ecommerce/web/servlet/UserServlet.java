package com.kl.ecommerce.web.servlet;

import com.kl.ecommerce.domain.User;
import com.kl.ecommerce.service.UserService;
import com.kl.ecommerce.service.serviceImpl.UserServiceImpl;
import com.kl.ecommerce.utils.MailUtils;
import com.kl.ecommerce.utils.MyBeanUtils;
import com.kl.ecommerce.utils.UUIDUtils;
import com.kl.ecommerce.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * User servlet
 */
@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {
    /**
     * Forward to register page
     *
     * @param request
     * @param response
     * @return register page url
     * @throws ServletException
     */
    public String registerPage(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        return "/jsp/register.jsp";
    }

    /**
     * 实现用户注册
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public String userRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        // Get parameters and create User instance
        Map<String, String[]> map = request.getParameterMap();

        User user = new User();
        Class clazz = user.getClass();
        try {
            MyBeanUtils.populate(user, map);
            //给用户其他属性赋值
            user.setUid(UUIDUtils.getId());
            user.setState(0);
            user.setCode(UUIDUtils.getCode());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            UserService userService = new UserServiceImpl();
            userService.userRegister(user);
            //发送激活邮件
            MailUtils.sendMail(user.getEmail(), user.getCode());
            request.setAttribute("msg", "用户注册成功，请激活！");
        } catch (Exception e) {
            request.setAttribute("msg", "用户注册失败，请重新注册！");
        }
        return "/jsp/info.jsp";
    }

    /**
     * 实现用户激活
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        String code = request.getParameter("code");

        UserService userService = new UserServiceImpl();
        boolean flag = false;
        try {
            flag = userService.userActive(code);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (flag) {
            //用户激活成功
            request.setAttribute("msg", "用户激活成功，请登录");
            return "/jsp/login.jsp";
        } else {
            //用户激活成功
            request.setAttribute("msg", "用户激活失败，请重新激活");
            return "/jsp/info.jsp";
        }
    }

    /**
     * Forward to login page
     *
     * @param request
     * @param response
     * @return register page url
     * @throws ServletException
     */
    public String loginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        return "/jsp/login.jsp";
    }


    /**
     * 实现用户登录
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public String userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        User user = new User();
        MyBeanUtils.populate(user, request.getParameterMap());
        String autoLogin = request.getParameter("autoLogin");
        String rememberUsername = request.getParameter("rememberUsername");
        System.out.println(autoLogin + ":" +rememberUsername);
        UserService userService = new UserServiceImpl();
        User user1 = null;
        try {
            user1 = userService.userLogin(user);
            //登录成功
            request.getSession().setAttribute("loginUser", user1);
            if (autoLogin != null && rememberUsername == null) {
                Cookie cookie1 = new Cookie("username", user.getUsername());
                Cookie cookie2 = new Cookie("password", user.getPassword());
                cookie1.setMaxAge(60*60*24*30);
                cookie2.setMaxAge(60*60*24*30);
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }

            response.sendRedirect("/ECommerce/index.jsp");
            return null;
        } catch (Exception e) {
            //登录失败
            String msg = e.getMessage();
            System.out.println(e);
            request.setAttribute("msg", msg);
            return "/jsp/login.jsp";
        }



    }

    /**
     * 退出用户
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username") || cookie.getName().equals("password")) {
                cookie.setMaxAge(0);
                cookie.setValue("");
                response.addCookie(cookie);
            }
        }
        response.sendRedirect("/ECommerce/index.jsp");
        return null;
    }

}
