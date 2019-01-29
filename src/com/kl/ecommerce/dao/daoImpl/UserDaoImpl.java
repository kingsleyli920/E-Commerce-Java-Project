package com.kl.ecommerce.dao.daoImpl;

import com.kl.ecommerce.dao.UserDao;
import com.kl.ecommerce.domain.User;
import com.kl.ecommerce.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    /**
     * 用户注册功能
     * @param user 用户对象
     * @throws SQLException
     */
    @Override
    public void userRegister(User user) throws SQLException {
        String sql = "INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {
                user.getUid(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getTelephone(),
                user.getBirthday(),
                user.getSex(),
                user.getState(),
                user.getCode()
        };
        qr.update(sql, params);
    }

    /**
     * 用户激活功能
     * @param code 激活码
     * @return
     */
    @Override
    public User userActive(String code) throws SQLException {
        String sql = "select * from user where code=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        User user = queryRunner.query(sql, new BeanHandler<User>(User.class), code);
        return user;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql = "update user set username=?, password=?, name=?, email=?, telephone=?, birthday=?, sex=?, state=?, code=? where uid=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getTelephone(),
                user.getBirthday(),
                user.getSex(),
                user.getState(),
                user.getCode(),
                user.getUid()
        };
        queryRunner.update(sql, params);
    }

    /**
     * 用户登录
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public User userLogin(User user) throws SQLException {
        String sql = "select * from user where username=? and password=?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
    }
}
