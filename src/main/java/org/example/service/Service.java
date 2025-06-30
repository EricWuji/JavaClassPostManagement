package org.example.service;

import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.entity.User;
import java.util.List;

public class Service {
    User user;

    Service(User user) {
        this.user = user;
    }

    public void TryToComment(String content, int postId) {
        if (content == null || content.isEmpty()) {
            System.out.println("Comment content cannot be empty.");
            return;
        }
        user.comment(content, user.getUserId(), postId);
    }

    public void tryToDeletePost(int postId) {
        if (postId <= 0) {
            System.out.println("Invalid post ID.");
            return;
        }
        // Verify ownership before deleting
        List<Post> userPosts = user.getAllPosts();
        if (userPosts.stream().anyMatch(p -> p.getPostId() == postId)) {
            user.deletePost(postId);
            System.out.println("Post deleted successfully.");
        } else {
            System.out.println("Post not found or you don't have permission to delete it.");
        }
    }

    public void tryToDeleteComment(int commentId) {
        if (commentId <= 0) {
            System.out.println("Invalid comment ID.");
            return;
        }
        // Verify ownership before deleting
        List<Comment> userComments = user.getAllComments();
        userComments.forEach(Comment::display);
        if (userComments.stream().anyMatch(c -> c.getCommentId() == commentId)) {
            user.deleteComment(commentId);
            System.out.println("Comment deleted successfully.");
        } else {
            System.out.println("Comment not found or you don't have permission to delete it.");
        }
    }

    public void displayAllComments() {
        List<Comment> comments = user.getAllComments();
        if (comments.isEmpty()) {
            System.out.println("You have not made any comments.");
        } else {
            System.out.println("--- Your Comments ---");
            for (Comment comment : comments) {
                System.out.println("Comment ID: " + comment.getCommentId() + " | Content: " + comment.getCommentContent());
            }
            System.out.println("---------------------");
        }
    }

    public void displayAllPosts(boolean showComments) {
        List<Post> posts = user.getAllPosts();
        List<Post> topPosts = posts.stream().filter(Post::isTopped).toList();
        List<Post> normalPosts = posts.stream().filter(post -> !post.isTopped()).toList();
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
