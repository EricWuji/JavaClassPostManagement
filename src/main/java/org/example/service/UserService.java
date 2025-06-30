package org.example.service;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.NormalUser;
import org.example.mapper.UserMapper;
import org.example.utils.MyBatisUtil;

public class UserService extends Service {

    NormalUser user;

    public UserService(NormalUser user) {
        super(user);
        this.user = user;
    }

    public void tryToPost(String postContent, int directoryId) {
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

    public void joinDirectory(int directoryId) {
        try (SqlSession session = MyBatisUtil.getSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            userMapper.joinDirectory(user.getUserId(), directoryId);
            session.commit();
            System.out.println("Joined directory successfully.");
        } catch (Exception e) {
            System.out.println("Failed to join directory: " + e.getMessage());
        }
    }

    public void LogoutFromDirectory(int directoryId) {
        if (!user.hasJoinedDirectory(directoryId)) {
            System.out.println("You are not a member of this directory.");
            return;
        }
        user.LogoutFromDirectory(directoryId);
    }
}
