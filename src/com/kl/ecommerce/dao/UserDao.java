package com.kl.ecommerce.dao;

import com.kl.ecommerce.domain.User;

import java.sql.SQLException;

public interface UserDao {
    void userRegister(User user) throws SQLException;

    User userActive(String code) throws SQLException;

    void updateUser(User user) throws SQLException;

    User userLogin(User user)throws SQLException;
}
