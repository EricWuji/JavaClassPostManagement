package org.example.entity;

import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.example.mapper.AdminMapper;
import org.example.mapper.PostMapper;
import org.example.mapper.UserMapper;
import org.example.utils.MyBatisUtil;

import java.util.List;

@Data
public class Post {
    int postId;
    String postContent;
    int userId;
    int directoryId;
    boolean topped;
    boolean fromAdmin;

    public void display(boolean showComments) {
        try (SqlSession sqlSession = MyBatisUtil.getSession()) {
            PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            AdminMapper adminMapper = sqlSession.getMapper(AdminMapper.class);
            List<Comment> commentList = postMapper.getCommentsByPostId(postId);
            System.out.println("-----------------------------");
            System.out.println("Post ID: " + postId);
            System.out.println("Content: " + postContent);
            if (fromAdmin) {
                System.out.println("From Admin: " + adminMapper.getAdminNameById(userId));
            } else {
                System.out.println("From User: " + userMapper.getUserNameById(userId));
            }
            if (showComments) {
                if (commentList.isEmpty()) {
                    System.out.println("No comments available.");
                } else {
                    System.out.println("Comments:");
                    for (Comment comment : commentList) {
                        comment.display();
                    }
                }
            }
        }
    }
}
