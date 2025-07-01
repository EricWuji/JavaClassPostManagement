package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SignUpMapper {
    @Insert("insert into user(password, user_name) values (#{password}, #{userName})")
    void insertByUserNameAndPassword(@Param("password") String password, @Param("userName") String userName);

    @Select("select user.user_name from user")
    List<String> selectAllUserName();
}
