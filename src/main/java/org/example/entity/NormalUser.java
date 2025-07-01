package org.example.entity;

import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.example.mapper.UserMapper;
import org.example.utils.MyBatisUtil;

import java.util.List;

@Data
public class NormalUser extends User {

    @Override
    public void post(String postContent, int userId, int directoryId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.insertPost(postContent, userId, directoryId);
            session.commit();
        }
    }

    @Override
    public void deletePost(int postId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.deletePostById(postId);
            session.commit();
        }
    }

    @Override
    public void comment(String content, int userId, int postId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.insertComment(content, userId, postId);
            session.commit();
        }
    }

    @Override
    public void deleteComment(int commentId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.deleteCommentById(commentId);
            session.commit();
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<Post> posts = userMapper.getPostsByUserId(userId);
            return posts != null ? posts : List.of();
        }
    }

    @Override
    public List<Comment> getAllComments() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<Comment> comments = userMapper.getCommentsByUserId(userId);
            return comments != null ? comments : List.of();
        }
    }

    public void LogoutFromDirectory(int directoryId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.LogoutFromDirectory(userId, directoryId);
            session.commit();
            System.out.println("Left directory successfully.");
        } catch (Exception e) {
            System.out.println("Failed to leave directory: " + e.getMessage());
        }
    }

    public boolean isBanned(int directoryId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            Boolean banned = userMapper.isBanned(userId, directoryId);
            return banned != null && banned;
        } catch (Exception e) {
            System.out.println("Error checking ban status: " + e.getMessage());
            return false;
        }
    }

    public boolean hasJoinedDirectory(int directoryId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.isUserInDirectory(userId, directoryId);
        }
    }

    public void joinDirectory(int directoryId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.joinDirectory(userId, directoryId);
            session.commit();
            System.out.println("Joined directory successfully.");
        } catch (Exception e) {
            System.out.println("Failed to join directory: " + e.getMessage());
        }
    }
}
