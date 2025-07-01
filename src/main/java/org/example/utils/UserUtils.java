package org.example.utils;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.Admin;
import org.example.entity.NormalUser;
import org.example.mapper.AdminMapper;
import org.example.mapper.UserMapper;

public class UserUtils {
    public static NormalUser getUserByNameAndPassword(String userName, String password) {
        try (SqlSession sqlSession = MyBatisUtil.getSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.getUserByNameAndPassword(userName, password);
        }
    }

    public static Admin getAdminByNameAndPassword(String adminName, String adminPassword) {
        try (SqlSession sqlSession = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = sqlSession.getMapper(AdminMapper.class);
            return adminMapper.getAdminByNameAndPassword(adminName, adminPassword);
        }
    }
}
