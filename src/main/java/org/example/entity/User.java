package org.example.entity;

import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.example.mapper.UserMapper;
import org.example.utils.MyBatisUtil;

import java.util.List;

@Data
public class User extends AbstractUser implements Methods {

    public User() {
        // 构造函数不再需要初始化 mapper
    }

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

    boolean isBanned(int directoryId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            Boolean banned = userMapper.isBanned(userId, directoryId);
            return banned != null && banned;
        } catch (Exception e) {
            System.out.println("Error checking ban status: " + e.getMessage());
            return false;
        }
    }

    boolean hasJoinedDirectory(int directoryId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            return userMapper.isUserInDirectory(userId, directoryId);
        }
    }

    public void TryToPost(String postContent, int directoryId) {
        if (postContent == null || postContent.isEmpty()) {
            System.out.println("Post content cannot be empty.");
            return;
        }
        if (!hasJoinedDirectory(directoryId)) {
            System.out.println("You must join the directory before you can post.");
            return;
        }
        if (isBanned(directoryId)) {
            System.out.println("You are banned from posting in this directory.");
            return;
        }
        post(postContent, userId, directoryId);
        System.out.println("Post created successfully.");
    }

    public void TryToComment(String content, int postId) {
        if (content == null || content.isEmpty()) {
            System.out.println("Comment content cannot be empty.");
            return;
        }
        comment(content, userId, postId);
    }

   public void tryToDeletePost(int postId) {
        if (postId <= 0) {
            System.out.println("Invalid post ID.");
            return;
        }
        // Verify ownership before deleting
        List<Post> userPosts = getAllPosts();
        if (userPosts.stream().anyMatch(p -> p.getPostId() == postId)) {
            deletePost(postId);
            System.out.println("Post deleted successfully.");
        } else {
            System.out.println("Post not found or you don't have permission to delete it.");
        }
    }

    public void tryToDeleteComment(int commentId) {
        if (commentId <= 0) {
            System.out.println("Invalid comment ID.");
            return;
        }
        // Verify ownership before deleting
        List<Comment> userComments = getAllComments();
        if (userComments.stream().anyMatch(c -> c.getCommentId() == commentId)) {
            deleteComment(commentId);
            System.out.println("Comment deleted successfully.");
        } else {
            System.out.println("Comment not found or you don't have permission to delete it.");
        }
    }

    public void displayAllComments() {
        List<Comment> comments = getAllComments();
        if (comments.isEmpty()) {
            System.out.println("You have not made any comments.");
        } else {
            System.out.println("--- Your Comments ---");
            for (Comment comment : comments) {
                System.out.println("Comment ID: " + comment.getCommentId() + " | Content: " + comment.getCommentContent());
            }
            System.out.println("---------------------");
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
