package org.example;

import org.example.controller.LoginController;
import org.example.entity.Admin;
import org.example.entity.User;
import org.example.filter.Filter;
import org.example.utils.DirectoryUtils;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginController loginController = new LoginController();

        while (true) {
            System.out.println("Welcome to the Social Media Application!");
            System.out.println("1. you are a user\n 2. you are an admin\n 3. exit");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("Please enter your username:");
                    String userName = sc.next();
                    System.out.println("Please enter your password:");
                    String password = sc.next();
                    sc.nextLine(); // Consume newline
                    User user = loginController.loginUser(userName, password);
                    if (user != null) {
                        System.out.println("Login successful! Welcome, " + userName + "!");
                        userFunction(user, sc); // Proceed to user functionalities
                    } else {
                        System.out.println("Login failed. Please check your username and password.");
                    }
                }
                case 2 -> {
                    System.out.println("Please enter your admin username:");
                    String adminUserName = sc.next();
                    System.out.println("Please enter your admin password:");
                    String adminPassword = sc.next();
                    sc.nextLine(); // Consume newline
                    Admin admin = loginController.loginAdmin(adminUserName, adminPassword);
                    if (admin != null) {
                        System.out.println("Admin login successful! Welcome, " + adminUserName + "!");
                        adminFunction(admin, sc); // Proceed to admin functionalities
                    } else {
                        System.out.println("Admin login failed. Please check your username and password.");
                    }
                }
                case 3 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void userFunction(User user, Scanner sc) {
        while (true) {
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("User Menu:");
            System.out.println("1. View Posts");
            System.out.println("2. Create Post");
            System.out.println("3. Delete Post");
            System.out.println("4. Comment on Post");
            System.out.println("5. Delete Comment");
            System.out.println("6. join a Directory");
            System.out.println("7. Exit");
            int userChoice = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (userChoice) {
                case 1 -> {
                    // Logic to view posts
                    System.out.println("Viewing posts...");
                    // Call method to fetch and display posts
                    user.displayAllPosts(true);
                }
                case 2 -> {
                    // Logic to create a post
                    System.out.println("Enter post content:");
                    String postContent = sc.nextLine();
                    postContent = Filter.filter(postContent);
                    System.out.println("Enter directory name:");
                    String directoryName = sc.nextLine();
                    Integer directoryId = DirectoryUtils.getDirectoryIdByName(directoryName);
                    if (directoryId != null) {
                        user.TryToPost(postContent, directoryId);
                    } else {
                        System.out.println("Directory not found.");
                    }
                }
                case 3 -> {
                    // Logic to delete a post
                    user.displayAllPosts(false);
                    System.out.println("Enter post ID to delete:");
                    int postId = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    user.tryToDeletePost(postId);
                }
                case 4 -> {
                    // Logic to comment on a post
                    user.displayAllPosts(false);
                    System.out.println("Enter post ID to comment on:");
                    int postId = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter your comment:");
                    String content = sc.nextLine();
                    content = Filter.filter(content);
                    user.TryToComment(content, postId);
                }
                case 5 -> {
                    // Logic to delete a comment
                    user.displayAllComments();
                    System.out.println("Enter comment ID to delete:");
                    int commentId = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    user.tryToDeleteComment(commentId);
                }
                case 6 -> {
                    System.out.println("Enter directory name to join:");
                    String directoryName = sc.nextLine();
                    Integer directoryId = DirectoryUtils.getDirectoryIdByName(directoryName);
                    if (directoryId != null) {
                        user.joinDirectory(directoryId);
                    } else {
                        System.out.println("Directory not found.");
                    }
                }
                case 7 -> {
                    // Exit user menu
                    System.out.println("Exiting user menu. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void adminFunction(Admin admin, Scanner sc) {
        System.out.println(admin);
        while (true) {
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("Admin Menu:");
            System.out.println("1. View All Posts");
            System.out.println("2. Create Post");
            System.out.println("3. Delete Post");
            System.out.println("4. Comment on Post");
            System.out.println("5. Delete Comment");
            System.out.println("6. Ban User");
            System.out.println("7. View All Users in Directory");
            System.out.println("8. Top Post");
            System.out.println("9. Unban User");
            System.out.println("10. Exit");
            int adminChoice = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (adminChoice) {
                case 1 -> {
                    // Logic to view all posts
                    admin.displayAllPosts();
                }
                case 2 -> {
                    // Logic to create a post
                    System.out.println("Enter post content:");
                    String postContent = sc.nextLine();
                    System.out.println("Enter directory name:");
                    String directoryName = sc.nextLine();
                    Integer directoryId = DirectoryUtils.getDirectoryIdByName(directoryName);
                    if (directoryId != null) {
                        admin.post(postContent, admin.getUserId(), directoryId);
                        System.out.println("Post created successfully.");
                    } else {
                        System.out.println("Directory not found.");
                    }
                }
                case 3 -> // Logic to delete a post
                        admin.tryToDeletePost(sc);
                case 4 -> {
                    // Logic to comment on a post
                    admin.displayAllPosts();
                    System.out.println("Enter post ID to comment on:");
                    int postId = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter your comment:");
                    String content = sc.nextLine();
                    admin.comment(content, admin.getUserId(), postId);
                }
                case 5 -> // Logic to delete a comment
                        admin.tryToDeleteComment(sc);
                case 6 -> // Logic to ban a user
                        admin.tryToBanUser(sc);
                case 7 -> // Logic to view all users in directory
                        admin.displayAllUsersByDirectoryId();
                case 8 -> // Logic to top a post
                        admin.tryToTopPost(sc);
                case 9 -> admin.tryToUnbanUser(sc);
                case 10 -> {
                    // Exit admin menu
                    System.out.println("Exiting admin menu. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}