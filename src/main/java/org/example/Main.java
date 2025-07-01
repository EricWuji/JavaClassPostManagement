package org.example;

import org.example.controller.LoginController;
import org.example.controller.SignUpController;
import org.example.entity.Admin;
import org.example.entity.NormalUser;
import org.example.service.AdminService;
import org.example.service.UserService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginController loginController = new LoginController();

        while (true) {
            System.out.println("Welcome to the Social Media Application!");
            System.out.println("1. you are a user\n 2. you are an admin\n 3. sign up\n 4. exit");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    NormalUser normalUser = loginController.loginUser(sc);
                    if (normalUser != null) {
                        System.out.println("Login successful! Welcome, " + normalUser.getUserName() + "!");
                        userFunction(normalUser, sc); // Proceed to user functionalities
                    } else {
                        System.out.println("Login failed. Please check your username and password.");
                    }
                }
                case 2 -> {
                    Admin admin = LoginController.loginAdmin(sc);
                    if (admin != null) {
                        System.out.println("Admin login successful! Welcome, " + admin.getUserName() + "!");
                        adminFunction(admin, sc); // Proceed to admin functionalities
                    } else {
                        System.out.println("Admin login failed. Please check your username and password.");
                    }
                }
                case 3 -> SignUpController.signUp(sc);
                case 4 -> {
                    System.out.println("Exiting the application. Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void userFunction(NormalUser normalUser, Scanner sc) {
        UserService userService = new UserService(normalUser);
        while (true) {
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("User Menu:");
            System.out.println("1. View Posts");
            System.out.println("2. Create Post");
            System.out.println("3. Delete Post");
            System.out.println("4. Comment on Post");
            System.out.println("5. Delete Comment");
            System.out.println("6. join a Directory");
            System.out.println("7. logout from Directory");
            System.out.println("8. Exit");
            int userChoice = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (userChoice) {
                case 1 -> // Logic to view posts
                        userService.displayAllPosts(true);
                case 2 -> // Logic to create a posts
                        userService.tryToPost(sc);
                case 3 -> // Logic to delete a post
                        userService.tryToDeletePost(sc);
                case 4 -> // Logic to comment on a post
                        userService.TryToComment(sc);
                case 5 -> // Logic to delete a comment
                        userService.tryToDeleteComment(sc);
                case 6 -> userService.joinDirectory(sc);
                case 7 -> // logout from the directory
                        userService.LogoutFromDirectory(sc);
                case 8 -> {
                    // Exit user menu
                    System.out.println("Exiting user menu. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void adminFunction(Admin admin, Scanner sc) {
        AdminService adminService = new AdminService(admin);
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
            System.out.println("10. Untop post");
            System.out.println("11. Exit Admin Menu");
            int adminChoice = sc.nextInt();
            sc.nextLine(); // Consume newline
            switch (adminChoice) {
                case 1 -> // Logic to view all posts
                        adminService.displayAllPosts(true);
                case 2 -> // Logic to create a post
                        adminService.tryToPost(sc);
                case 3 -> // Logic to delete a post
                        adminService.tryToDeletePost(sc);
                case 4 -> adminService.TryToComment(sc);
                case 5 -> adminService.tryToDeleteComment(sc);
                case 6 -> // Logic to ban a user
                        adminService.tryToBanUser(sc);
                case 7 -> // Logic to view all users in directory
                        adminService.displayAllUsersByDirectoryId();
                case 8 -> // Logic to top a post
                        adminService.topPost(sc); // Assuming this method remains on Admin entity for now
                case 9 -> adminService.tryToUnbanUser(sc);
                case 10 -> adminService.unTopPost(sc);
                case 11 -> {
                    // Exit admin menu
                    System.out.println("Exiting admin menu. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}