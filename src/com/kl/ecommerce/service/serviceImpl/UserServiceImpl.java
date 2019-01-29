package com.kl.ecommerce.service.serviceImpl;

import com.kl.ecommerce.dao.UserDao;
import com.kl.ecommerce.dao.daoImpl.UserDaoImpl;
import com.kl.ecommerce.domain.User;
import com.kl.ecommerce.service.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    @Override
    public void userRegister(User user) throws SQLException {
        UserDao userDao = new UserDaoImpl();
        userDao.userRegister(user);
    }

    @Override
    public boolean userActive(String code) throws SQLException {

        UserDao userDao = new UserDaoImpl();
        User user = userDao.userActive(code);

        if (user != null) {
            //修改用户状态
            user.setState(1);
            user.setCode(null);

            //用户状态更新到数据库
            userDao.updateUser(user);
            return true;
        }
        return false;
    }

    @Override
    public User userLogin(User user) throws SQLException {
        UserDao userDao = new UserDaoImpl();
        User user1 = userDao.userLogin(user);

        if (user1 == null) {
            throw new RuntimeException("账号或密码有误！");
        } else if (user1.getState() == 0) {
            throw new RuntimeException("用户未激活！");
        } else {
            return user1;
        }

    }
}
