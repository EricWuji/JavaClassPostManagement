package org.example.controller;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.Admin;
import org.example.entity.User;
import org.example.mapper.AdminMapper;
import org.example.mapper.UserMapper;
import org.example.utils.MyBatisUtil;

public class LoginController {

    public LoginController() {
        // 构造函数不再需要初始化 mappers
    }

    public User loginUser(String userName, String password) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            String passwordInSql = userMapper.getPasswordByUserName(userName);
            if (passwordInSql == null) {
                return null; // User not found
            }
            if (passwordInSql.equals(password)) {
                return userMapper.getUserByUserName(userName); // Successful login
            } else {
                return null; // Incorrect password
            }
        }
    }

    public Admin loginAdmin(String userName, String password) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            Admin admin = adminMapper.getAdminByUserName(userName);
            if (admin != null && admin.getPassword().equals(password)) {
                return admin; // Successful login
            } else {
                return null; // Admin not found or incorrect password
            }
        }
    }
}
