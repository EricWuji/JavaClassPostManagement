package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.entity.Comment;
import org.example.entity.Post;
import org.example.entity.NormalUser;

import java.util.List;

public interface UserMapper {
    @Insert("insert into post(post_content, user_id, directory_id)" +
            "values (#{postContent}, #{userId}, #{directoryId})")
    void insertPost(@Param("postContent") String postContent, @Param("userId") int userId, @Param("directoryId") int directoryId);

    @Insert("insert into comment(comment_content, user_id, post_id)" +
            "values (#{content}, #{userId}, #{postId})")
    void insertComment(@Param("content") String content, @Param("userId") int userId, @Param("postId") int postId);

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
    @Select("select * from post where user_id = #{userId} and from_admin = 0")
    List<Post> getPostsByUserId(@Param("userId") int userId);

    @Results({
            @Result(property = "commentId", column = "comment_id"),
            @Result(property = "commentContent", column = "comment_content"),
            @Result(property = "postId", column = "post_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fromAdmin", column = "from_admin")
    })
    @Select("select * from comment where user_id = #{userId} and from_admin = 0")
    List<Comment> getCommentsByUserId(@Param("userId") int userId);

    @Select("select password from user where user_name = #{userName}")
    String getPasswordByUserName(@Param("userName") String userName);

    @Select("select user_name from user where user_id = #{userId}")
    String getUserNameById(@Param("userId") int userId);

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "password", column = "password")
    })
    @Select("select * from user where user_name = #{userName}")
    NormalUser getUserByUserName(@Param("userName") String userName);

    @Insert("insert into joining(user_id, directory_id) values (#{userId}, #{directoryId})")
    void joinDirectory(@Param("userId") int userId, @Param("directoryId") int directoryId);

    @Select("select banned from joining where user_id = #{userId} and directory_id = #{directoryId}")
    Boolean isBanned(@Param("userId") int userId, @Param("directoryId") int directoryId);

    @Select("select count(*) > 0 from joining where user_id = #{userId} and directory_id = #{directoryId}")
    boolean isUserInDirectory(@Param("userId") int userId, @Param("directoryId") int directoryId);

    @Delete("delete from joining where user_id = #{userId} and directory_id = #{directoryId}")
    void LogoutFromDirectory(@Param("userId") int userId, @Param("directoryId") int directoryId);
}
