package org.example.controller;

import org.example.entity.Admin;
import org.example.entity.NormalUser;
import org.example.utils.UserUtils;

import java.util.Scanner;

public class LoginController {

    public NormalUser loginUser(Scanner sc) {
        System.out.println("Please enter your username:");
        String userName = sc.next();
        System.out.println("Please enter your password:");
        String password = sc.next();
        sc.nextLine(); // Consume newline
        return UserUtils.getUserByNameAndPassword(userName, password);
    }

    public static Admin loginAdmin(Scanner sc) {
        System.out.println("Please enter your admin username:");
        String adminUserName = sc.next();
        System.out.println("Please enter your admin password:");
        String adminPassword = sc.next();
        sc.nextLine(); // Consume newline
        return UserUtils.getAdminByNameAndPassword(adminUserName, adminPassword);
    }
}
