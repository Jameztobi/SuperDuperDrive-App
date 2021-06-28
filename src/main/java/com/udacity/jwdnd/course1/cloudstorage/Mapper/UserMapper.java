package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
        @Select("SELECT * FROM USERS WHERE username = #{username}")
        Users getUser(String username);

        @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname)"+
        "VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")

        @Options(useGeneratedKeys = true, keyProperty = "userid")
        int insert(Users user);

        @Update("UPDATE USERS SET firstname = #{firstname}, lastname=#{lastname} WHERE username = #{username}")
        boolean update(Users user);

        @Delete("DELETE FROM USERS WHERE username = #{username}")
        boolean delete(Users user);

}
