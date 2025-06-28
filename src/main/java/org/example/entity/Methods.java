package org.example.entity;

import java.util.List;

public interface Methods {
    void post(String postContent, int userId, int directoryId);
    void deletePost(int postId);
    void comment(String content, int userId, int postId);
    void deleteComment(int commentId);
    List<Post> getAllPosts();
    List<Comment> getAllComments();
    default void displayAllPosts(boolean showComments) {
        List<Post> posts = getAllPosts();
        List<Post> topPosts = posts.stream().filter(post -> post.topped).toList();
        List<Post> normalPosts = posts.stream().filter(post -> !post.topped).toList();
        if (posts.isEmpty()) {
            System.out.println("No posts available.");
            return;
        }

        System.out.println("--- Top Posts ---");
        if (topPosts.isEmpty()) {
            System.out.println("No top posts available.");
        } else {
            for (Post post : topPosts) {
                post.display(showComments);
            }
        }
        System.out.println("\n--- Normal Posts ---");
        if (normalPosts.isEmpty()) {
            System.out.println("No normal posts available.");
        } else {
            for (Post post : normalPosts) {
                post.display(showComments);
            }
        }
    }
}
