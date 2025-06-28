package org.example.entity;

import lombok.Data;
import org.apache.ibatis.session.SqlSession;
import org.example.mapper.AdminMapper;
import org.example.utils.MyBatisUtil;

import java.util.List;
import java.util.Scanner;

@Data
public class Admin extends AbstractUser implements Methods {

    int directoryId;

    public Admin() {
        // 构造函数不再需要初始化 mapper
    }

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
            List<Post> posts = adminMapper.getPostsByDirectoryIdAndUserId(directoryId, userId);
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

    public List<Comment> getAllCommentsInDirectory() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            List<Comment> comments = adminMapper.getAllCommentsInDirectory(directoryId);
            return comments != null ? comments : List.of();
        }
    }

    public void topPost(int postId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            adminMapper.topPost(postId);
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

    public List<User> getAllUsersByDirectoryId() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            List<User> users = adminMapper.getAllUnbannedUsers(directoryId);
            return users != null ? users : List.of();
        }
    }

    public List<User> getBannedUsersByDirectoryId() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            List<User> users = adminMapper.getBannedUsersByDirectoryId(directoryId);
            return users != null ? users : List.of();
        }
    }

    public void displayAllPosts() {
        List<Post> posts = getAllPostsByDirectoryId();
        List<Post> topPosts = posts.stream().filter(post -> post.topped).toList();
        List<Post> normalPosts = posts.stream().filter(post -> !post.topped).toList();
        if (posts.isEmpty()) {
            System.out.println("No posts available in this directory.");
        } else {
            System.out.println("Top Posts:");
            if (topPosts.isEmpty()) {
                System.out.println("No top posts available.");
            } else {
                for (Post post : topPosts) {
                    post.display(true);
                }
            }
            System.out.println("Normal Posts:");
            if (normalPosts.isEmpty()) {
                System.out.println("No normal posts available.");
            } else {
                for (Post post : normalPosts) {
                    post.display(true);
                }
            }
        }
    }

    public List<Post> getAllPostsByDirectoryId() {
        try (SqlSession session = MyBatisUtil.getSession()) {
            AdminMapper adminMapper = session.getMapper(AdminMapper.class);
            List<Post> posts = adminMapper.getAllPostsByDirectoryId(directoryId);
            return posts != null ? posts : List.of();
        }
    }

    public void tryToDeletePost(Scanner sc) {
        List<Post> posts = getAllPostsByDirectoryId();
        for (Post post : posts) {
            post.display(false);
        }
        System.out.println("Enter the post ID to delete:");
        int postId = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (posts.stream().anyMatch(post -> post.postId == postId)) {
            deletePost(postId);
            System.out.println("Post deleted successfully.");
        } else {
            System.out.println("Post not found.");
        }
    }

    public void tryToDeleteComment(Scanner sc) {
        List<Comment> comments = getAllCommentsInDirectory();
        if (comments.isEmpty()) {
            System.out.println("No comments found in this directory.");
            return;
        }

        for (Comment comment : comments) {
            comment.display();
        }
        System.out.println("Enter the comment ID to delete:");
        int commentId = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (comments.stream().anyMatch(comment -> comment.commentId == commentId)) {
            deleteComment(commentId);
            System.out.println("Comment deleted successfully.");
        } else {
            System.out.println("Comment not found in this directory.");
        }
    }

    public void tryToBanUser(Scanner sc) {
        List<User> users = getAllUsersByDirectoryId(); // Now gets only unbanned users
        if (users.isEmpty()) {
            System.out.println("No unbanned users found in this directory.");
            return;
        }
        System.out.println("Unbanned users in directory " + directoryId + ":");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserId() + ", User Name: " + user.getUserName());
        }
        System.out.println("Enter the user ID to ban:");
        int userIdToBan = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (users.stream().anyMatch(user -> user.getUserId() == userIdToBan)) {
            banUser(userIdToBan);
            System.out.println("User banned successfully.");
        } else {
            System.out.println("User not found or is already banned.");
        }
    }

    public void tryToUnbanUser(Scanner sc) {
        List<User> bannedUsers = getBannedUsersByDirectoryId();
        if (bannedUsers.isEmpty()) {
            System.out.println("No banned users found in this directory.");
            return;
        }
        System.out.println("Banned users in directory " + directoryId + ":");
        for (User user : bannedUsers) {
            System.out.println("User ID: " + user.getUserId() + ", User Name: " + user.getUserName());
        }
        System.out.println("Enter the user ID to unban:");
        int userIdToUnban = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (bannedUsers.stream().anyMatch(user -> user.getUserId() == userIdToUnban)) {
            unbanUser(userIdToUnban);
            System.out.println("User unbanned successfully.");
        } else {
            System.out.println("User not found among banned users.");
        }
    }

    public void tryToTopPost(Scanner sc) {
        List<Post> posts = getAllPostsByDirectoryId();
        for (Post post : posts) {
            post.display(false);
        }
        System.out.println("Enter the post ID to top:");
        int postId = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (posts.stream().anyMatch(post -> post.postId == postId)) {
            topPost(postId);
            System.out.println("Post topped successfully.");
        } else {
            System.out.println("Post not found.");
        }
    }

    public void displayAllUsersByDirectoryId() {
        List<User> users = getAllUsersByDirectoryId();
        if (users.isEmpty()) {
            System.out.println("No users in this directory.");
        } else {
            System.out.println("Users in directory " + directoryId + ":");
            for (User user : users) {
                System.out.println("User ID: " + user.getUserId() + ", User Name: " + user.getUserName());
            }
        }
    }
}
