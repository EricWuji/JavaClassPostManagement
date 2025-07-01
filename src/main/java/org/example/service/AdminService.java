package org.example.service;

import org.example.entity.Admin;
import org.example.entity.NormalUser;
import org.example.entity.Post;

import java.util.List;
import java.util.Scanner;

public class AdminService extends Service {

    Admin user;

    public AdminService(Admin user) {
        super(user);
        this.user = user;
    }

    public void tryToPost(Scanner sc) {
        System.out.println("Enter the content of the post:");
        String postContent = sc.nextLine();
        if (postContent.isEmpty()) {
            System.out.println("Post content cannot be empty.");
            return;
        }
        user.post(postContent, user.getUserId(), user.getDirectoryId());
    }

    public void tryToBanUser(Scanner sc) {
        List<NormalUser> normalUsers = user.getAllUsersByDirectoryId(); // Now gets only unbanned users
        if (normalUsers.isEmpty()) {
            System.out.println("No unbanned users found in this directory.");
            return;
        }
        System.out.println("Unbanned users in directory " + user.getUserId() + ":");
        for (NormalUser normalUser : normalUsers) {
            System.out.println("User ID: " + normalUser.getUserId() + ", User Name: " + normalUser.getUserName());
        }
        System.out.println("Enter the user ID to ban:");
        int userIdToBan = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (normalUsers.stream().anyMatch(normalUser -> normalUser.getUserId() == userIdToBan)) {
            user.banUser(userIdToBan);
            System.out.println("User banned successfully.");
        } else {
            System.out.println("User not found or is already banned.");
        }
    }

    public void tryToUnbanUser(Scanner sc) {
        List<NormalUser> bannedNormalUsers = user.getBannedUsersByDirectoryId();
        if (bannedNormalUsers.isEmpty()) {
            System.out.println("No banned users found in this directory.");
            return;
        }
        System.out.println("Banned users in directory " + user.getDirectoryId() + ":");
        for (NormalUser normalUser : bannedNormalUsers) {
            System.out.println("User ID: " + normalUser.getUserId() + ", User Name: " + normalUser.getUserName());
        }
        System.out.println("Enter the user ID to unban:");
        int userIdToUnban = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (bannedNormalUsers.stream().anyMatch(normalUser -> normalUser.getUserId() == userIdToUnban)) {
            user.unbanUser(userIdToUnban);
            System.out.println("User unbanned successfully.");
        } else {
            System.out.println("User not found among banned users.");
        }
    }

    public void displayAllUsersByDirectoryId() {
        List<NormalUser> normalUsers = user.getAllUsersByDirectoryId();
        if (normalUsers.isEmpty()) {
            System.out.println("No users in this directory.");
        } else {
            System.out.println("Users in directory " + user.getDirectoryId() + ":");
            for (NormalUser normalUser : normalUsers) {
                System.out.println("User ID: " + normalUser.getUserId() + ", User Name: " + normalUser.getUserName());
            }
        }
    }

    public void unTopPost(Scanner sc) {
        List<Post> posts = user.getAllPostsByDirectoryId();
        if (posts.isEmpty()) {
            System.out.println("No posts in this directory.");
            return;
        }
        for (Post post : posts) {
            post.display(false);
        }
        System.out.println("Enter the post ID to untopped:");
        int postId = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (posts.stream().anyMatch(post -> post.getPostId() == postId)) {
            user.UntopPost(postId);
            System.out.println("Post untopped successfully.");
        } else {
            System.out.println("Post not found.");
        }
    }

    public void topPost(Scanner sc) {
        displayAllPosts(false);
        List<Post> posts = user.getAllPostsByDirectoryId();
        System.out.println("Enter the post ID to top:");
        int postId = sc.nextInt();
        sc.nextLine(); // Consume newline
        if (posts.stream().anyMatch(post -> post.getPostId() == postId)) {
            user.TopPost(postId);
            System.out.println("Post topped successfully.");
        } else {
            System.out.println("Post not found.");
        }
    }
}
