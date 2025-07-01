package org.example.entity;

import lombok.Data;

import java.util.List;

@Data
public abstract class User {
    String userName;
    String password;
    int userId;

    public abstract void post(String postContent, int userId, int directoryId);

    public abstract void deletePost(int postId);

    public abstract void comment(String content, int userId, int postId);

    public abstract void deleteComment(int commentId);

    public abstract List<Post> getAllPosts();

    public abstract List<Comment> getAllComments();
}
