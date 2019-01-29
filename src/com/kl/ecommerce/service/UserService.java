package com.kl.ecommerce.service;

import com.kl.ecommerce.domain.User;

import java.sql.SQLException;

public interface UserService {

    void userRegister(User user) throws SQLException;

    boolean userActive(String code) throws SQLException;

    User userLogin(User user) throws SQLException;
}
