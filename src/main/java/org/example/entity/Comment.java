package org.example.entity;

import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.example.mapper.AdminMapper;
import org.example.mapper.UserMapper;
import org.example.utils.MyBatisUtil;

@Data
public class Comment {
    String commentContent;
    int commentId;
    int postId;
    int userId;
    boolean fromAdmin;

    public void display() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            // Display logic for the comment
            System.out.println("-----------------------------");
            System.out.println("Comment ID: " + commentId);
            System.out.println("Content: " + commentContent);
            if (fromAdmin) {
                AdminMapper adminMapper = session.getMapper(AdminMapper.class);
                String adminName = adminMapper.getAdminNameById(userId);
                System.out.println("From Admin: " + adminName);
            } else {
                UserMapper userMapper = session.getMapper(UserMapper.class);
                String userName = userMapper.getUserNameById(userId);
                System.out.println("From User: " + userName);
            }
        }
    }
}
