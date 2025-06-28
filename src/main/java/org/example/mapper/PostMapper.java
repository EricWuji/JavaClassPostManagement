package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.example.entity.Comment;

import java.util.List;

public interface PostMapper {

    @Results({
            @Result(property = "commentId", column = "comment_id"),
            @Result(property = "commentContent", column = "comment_content"),
            @Result(property = "postId", column = "post_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fromAdmin", column = "from_admin")
    })
    @Select("select * from comment where post_id = #{postId}")
    List<Comment> getCommentsByPostId(@Param("postId") int postId);
}
