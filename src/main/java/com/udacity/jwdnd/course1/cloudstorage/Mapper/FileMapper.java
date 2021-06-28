package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filesize, filedata, filename, contenttype,  userid) VALUES(#{filesize}, #{filedata}, #{filename}, #{contenttype}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    Integer insert(Files file);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} ")
    List<Files> getUserFile(Integer userid);


//    @Update("UPDATE FILES SET filename = #{filename}, contenttype=#{contenttype},filesize=#{filesize}, filedata=#{filedata} WHERE fileId = #{fileId} AND userid = #{userid}")
//    boolean update(Files file);

    @Delete("DELETE FROM FILES WHERE userid = #{userid} AND fileid = #{fileid}")
    boolean delete(Integer userid, Integer fileid);

}
