package org.example.controller;

import org.apache.ibatis.session.SqlSession;
import org.example.mapper.SignUpMapper;
import org.example.utils.MyBatisUtil;

import java.util.List;
import java.util.Scanner;

public class SignUpController {
    public static void signUp(Scanner sc) {
        try (SqlSession sqlSession = MyBatisUtil.getSession()) {
            SignUpMapper mapper = sqlSession.getMapper(SignUpMapper.class);
            List<String> allUserName = mapper.selectAllUserName();
            System.out.println("input your user name:");
            String userName = sc.nextLine();
            sc.nextLine();
            if (allUserName.stream().filter(name -> name.equals(userName)).count() > 0) {
                System.out.println("user name is already taken.");
                return ;
            }
            System.out.println("input your user password:");
            String password = sc.nextLine();
            sc.nextLine();
            mapper.insertByUserNameAndPassword(userName, password);
        }
    }
}
