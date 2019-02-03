package com.kl.ecommerce.test;

import com.kl.ecommerce.dao.UserDao;
import com.kl.ecommerce.domain.User;
import com.kl.ecommerce.utils.BeanFactory;
import com.kl.ecommerce.utils.JDBCUtils;
import com.kl.ecommerce.utils.MyBeanUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class UnitTest {

    @Test
    public void testJDBCUtil() throws SQLException {
        System.out.println(JDBCUtils.getConnection());
    }

    @Test
    public void testBeanUtil() throws Exception {
        Map<String, String[]> map = new HashMap<>();
        map.put("username", new String[] {"tom"});
        map.put("password", new String[] {"123"});
        map.put("birthday", new String[] {"1992-10-15"});

        User user =  new User();
        BeanUtils.populate(user, map);
        System.out.println(user);
    }
    //[94FAB5F9079547C289E02BC73F7C3535, Sun Jan 27 22:22:42 PST 2019, 4199.0, 1, null, null, null, 6B8C426C70584C2BAF9EF26F39387630]
    @Test
    public void testUpdateOrder() throws Exception{
        String sql = "insert into orders values (?,?,?,?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Date date = new Date(new java.util.Date().getTime());
        System.out.println(date);
        Object[] params = {
                "94FAB5F9079547C289E02BC73F7C3535",
                date,
                4199.0,
                1,
                null,
                null,
                null,
                "6B8C426C70584C2BAF9EF26F39387630",
        };
        queryRunner.update(sql, params);
    }
    @Test
    public void testBeanFactory() throws SQLException {
        UserDao userDao = (UserDao) BeanFactory.createDaoInstance("UserDao");
        User user = new User();
        user.setUsername("kingsley");
        user.setPassword("kingsley");
        User uu = userDao.userLogin(user);
        System.out.println(uu);
    }
}
