package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Admin;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.entity.NormalUser;

import java.util.List;

public interface AdminMapper {

    @Insert("insert into post(post_content, user_id, directory_id, from_admin) " +
            "values (#{postContent}, #{userId}, #{directoryId}, 1)")
    void insertPostFromAdmin(@Param("postContent") String postContent, @Param("userId") int userId, @Param("directoryId") int directoryId);

    @Insert("insert into comment(comment_content, user_id, post_id, from_admin) " +
            "values (#{content}, #{userId}, #{postId}, 1)")
    void insertCommentFromAdmin(@Param("content") String content, @Param("userId") int userId, @Param("postId") int postId);

    @Delete("delete from comment where comment_id = #{commentId}")
    void deleteCommentById(@Param("commentId") int commentId);

    @Delete("delete from post where post_id = #{postId}")
    void deletePostById(@Param("postId") int postId);

    @Results({
            @Result(property = "postId", column = "post_id"),
            @Result(property = "postContent", column = "post_content"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "directoryId", column = "directory_id"),
            @Result(property = "fromAdmin", column = "from_admin"),
            @Result(property = "topped", column = "topped")
    })
    @Select("select * from post where directory_id = #{directoryId}")
    List<Post> getPostsByDirectoryId(@Param("directoryId") int directoryId);

    @Results({
            @Result(property = "commentId", column = "comment_id"),
            @Result(property = "commentContent", column = "comment_content"),
            @Result(property = "postId", column = "post_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fromAdmin", column = "from_admin")
    })
    @Select("select * from comment where user_id = #{userId} and from_admin = 1")
    List<Comment> getCommentsByUserId(@Param("userId") int userId);

    @Results({
            @Result(property = "commentId", column = "comment_id"),
            @Result(property = "commentContent", column = "comment_content"),
            @Result(property = "postId", column = "post_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fromAdmin", column = "from_admin")
    })
    @Select("SELECT c.* FROM comment c JOIN post p ON c.post_id = p.post_id WHERE p.directory_id = #{directoryId}")
    List<Comment> getAllCommentsInDirectory(@Param("directoryId") int directoryId);

    @Update("update post set topped = 1 where post_id = #{postId}")
    void topPost(@Param("postId") int postId);

    @Update("update joining set banned = 1 where user_id = #{userId}")
    void banUser(@Param("userId") int userId);

    @Update("update joining set banned = 0 where user_id = #{userId}")
    void unbanUser(@Param("userId") int userId);

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "password", column = "password")
    })
    @Select("select u.user_id, u.user_name, u.password from user u join joining j on u.user_id = j.user_id where j.directory_id = #{directoryId} and j.banned = 0")
    List<NormalUser> getAllUnbannedUsers(@Param("directoryId") int directoryId);

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "password", column = "password")
    })
    @Select("select u.user_id, u.user_name, u.password from user u join joining j on u.user_id = j.user_id where j.directory_id = #{directoryId} and j.banned = 1")
    List<NormalUser> getBannedUsersByDirectoryId(@Param("directoryId") int directoryId);

    @Select("select admin_name from admin where admin_id = #{userId}")
    String getAdminNameById(@Param("userId") int userId);

    @Results({
            @Result(property = "postId", column = "post_id"),
            @Result(property = "postContent", column = "post_content"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "directoryId", column = "directory_id"),
            @Result(property = "fromAdmin", column = "from_admin"),
            @Result(property = "topped", column = "topped")
    })
    @Select("select * from post where directory_id = #{directoryId}")
    List<Post> getAllPostsByDirectoryId(@Param("directoryId") int directoryId);

    @Results({
            @Result(property = "userId", column = "admin_id"),
            @Result(property = "userName", column = "admin_name"),
            @Result(property = "password", column = "password"),
            @Result(property = "directoryId", column = "directory_id")
    })
    @Select("select * from admin where admin_name = #{userName}")
    Admin getAdminByUserName(@Param("userName") String userName);

    @Update("update post set topped = 0 where post_id = #{postId}")
    void UntopPost(int postId);
}
