package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, key,  password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credentials credential);

    @Select("SELECT * FROM CREDENTIALS WHERE  userid = #{userid}")
    List<Credentials> getUserCredentials(Integer userid);

    @Select("SELECT key FROM CREDENTIALS WHERE  userid = #{userid} AND credentialid = #{credentialid}")
    String getUserDecryptedKey(Integer userid, Integer credentialid);

    @Update("UPDATE CREDENTIALS SET username = #{username}, password=#{password}, key=#{key}, url=#{url} WHERE credentialid = #{credentialid} AND userid=#{userid}")
    void update(String username, String password, String key, String url, Integer credentialid, Integer userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid=#{userid}")
    boolean delete(Integer credentialid, Integer userid);
}
