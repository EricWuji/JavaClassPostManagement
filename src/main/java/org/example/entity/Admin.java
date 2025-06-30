package org.example.entity;

import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.example.mapper.AdminMapper;
import org.example.utils.MyBatisUtil;

import java.util.List;

@Data
public class Admin extends User {

    int directoryId;

    @Override
    public void post(String postContent, int userId, int directoryId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            adminMapper.insertPostFromAdmin(postContent, userId, directoryId);
            session.commit();
        }
    }

    @Override
    public void deletePost(int postId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            adminMapper.deletePostById(postId);
            session.commit();
        }
    }

    @Override
    public void comment(String content, int userId, int postId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            adminMapper.insertCommentFromAdmin(content, userId, postId);
            session.commit();
        }
    }

    @Override
    public void deleteComment(int commentId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            adminMapper.deleteCommentById(commentId);
            session.commit();
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            List<Post> posts = adminMapper.getPostsByDirectoryId(directoryId);
            return posts != null ? posts : List.of();
        }
    }

    @Override
    public List<Comment> getAllComments() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            List<Comment> comments = adminMapper.getCommentsByUserId(userId);
            return comments != null ? comments : List.of();
        }
    }

    public void TopPost(int postId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            adminMapper.topPost(postId); // Assuming this method toggles the "topped" status
            session.commit();
        }
    }

    public void UntopPost(int postId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            adminMapper.UntopPost(postId); // Assuming this method toggles the "topped" status
            session.commit();
        }
    }

    public void banUser(int userId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            adminMapper.banUser(userId);
            session.commit();
        }
    }

    public void unbanUser(int userId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            adminMapper.unbanUser(userId);
            session.commit();
        }
    }

    public List<NormalUser> getAllUsersByDirectoryId() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            List<NormalUser> normalUsers = adminMapper.getAllUnbannedUsers(directoryId);
            return normalUsers != null ? normalUsers : List.of();
        }
    }

    public List<NormalUser> getBannedUsersByDirectoryId() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            List<NormalUser> normalUsers = adminMapper.getBannedUsersByDirectoryId(directoryId);
            return normalUsers != null ? normalUsers : List.of();
        }
    }

    public List<Post> getAllPostsByDirectoryId() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            List<Post> posts = adminMapper.getAllPostsByDirectoryId(directoryId);
            return posts != null ? posts : List.of();
        }
    }
}
