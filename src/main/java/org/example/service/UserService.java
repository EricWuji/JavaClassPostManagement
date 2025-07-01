package org.example.service;

import org.example.entity.NormalUser;
import org.example.filter.Filter;
import org.example.utils.DirectoryUtils;

import java.util.Scanner;

public class UserService extends Service {

    NormalUser user;

    public UserService(NormalUser user) {
        super(user);
        this.user = user;
    }

    public void tryToPost(Scanner sc) {
        System.out.println("Enter post content:");
        String postContent = sc.nextLine();
        postContent = Filter.filter(postContent);
        System.out.println("Enter directory name:");
        String directoryName = sc.nextLine();
        Integer directoryId = DirectoryUtils.getDirectoryIdByName(directoryName);
        if (directoryId == null) {
            System.out.println("Directory not found.");
            return ;
        }
        if (postContent == null || postContent.isEmpty()) {
            System.out.println("Post content cannot be empty.");
            return;
        }
        if (!user.hasJoinedDirectory(directoryId)) {
            System.out.println("You must join the directory before you can post.");
            return;
        }
        if (user.isBanned(directoryId)) {
            System.out.println("You are banned from posting in this directory.");
            return;
        }
        user.post(postContent, user.getUserId(), directoryId);
        System.out.println("Post created successfully.");
    }

    public void joinDirectory(Scanner sc) {
        System.out.println("Enter directory name to join:");
        String directoryName = sc.nextLine();
        Integer directoryId = DirectoryUtils.getDirectoryIdByName(directoryName);
        if (directoryId != null) {
            user.joinDirectory(directoryId);
        } else {
            System.out.println("Directory not found.");
        }
    }

    public void LogoutFromDirectory(Scanner sc) {
        System.out.println("Enter directory name to logout:");
        String directoryName = sc.nextLine();
        Integer directoryId = DirectoryUtils.getDirectoryIdByName(directoryName);
        if (directoryId == null) {
            System.out.println("Directory not found");
            return ;
        }
        if (!user.hasJoinedDirectory(directoryId)) {
            System.out.println("You are not a member of this directory.");
            return;
        }
        user.LogoutFromDirectory(directoryId);
    }
}
