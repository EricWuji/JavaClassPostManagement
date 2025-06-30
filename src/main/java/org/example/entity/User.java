package org.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    String userName;
    String password;
    int userId;

    public void post(String postContent, int userId, int directoryId) {

    }

    public void deletePost(int postId) {

    }

    public void comment(String content, int userId, int postId) {

    }

    public void deleteComment(int commentId) {

    }

    public List<Post> getAllPosts() {
        return null;
    }

    public List<Comment> getAllComments() {
        return null;
    }
}
